import java.util.*;

public class Pattern6
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int totalNoOfLines = 2*n + 1;
		int currentLineNo = 1;
		int stars = n+1;
		int sp=1;
		while(currentLineNo<=totalNoOfLines){
		    for(int i=1;i<=stars;i++){
		        System.out.print("* ");
		    }
		    for(int i=1;i<=sp;i++){
		        System.out.print("  ");
		    }
		    for(int i=1;i<=stars;i++){
		        System.out.print("* ");
		    }
		    System.out.println();
		    
		    if(currentLineNo<=n){
		        stars--;
		        sp+=2;
		    }
		    else{
		        stars++;
		        sp-=2;
		    }
		    currentLineNo++;
		}
		sc.close();
	}
}