import java.util.*;

public class Pattern11
{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int totalNoOfLines = n;
		int currentLineNo = 1;
		int stars = 1;
		int num=1;
		while(currentLineNo<=totalNoOfLines){
		    for(int i=1;i<=stars;i++){
		        System.out.print(num+" ");
		        num++;
		    }
		    System.out.println();
		    stars++;
		    currentLineNo++;
		}
		sc.close();
	}
}