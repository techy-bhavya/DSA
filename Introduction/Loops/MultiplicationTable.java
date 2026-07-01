import java.util.Scanner;
class MultiplicationTable {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number for multiplication table: ");
        int num = sc.nextInt();

        for(int mul = 1; mul <= 10; mul++){
            System.out.println(num + " x " + mul + " = " + (num*mul));
        }
        sc.close();
    }
}