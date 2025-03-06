public class Car {
    private String make;
    private String model;
    private String color;
    private int doors;
    private boolean convertible;

    public String getMake(){
        return make;
    }
    public String getModel() {
        return model;
    }
    public String getColor() {
        return color;
    }
    public int getDoors() {
        return doors;
    }
    public boolean isConvertible() {
        return convertible;
    }

    // this.は、インスタンスを指し、インスタンスのフィールドにアクセスする時に使用する
    // setterを使用することでif文等を利用し、検証(有効な値かどうか)してから値を設定する
    public void setMake(String make) {
        if(make == null){
            make = "Unknown";
        }
        this.make = make;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setDoors(int doors) {
        this.doors = doors;
    }
    public void setConvertible(boolean convertible) {
        this.convertible = convertible;
    }

    public void describeCar(){
        System.out.println("Make: " + make +
                ", Model: " + model +
                ", Color: " + color +
                ", Doors: " + doors +
                (convertible ? ", Convertible" : ""));
    }
}
