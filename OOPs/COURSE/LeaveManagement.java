import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.*;

class LeaveRecord {
    String type;
    LocalDate fromDate, toDate;
    int days;

    LeaveRecord(String type, LocalDate fromDate, LocalDate toDate, int days) {
        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.days = days;
    }

    void display() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.printf("%-10s %-12s %-12s %-5d%n",
                type, fromDate.format(df), toDate.format(df), days);
    }
}

class Employee {
    private int empId;
    private String empName;
    private ArrayList<LeaveRecord> leaves = new ArrayList<>();

    private static final int MAX_CASUAL = 12;
    private static final int MAX_MEDICAL = 15;

    Employee(int empId, String empName) {
        this.empId = empId;
        this.empName = empName;
    }

    int getEmpId() {
        return empId;
    }

    void addLeave(String type, LocalDate fromDate, LocalDate toDate) {

        if (toDate.isBefore(fromDate)) {
            System.out.println("Invalid dates. To-date cannot be before from-date.");
            return;
        }

        if (fromDate.getYear() != toDate.getYear()) {
            System.out.println("Leave must be within the same financial year.");
            return;
        }

        int maxAllowed = getMaxLeaves(type);

        if (maxAllowed == 0) {
            System.out.println("Invalid leave type.");
            return;
        }

        int days = countWorkingDays(fromDate, toDate);

        if (days == 0) {
            System.out.println("Leave not required. Selected dates are only weekends.");
            return;
        }

        if (isOverlappingLeave(fromDate, toDate)) {
            System.out.println("Leave not approved. Dates overlap with an existing leave record.");
            return;
        }

        int year = fromDate.getYear();
        int alreadyTaken = getLeavesTaken(type, year);

        if (alreadyTaken + days > maxAllowed) {
            System.out.println("Leave not approved. Remaining balance: "
                    + (maxAllowed - alreadyTaken) + " day(s).");
            return;
        }

        leaves.add(new LeaveRecord(type, fromDate, toDate, days));
        System.out.println("Leave record added successfully.");
    }

    int countWorkingDays(LocalDate fromDate, LocalDate toDate) {
        int count = 0;

        LocalDate date = fromDate;

        while (!date.isAfter(toDate)) {
            DayOfWeek day = date.getDayOfWeek();

            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                count++;
            }

            date = date.plusDays(1);
        }

        return count;
    }

    boolean isOverlappingLeave(LocalDate fromDate, LocalDate toDate) {
        for (LeaveRecord leave : leaves) {
            if (!fromDate.isAfter(leave.toDate) && !toDate.isBefore(leave.fromDate)) {
                return true;
            }
        }

        return false;
    }

    int getLeavesTaken(String type, int year) {
        int total = 0;

        for (LeaveRecord leave : leaves) {
            if (leave.type.equalsIgnoreCase(type) && leave.fromDate.getYear() == year) {
                total += leave.days;
            }
        }

        return total;
    }

    int getMaxLeaves(String type) {
        if (type.equalsIgnoreCase("Casual")) {
            return MAX_CASUAL;
        } else if (type.equalsIgnoreCase("Medical")) {
            return MAX_MEDICAL;
        } else {
            return 0;
        }
    }

    void displayRecord(int year) {
        int casualTaken = getLeavesTaken("Casual", year);
        int medicalTaken = getLeavesTaken("Medical", year);

        System.out.println("\nEmployee ID   : " + empId);
        System.out.println("Employee Name : " + empName);
        System.out.println("Financial Year: " + year);

        System.out.println("\nCasual Leaves Taken    : " + casualTaken);
        System.out.println("Casual Leaves Balance  : " + (MAX_CASUAL - casualTaken));

        System.out.println("Medical Leaves Taken   : " + medicalTaken);
        System.out.println("Medical Leaves Balance : " + (MAX_MEDICAL - medicalTaken));

        System.out.println("\nLeave Details:");
        System.out.printf("%-10s %-12s %-12s %-5s%n", "Type", "From", "To", "Days");

        boolean found = false;

        for (LeaveRecord leave : leaves) {
            if (leave.fromDate.getYear() == year) {
                leave.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No leave records found.");
        }
    }
}

public class LeaveManagement {
    static Scanner sc = new Scanner(System.in);
    static DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    static Map<Integer, Employee> employees = new LinkedHashMap<>();

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n====== LNMIIT Leave Management ======");
            System.out.println("1. Add Employee Record");
            System.out.println("2. Add Leave Record");
            System.out.println("3. Search and Display Leave Record");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;

                case 2:
                    addLeave();
                    break;

                case 3:
                    searchEmployee();
                    break;

                case 4:
                    System.out.println("Thank you.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        sc.close();
    }

    static void addEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (employees.containsKey(id)) {
            System.out.println("Employee ID already exists.");
            return;
        }

        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();

        employees.put(id, new Employee(id, name));
        System.out.println("Employee record added successfully.");
    }

    static void addLeave() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Employee emp = employees.get(id);

        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        try {
            System.out.print("Enter Leave Type (Casual/Medical): ");
            String type = sc.nextLine();

            System.out.print("Enter From Date (dd-MM-yyyy): ");
            LocalDate fromDate = LocalDate.parse(sc.nextLine(), df);

            System.out.print("Enter To Date (dd-MM-yyyy): ");
            LocalDate toDate = LocalDate.parse(sc.nextLine(), df);

            emp.addLeave(type, fromDate, toDate);

        } catch (Exception e) {
            System.out.println("Invalid input or date format.");
        }
    }

    static void searchEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();

        Employee emp = employees.get(id);

        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.print("Enter Financial Year: ");
        int year = sc.nextInt();

        emp.displayRecord(year);
    }
}