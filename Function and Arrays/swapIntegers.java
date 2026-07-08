class swapIntegers {
  public static void swap(int a, int b){
    int temp=a;
    a=b;
    b=temp;
  }

  public static void main(String[] args) {
    int a = 5;
    int b = 10;

    System.out.println("Before swapping: a = " + a + ", b = " + b);

    // Swapping using a temporary variable
    swap(a, b);

    System.out.println("After swapping: a = " + a + ", b = " + b);
  }
}
