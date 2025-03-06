import java.util.Objects;

public class Dog2 extends Animal2 implements FlightEnabled {

    public Dog2(String type, String size, double weight) {
        super(type, size, weight);
    }

    @Override
    public void move(String speed) {
        if (speed.equals("slow")){
            System.out.println(type + " walking");
        }else{
            System.out.println(type + " running");
        }

    }

    @Override
    public void makeNoise() {
        if (Objects.equals(type, "Wolf")){
            System.out.println("Howling");
        }else{
            System.out.println("Woof");
        }
    }

    @Override
    public void takeOff() {

    }

    @Override
    public void land() {

    }

    @Override
    public void fly() {

    }
}
