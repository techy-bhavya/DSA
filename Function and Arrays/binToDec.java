//find decimal equivalent

import java.util.*;

public class binToDec
{
    public static int ToDec(int n){
        int dec=0;
        int pow2=1;
        while(n!=0){
            int dig=n%10;
            dec+=dig*pow2;
            n/=10;
            pow2*=2;
        }
        return dec;
    }
    
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n=sc.nextInt();
		int res = ToDec(n);
		System.out.println(res);
        sc.close();
	}
}