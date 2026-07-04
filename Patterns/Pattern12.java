import java.util.*;

public class Pattern12
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int totalNoOfLines = n;
		int currentLineNo = 1;
		int stars = 1;
		int a=1;
		int b=1;
		while(currentLineNo<=totalNoOfLines){
		    for(int i=1;i<=stars;i++){
		        System.out.print(a+" ");
		        int c=a+b;
		        a=b;
		        b=c;
		    }
		    System.out.println();
		    stars++;
		    currentLineNo++;
		}
		sc.close();
	}
}