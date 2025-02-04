public class DoWhile {
    public static void main(String[] args) {
        System.out.println(isPalindrome(101));
    }

    public static boolean isPalindrome(int number){
        int originalNumber = number;
        int reverse = 0;

        while(number != 0){
            reverse = (number % 10) + (reverse * 10);
            number /= 10;
        }

        if(originalNumber == reverse){
            return true;
        }else{
            return false;
        }
    }
}
