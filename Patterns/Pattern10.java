import java.util.*;

public class Pattern10
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int totalNoOfLines = n;
		int currentLineNo = 1;
		int outersp=n/2;
		int innersp=-1;
		while(currentLineNo<=totalNoOfLines){
		    for(int i=1;i<=outersp;i++){
		        System.out.print("  ");
		    }
		    System.out.print("* ");
		    for(int i=1;i<=innersp;i++){
		        System.out.print("  ");
		    }
		    if(currentLineNo!=1 && currentLineNo!=n){
		        System.out.print("* ");
		    }
		    
		    System.out.println();
		    
		    if(currentLineNo<=n/2){
		        outersp--;
		        innersp+=2;
		    }
		    else{
		        outersp++;
		        innersp-=2;
		        
		    }
		    currentLineNo++;
		}
		sc.close();
	}
}