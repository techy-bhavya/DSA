import java.util.*;

class LogicalOperators {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int year = sc.nextInt();
        if((year % 4 == 0) && (year % 400 == 0)){
            System.out.println("This is a leap year");
        } else if((year%4 == 0) && (year % 100 != 0)){
            System.out.println("This is a leap year");
        } else {
            System.out.println("Not a leap year");
        }

        if((year % 4 == 0) && (year % 400 == 0 ||  year % 100 != 0)){
            System.out.println("This is a leap year");
        } else {
            System.out.println("Not a leap year");
        }
        sc.close();
    }
}