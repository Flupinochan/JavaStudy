import java.util.Arrays;
import java.util.Scanner;

public class ArrayTest {
    public static void main(String[] args) {
        int[] array = getIntegers(5);
        int[] sortedArray = sortIntegers(array);
        System.out.println(Arrays.toString(sortedArray));
    }

    public static int[] getIntegers(int arraySize){
        Scanner scanner = new Scanner(System.in);
        int[] array = new int[arraySize];
        for (int i = 0; i < array.length; i++){
            array[i] = scanner.nextInt();
        }
        return array;
    }

    public static int[] sortIntegers(int[] array){
        int[] arrayCopy = Arrays.copyOf(array, array.length);
        boolean judge = true;
        int tmp;
        while(judge){
            judge = false;
            for (int i = 0; i < (arrayCopy.length - 1); i++){
                if (arrayCopy[i] < arrayCopy[i + 1]){
                    tmp = arrayCopy[i + 1];
                    arrayCopy[i + 1] = arrayCopy[i];
                    arrayCopy[i] = tmp;
                    judge = true;
                }
            }
        }
        return arrayCopy;
    }

}
