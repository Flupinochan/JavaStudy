import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        Dog2 dog = new Dog2("Wolf", "big", 100);
        Dog2 dog2 = new Dog2("Wolf", "big", 100);
        dog.makeNoise();
        dog.move("slow");

        ArrayList<Animal2> animals = new ArrayList<>();
        animals.add(dog);
        animals.add(dog2);
    }
}
