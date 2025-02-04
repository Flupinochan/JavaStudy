public abstract class Animal2 {
    protected String type;
    private String size;
    private double weight;

    public Animal2(String type, String size, double weight){
        this.type = type;
        this.size = size;
        this.weight = weight;
    }

    public abstract void move(String speed);
    public abstract void makeNoise();
}
