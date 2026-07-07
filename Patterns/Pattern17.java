import java.util.*;

public class Pattern17
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int totalNoOfLines=n;
		int currentLineNo=1;
		int stars = 1;
		int sp=2;
		while(currentLineNo<=totalNoOfLines){
		    if (currentLineNo!=n/2 + 1){
		        for(int i=1;i<=sp;i++){
		            System.out.print("  ");
		        }
		    }
		    else{
		        System.out.print("* * ");
		    }
		    for(int i=1;i<=stars;i++){
		        System.out.print("* ");
		    }
		    System.out.println();
		    if(currentLineNo<=n/2){
		        stars++;
		    }
		    else{
		        stars--;
		    }
		    currentLineNo++;
		}
		sc.close();
	}
}