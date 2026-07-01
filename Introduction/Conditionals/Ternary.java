import java.util.Scanner;
class Ternary {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int x = sc.nextInt();
        int y = sc.nextInt();

        int max = x > y ? x : y;

        System.out.println("Max value is " + max);
        sc.close();
    }
}