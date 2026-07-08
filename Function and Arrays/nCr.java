//find nCr

import java.util.*;

public class nCr
{
    public static int factorial(int a){
        int prod=1;
        for(int i=1;i<=a;i++){
            prod*=i;
        }
        return prod;
    }
    
    public static double calcnCr(int n, int r){
        if(r>n || r<0){
            return 0;
        }
        return factorial(n)/(double)(factorial(n-r)*factorial(r));
    }
    
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n=sc.nextInt();
		int r=sc.nextInt();
	    double res = calcnCr(n,r);
		System.out.println(res);
        sc.close();
	}
}