class Student{
  String name;
  int rollNo;
  int age;
}


class Main{
  public static void main(String[] args) {
    Student s1 = new Student();
    s1.name = "John";

    System.out.println(s1.name);
    System.out.println(s1.rollNo);
    System.out.println(s1.age);
  }
}

