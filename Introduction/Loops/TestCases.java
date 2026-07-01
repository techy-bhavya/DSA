import java.util.Scanner;
class TestCases {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of integers you want to know about: ");

        int testCases = sc.nextInt();

        while(testCases > 0){
            System.out.print("Enter the number: ");

            int num = sc.nextInt();

            if(num % 2 == 0){
                System.out.println("Current number is even");
            } else {
                System.out.println("Current number is odd");
            }

            testCases--;
            sc.close();
        }
    }  
}