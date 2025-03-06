public class MethodClass {
    public static void main(String[] args) {
        calculateScore("metalmental", 999);
        calculateScore(100);

        System.out.println(convertToCentimeters(5, 8));
        System.out.println(convertToCentimeters( 68));

        System.out.println(getDurationString(3611));
        System.out.println(getDurationString(61, 10));
    }

    public static void calculateScore(String playerName, int score){
        System.out.println("Player " + playerName + " scored " + score + " points");
    }

    public static void calculateScore(int score){
        calculateScore("Anonymous", score);
    }

    public static double convertToCentimeters(int inches){
        double totalCentimeters = inches * 2.54;
        return totalCentimeters;
    }

    public static double convertToCentimeters(int feet, int inches){
        double totalCentimeters = convertToCentimeters((feet * 12) + inches);
        return totalCentimeters;
    }

    public static String getDurationString(int seconds){
        if (seconds < 0){
            return "Invalid data";
        }

        int minutes = seconds /60;
        String message = getDurationString(minutes, seconds);

        return message;
    }

    public static String getDurationString(int minutes, int seconds){
        if (minutes < 0 || seconds < 0 || seconds > 59){
            return "Invalid data";
        }

        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        String message = hours + "h " + remainingMinutes + "m " + seconds + "s";

        return message;
    }
}
