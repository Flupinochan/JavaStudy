public class SwitchClass {
    public static void main(String[] args) {

        int switchValue = 3;

        switch (switchValue) {
            case 1 -> System.out.println("Value was 1");
            case 2 -> System.out.println("Value was 2");
            default -> System.out.println("Was not 1 or 2");
        }

        printDayOfWeek(1);

    }

    public  static void printDayOfWeek(int day){

        String dayOfWeek = switch (day){
            case 0 -> {
                yield "Sunday";
            }
            case 1 -> {
                yield "Monday";
            }
            default -> {
                yield "Invalid Day";
            }
        };

        System.out.println(day + " = " + dayOfWeek);
    }

}
