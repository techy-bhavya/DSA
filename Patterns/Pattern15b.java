import java.util.*;

public class Pattern15b
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int totalNoOfLines = n;
		int currentLineNo = 1;
		int stars = n;
		int outersp = 0;
		while(currentLineNo<=totalNoOfLines){
		    for(int i=1;i<=outersp;i++){
		        System.out.print("  ");
		    }
		    for(int i=1;i<=stars;i++){
		        if(currentLineNo!=1 && currentLineNo<=n/2 && i>1 && i<stars){
		            System.out.print("  ");
		        }
		        else{
		            System.out.print("* ");
		        }
		    }
		    System.out.println();
		    if(currentLineNo<=n/2){
		        outersp++;
		        stars-=2;
		    }
		    else{
		        outersp--;
		        stars+=2;
		    }
		    
		    currentLineNo++;
		}
		sc.close();
	}
}