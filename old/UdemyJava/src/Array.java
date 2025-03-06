import java.util.Arrays;
import java.util.Random;

public class Array {
    public static void main(String[] args) {
        int[] myIntArray = new int[10];
        myIntArray[5] = 50;

        int[] myIntArray2 = new int[]{1, 2, 3, 4, 5};
        int[] myIntArray3 = {1, 2, 3, 4, 5};

        // Enhanced for loop
        int[] newArray = new int[]{1, 2, 3, 4, 5};
        for (int j : newArray) {
            System.out.println(j);
        }
        // Arraysクラス 対象のArrayに対してsortなど様々な操作ができる
        System.out.println(Arrays.toString(newArray));

        System.out.println(Arrays.toString(getRandomArray(10)));

        int[] arrayTest = new int[5];
        Arrays.fill(arrayTest, 5); // Arrayの要素を全て5にする
        System.out.println(Arrays.toString(arrayTest));

        int[] arrayTestCopy = Arrays.copyOf(arrayTest, arrayTest.length); // Arrayをコピー
        arrayTestCopy[0] = 1111;
        System.out.println(Arrays.toString(arrayTestCopy));
    }

    private static int[] getRandomArray(int length){
        Random random = new Random();
        int[] newInt = new int[length];
        for (int i = 0; i < length; i++){
            newInt[i] = random.nextInt(100);
        }
        Arrays.sort(newInt); // sort
        return newInt;
    }
}
