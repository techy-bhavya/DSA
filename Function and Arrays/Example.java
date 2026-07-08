class Example{
  public static void doNothing(int a, char x){
    System.err.println("Do Nothing!");
  }
  public static void main(String[] args) {
    doNothing(56,'c');
    System.out.println("End of Main Function");
  }
}