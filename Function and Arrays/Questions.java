import java.util.Scanner;

class Questions {

    public static int search(int[] arr, int x){
        if(arr==null || arr.length==0)
            return -1;
        
        for(int i=0;i<arr.length;i++){
            if(arr[i]==x){
                return i;
            }
        }
        return -1;
    }


    public static int findSpan(int[] arr){
        if(arr.length==0){
            return -1;
        }
        
        int max = arr[0]; //Integer.MIN_VALUE;
        int min = arr[0]; //Integer.MAX_VALUE;

        for(int i = 1; i < arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
            }   

            if(arr[i] < min){
                min = arr[i];
            }
        }

        return max - min;
    }

    public static int countElementsGreaterThanX(int[] arr, int x){
        int count=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]>x){
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);

        System.out.print("Enter the size of array: ");
        int size = scn.nextInt();

        int[] arr = new int[size];

        System.out.print("Enter " + size + " elements of array: ");
        
        for(int i=0; i<arr.length; i++){
            arr[i] = scn.nextInt();
        }

        System.out.print("Enter the value of x: ");
        int x = scn.nextInt();

        int res = countElementsGreaterThanX(arr, x);

        System.out.println("Number of elements greater than x: " + res);
        System.out.println("Span of the array: " + findSpan(arr));
        System.out.println("Index of x in the array: " + search(arr, x));
        scn.close();
    }
}