import java.util.Scanner;

public class ConsoleInputClass {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int counter = 1;
        int sum = 0;

        while(counter <= 5){
            System.out.print("Enter number #" + counter + ": ");
            String inputNumber = scanner.nextLine();
            try {
                int inputNumber2 = Integer.parseInt(inputNumber);
                counter++;
                sum += inputNumber2;
            }catch (NumberFormatException nfe){
                System.out.println("Invalid Number");
            }
        }

        System.out.println("The total number: " + sum);

    }

    public static String getInputFromConsole(){
        String name = System.console().readLine("Input Your Name: ");
        System.out.println("Hi " + name);

        return "";
    }

    public static String getInputFromScanner(){

        Scanner scanner = new Scanner(System.in);

        System.out.print("Input Your Name: ");
        String name = scanner.nextLine();
        System.out.println("Hi " + name);

        return "";
    }



}
