import java.util.Scanner;
class PrimeNumber {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int num = sc.nextInt();

        int factor_count = 0;

        for(int div = 1; div <= num; div++){
            if(num % div == 0){
                factor_count++;
            }
            if(factor_count > 2){
                break; // early exit 
            }
        }

        if(factor_count == 2){
            System.out.println("The given number is prime.");
        } else {
            System.out.println("The given number is not prime.");
        }
        sc.close();
    }
}