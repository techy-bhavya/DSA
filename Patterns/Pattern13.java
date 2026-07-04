import java.util.*;

public class Pattern13
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int totalNoOfLines = 2*n + 1;
		int currentLineNo = 1;
		int stars = 1;
		int sp=n;
		int startNum = 1;
		while(currentLineNo<=totalNoOfLines){
		    for(int i=1;i<=sp;i++){
		        System.out.print("  ");
		    }
		    int num = startNum;
		    for(int i=1;i<=stars;i++){
		        
		        System.out.print(num+" ");
		        if(i<=stars/2){
		            num++;
		        }
		        else{
		            num--;
		        }
		    }
		    System.out.println();
		    if(currentLineNo<=n){
		        stars+=2;
		        sp--;
		        startNum++;
		    }
		    else{
		        stars-=2;
		        sp++;
		        startNum--;
		    }
		    currentLineNo++;
		}
		sc.close();
	}
}