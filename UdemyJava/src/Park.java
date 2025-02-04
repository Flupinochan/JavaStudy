public class Park extends Point{
    private String name;

    public Park(String location, String name) {
        super(location);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Park [name=" + name + "]";
    }
    
}
