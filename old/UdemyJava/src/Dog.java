public class Dog extends Animal {

    private String earShape;
    private String tailShape;

    public Dog() {
        super("Mutt", "Big", 50);
    }

    public Dog(String type, double weight){
        this(type, weight, "Perky", "Curled");
    }

    public Dog(String type, double weight, String earShape, String tailShape) {
        // superは最初に実行する必要があるため、if文ではなく三項演算子で直接記載する
        super(type, (weight < 15) ? "small" : (weight < 35) ? "medium" : "large", weight);
        this.earShape = earShape;
        this.tailShape = tailShape;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "earShape='" + earShape + '\'' +
                ", tailShape='" + tailShape + '\'' +
                "} " + super.toString();
    }

    @Override
    public void move(String speed) {
        super.move(speed);
        if (speed == "slow"){
            walk();
            wagTail();
        }else{
            run();
            bark();
        }

        System.out.println();
    }

    @Override
    public void makeNoise() {
        // protectedにすればサブクラスから親クラスのフィールドにアクセス可能
        if (type == "Wolf"){
            System.out.println("Wolf");
        }
        bark();
        System.out.println();
    }

    private void bark(){
        System.out.println("Woof! ");
    }

    private void run(){
        System.out.println("Dog Running! ");
    }

    private void walk(){
        System.out.println("Dog Walking! ");
    }

    private void wagTail(){
        System.out.println("Tail Wagging! ");
    }

}
