import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

// Stores information about one leave
class LeaveRecord {

    private final String type;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final int days;

    LeaveRecord(String type, LocalDate fromDate,
                LocalDate toDate, int days) {

        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.days = days;
    }

    String getType() {
        return type;
    }

    LocalDate getFromDate() {
        return fromDate;
    }

    LocalDate getToDate() {
        return toDate;
    }

    int getDays() {
        return days;
    }

    boolean overlaps(LocalDate newFromDate, LocalDate newToDate) {

        return !newFromDate.isAfter(toDate)
                && !newToDate.isBefore(fromDate);
    }

    void display(DateTimeFormatter formatter) {

        System.out.printf(
                "%-10s %-12s %-12s %-5d%n",
                type,
                fromDate.format(formatter),
                toDate.format(formatter),
                days
        );
    }
}

// Stores employee details and their leave records
class Employee {

    private final int empId;
    private final String empName;
    private final ArrayList<LeaveRecord> leaveRecords;

    private static final int MAX_CASUAL_LEAVES = 12;
    private static final int MAX_MEDICAL_LEAVES = 15;

    Employee(int empId, String empName) {

        this.empId = empId;
        this.empName = empName;
        this.leaveRecords = new ArrayList<>();
    }

    int getEmpId() {
        return empId;
    }

    String getEmpName() {
        return empName;
    }

    void addLeave(String inputType,
                  LocalDate fromDate,
                  LocalDate toDate) {

        String leaveType = getValidLeaveType(inputType);

        if (leaveType == null) {
            System.out.println(
                    "Invalid leave type. Enter Casual or Medical."
            );
            return;
        }

        if (toDate.isBefore(fromDate)) {
            System.out.println(
                    "Invalid dates. To-date cannot be before From-date."
            );
            return;
        }

        /*
         * The question considers January to December
         * as one financial year.
         */
        if (fromDate.getYear() != toDate.getYear()) {
            System.out.println(
                    "Leave must be within the same financial year."
            );
            return;
        }

        int workingDays = countWorkingDays(fromDate, toDate);

        if (workingDays == 0) {
            System.out.println(
                    "Leave is not required because the selected "
                            + "dates contain only weekends."
            );
            return;
        }

        if (isOverlappingLeave(fromDate, toDate)) {
            System.out.println(
                    "Leave not approved. The selected dates overlap "
                            + "with an existing leave record."
            );
            return;
        }

        int year = fromDate.getYear();
        int maximumAllowed = getMaximumLeaves(leaveType);
        int alreadyTaken = getLeavesTaken(leaveType, year);
        int remainingBalance = maximumAllowed - alreadyTaken;

        if (workingDays > remainingBalance) {
            System.out.println(
                    "Leave not approved. Requested leave: "
                            + workingDays + " day(s)."
            );

            System.out.println(
                    "Remaining " + leaveType
                            + " leave balance: "
                            + remainingBalance + " day(s)."
            );

            return;
        }

        LeaveRecord record = new LeaveRecord(
                leaveType,
                fromDate,
                toDate,
                workingDays
        );

        leaveRecords.add(record);

        System.out.println("Leave record added successfully.");
        System.out.println(
                "Number of working days counted: "
                        + workingDays
        );

        System.out.println(
                "Remaining " + leaveType
                        + " leave balance: "
                        + (remainingBalance - workingDays)
        );
    }

    private String getValidLeaveType(String type) {

        if (type.equalsIgnoreCase("Casual")) {
            return "Casual";
        }

        if (type.equalsIgnoreCase("Medical")) {
            return "Medical";
        }

        return null;
    }

    private int getMaximumLeaves(String type) {

        if (type.equalsIgnoreCase("Casual")) {
            return MAX_CASUAL_LEAVES;
        }

        if (type.equalsIgnoreCase("Medical")) {
            return MAX_MEDICAL_LEAVES;
        }

        return 0;
    }

    private int countWorkingDays(LocalDate fromDate,
                                 LocalDate toDate) {

        int workingDays = 0;
        LocalDate currentDate = fromDate;

        while (!currentDate.isAfter(toDate)) {

            DayOfWeek day = currentDate.getDayOfWeek();

            if (day != DayOfWeek.SATURDAY
                    && day != DayOfWeek.SUNDAY) {

                workingDays++;
            }

            currentDate = currentDate.plusDays(1);
        }

        return workingDays;
    }

    private boolean isOverlappingLeave(LocalDate fromDate,
                                       LocalDate toDate) {

        for (LeaveRecord record : leaveRecords) {

            if (record.overlaps(fromDate, toDate)) {
                return true;
            }
        }

        return false;
    }

    private int getLeavesTaken(String type, int year) {

        int totalLeaves = 0;

        for (LeaveRecord record : leaveRecords) {

            boolean sameType =
                    record.getType().equalsIgnoreCase(type);

            boolean sameYear =
                    record.getFromDate().getYear() == year;

            if (sameType && sameYear) {
                totalLeaves += record.getDays();
            }
        }

        return totalLeaves;
    }

    void displayRecord(int year,
                       DateTimeFormatter formatter) {

        int casualLeavesTaken =
                getLeavesTaken("Casual", year);

        int medicalLeavesTaken =
                getLeavesTaken("Medical", year);

        int casualBalance =
                MAX_CASUAL_LEAVES - casualLeavesTaken;

        int medicalBalance =
                MAX_MEDICAL_LEAVES - medicalLeavesTaken;

        System.out.println("\n========== Employee Leave Record ==========");

        System.out.println("Employee ID     : " + empId);
        System.out.println("Employee Name   : " + empName);
        System.out.println("Financial Year  : " + year);

        System.out.println("\nCasual Leaves");

        System.out.println(
                "Leaves already taken : " + casualLeavesTaken
        );

        System.out.println(
                "Remaining balance     : " + casualBalance
        );

        System.out.println("\nMedical Leaves");

        System.out.println(
                "Leaves already taken : " + medicalLeavesTaken
        );

        System.out.println(
                "Remaining balance     : " + medicalBalance
        );

        System.out.println("\nLeave Details");

        System.out.printf(
                "%-10s %-12s %-12s %-5s%n",
                "Type",
                "From Date",
                "To Date",
                "Days"
        );

        boolean recordFound = false;

        for (LeaveRecord record : leaveRecords) {

            if (record.getFromDate().getYear() == year) {

                record.display(formatter);
                recordFound = true;
            }
        }

        if (!recordFound) {
            System.out.println(
                    "No leave records found for the given year."
            );
        }

        System.out.println("===========================================");
    }
}

// Manages all employee objects
class LeaveManagementSystem {

    private final Map<Integer, Employee> employees;

    LeaveManagementSystem() {
        employees = new LinkedHashMap<>();
    }

    void addEmployee(int empId, String empName) {

        if (employees.containsKey(empId)) {
            System.out.println(
                    "Employee ID already exists."
            );
            return;
        }

        Employee employee =
                new Employee(empId, empName);

        employees.put(empId, employee);

        System.out.println(
                "Employee record added successfully."
        );
    }

    Employee searchEmployee(int empId) {
        return employees.get(empId);
    }
}

// Main application class
public class LeaveManagement {

    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter;
    private final LeaveManagementSystem system;

    LeaveManagement() {

        scanner = new Scanner(System.in);

        dateFormatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        system = new LeaveManagementSystem();
    }

    public static void main(String[] args) {

        LeaveManagement application =
                new LeaveManagement();

        application.run();
    }

    void run() {

        int choice;

        do {
            displayMenu();
            choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    addEmployee();
                    break;

                case 2:
                    addLeaveRecord();
                    break;

                case 3:
                    searchAndDisplayRecord();
                    break;

                case 4:
                    System.out.println(
                            "Thank you for using the system."
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Enter a number from 1 to 4."
                    );
            }

        } while (choice != 4);

        scanner.close();
    }

    private void displayMenu() {

        System.out.println(
                "\n====== LNMIIT Leave Management System ======"
        );

        System.out.println("1. Add Employee Record");
        System.out.println("2. Add Leave Record");
        System.out.println("3. Search and Display Leave Record");
        System.out.println("4. Exit");
    }

    private void addEmployee() {

        int empId =
                readInteger("Enter Employee ID: ");

        System.out.print("Enter Employee Name: ");
        String empName = scanner.nextLine().trim();

        if (empName.isEmpty()) {
            System.out.println(
                    "Employee name cannot be empty."
            );
            return;
        }

        system.addEmployee(empId, empName);
    }

    private void addLeaveRecord() {

        int empId =
                readInteger("Enter Employee ID: ");

        Employee employee =
                system.searchEmployee(empId);

        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println(
                "Employee found: "
                        + employee.getEmpName()
        );

        System.out.print(
                "Enter Leave Type (Casual/Medical): "
        );

        String leaveType =
                scanner.nextLine().trim();

        try {
            System.out.print(
                    "Enter From Date (dd-MM-yyyy): "
            );

            LocalDate fromDate =
                    LocalDate.parse(
                            scanner.nextLine().trim(),
                            dateFormatter
                    );

            System.out.print(
                    "Enter To Date (dd-MM-yyyy): "
            );

            LocalDate toDate =
                    LocalDate.parse(
                            scanner.nextLine().trim(),
                            dateFormatter
                    );

            employee.addLeave(
                    leaveType,
                    fromDate,
                    toDate
            );

        } catch (DateTimeParseException exception) {

            System.out.println(
                    "Invalid date. Enter the date in "
                            + "dd-MM-yyyy format."
            );
        }
    }

    private void searchAndDisplayRecord() {

        int empId =
                readInteger("Enter Employee ID: ");

        Employee employee =
                system.searchEmployee(empId);

        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        int year =
                readInteger("Enter Financial Year: ");

        employee.displayRecord(
                year,
                dateFormatter
        );
    }

    private int readInteger(String message) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException exception) {

                System.out.println(
                        "Invalid input. Please enter an integer."
                );
            }
        }
    }
}

