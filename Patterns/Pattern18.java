import java.util.*;

public class Pattern18
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int totalNoOfLines=n;
		int currentLineNo=1;
		int stars=3;
		int sp=1;
		int startNum=1;
		while(currentLineNo<=totalNoOfLines){
		    int num=startNum;
		    for(int i=1;i<=stars;i++){
		        System.out.print(num+" ");
		        num++;
		    }
		    num--;
		    for(int i=1;i<=sp;i++){
		        System.out.print("  ");
		    }
		    for(int i=1;i<=stars;i++){
		        System.out.print(num+" ");
		        num--;
		    }
		    System.out.println();
		    if(currentLineNo<=n/2){
		        stars--;
		        sp+=2;
		        startNum++;
		    }
		    else{
		        stars++;
		        sp-=2;
		        startNum--;
		    }
		    currentLineNo++;
		    
		}
		sc.close();
	}
}