public class swapIntegersSolution {

  public static void swapIntegers(int[] arr){
    int temp = arr[0];
    arr[0]=arr[1];
    arr[1] = temp;

  }
      
  public static void main(String[] args) {
    int n=2;
    int[] arr = new int[n];
    arr[0] = 5;
    arr[1] = 10;
    System.out.println("Before swapping: a = " + arr[0] + ", b = " + arr[1]);
    swapIntegers(arr);
    System.out.println("After swapping: a = " + arr[0] + ", b = " + arr[1]);
  }
}

/* DOES NOT WORK FOR WRAPPER CLASSES

class swapIntegersWrapper {
  public static void swap(Integer a, Integer b){
    Integer temp= new Integer(a);
    a=b;
    b=temp;
  }

  public static void main(String[] args) {
    Integer a = new Integer(5);
    Integer b = new Integer(8);

    System.out.println("Before swapping: a = " + a + ", b = " + b);

    // Swapping using a temporary variable
    swap(a, b);

    System.out.println("After swapping: a = " + a + ", b = " + b);
  }
}
 */