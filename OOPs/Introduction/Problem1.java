class Car{
  String engineType;
  String color;
  int horsepower;

  public Car(){

  }

  public Car(String engineType){
    this.engineType = engineType;
  }

  public Car(String engineType, String color){
    this(engineType);
    this.color = color;
  }

  public Car(String engineType, String color, int horsepower){
    this(engineType, color);
    this.horsepower = horsepower;
  }

  public int twiceHorsePower(){
    return horsepower * 2;
  }

  public String changeColor(String newColor){
    this.color = newColor;
    return this.color;
  }
}

public class Problem1 {
  public static void main(String[] args) {
    Car zantro = new Car("V8", "Red", 400);
    System.out.println("Engine Type: " + zantro.engineType);
    System.out.println("Color: " + zantro.color);
    System.out.println("Horsepower: " + zantro.horsepower);
    System.out.println("Twice Horsepower: " + zantro.twiceHorsePower());
    System.out.println("Changing color to Blue: " + zantro.changeColor("Blue"));
  }
}
