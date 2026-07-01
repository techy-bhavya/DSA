import java.util.Scanner;
class PrimeOptimized {

    // this will fail for input number = 1, fix this scenario 
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int num = sc.nextInt();

        boolean isPrime = true;

        for(int div = 2; div*div <= num; div++){
            if(num % div == 0){
                isPrime = false;
                break;
            }
        }

        if(isPrime == true){
            System.out.println("This number is a prime number.");
        } else {
            System.out.println("This number is a not prime number.");
        }
        sc.close();
    }
}