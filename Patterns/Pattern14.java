import java.util.*;

public class Pattern14
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int totalNoOfLines = n;
		int currentLineNo = 1;
		int stars = 1;
		int sp=2*n-3;
		while(currentLineNo<=totalNoOfLines){
		    int num=1;
		    for(int i=1;i<=stars;i++){
		        System.out.print(num+" ");
		        num++;
		    }
		    for(int i=1;i<=sp;i++){
		        System.out.print("  ");
		    }
		    if(currentLineNo==n){
		        stars--;
		        num--;
		    }
		    num--;
		    for(int i=1;i<=stars;i++){
		        System.out.print(num+" ");
		        num--;
		    }
		    System.out.println();
		    stars++;
		    sp-=2;
		    currentLineNo++;
		}
		sc.close();
	}
}