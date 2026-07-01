import java.util.Scanner;
class Factorial {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int num = sc.nextInt();
        int res = 1;

        for(int mul = 1; mul <= num; mul++){
            res = res * mul;
        }

        System.out.println("Factorial of " + num + " is: " + res);
        sc.close();
    }
}