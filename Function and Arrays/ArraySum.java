import java.util.*;

public class ArraySum
{
    
    public static int[] sumOfTwoArrays(int[] arr1, int[] arr2){
        int[] res = new int[Math.max(arr1.length,arr2.length)+1];
        int i=arr1.length - 1;
        int j = arr2.length - 1;
        int k = res.length - 1;
        int carry = 0;
        while(k>=0){
            int cSum = 0;
            if(i>=0){
                cSum+=arr1[i];
            }
            if(j>=0){
                cSum+=arr2[j];
            }
            cSum+=carry;
            res[k]=cSum%10;
            carry=cSum/10;
            i--;
            j--;
            k--;
        }
        return res;
    }
    
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n1 = sc.nextInt();
		int[] arr1 = new int[n1];
		for(int i=0;i<n1;i++){
		    arr1[i] = sc.nextInt();
		}
		int n2 = sc.nextInt();
		int[] arr2 = new int[n2];
		for(int i=0;i<n2;i++){
		    arr2[i] = sc.nextInt();
		}
		
		int[] res = sumOfTwoArrays(arr1,arr2);
		for(int i=0;i<res.length;i++){
		    if(i==0 && res[i]==0)
		        continue;
		    System.out.print(res[i] + " ");
		}
		sc.close();
	}
}