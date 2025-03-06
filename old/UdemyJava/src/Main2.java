public class Main2{
    public static void main(String[] args) {
        var nationalUSParks = new Park[]{
            new Park("44, -110", "Yellowstone"),
            new Park("36, 33", "Grand canyon"),
            new Park("15, 220", "Yosemite")
        };

        Layer<Park> parkLayer = new Layer<>(nationalUSParks);
        parkLayer.renderLayer();
    }
}