public class IfClass {
    public static void main(String[] args) {

        displayHighScorePosition("metalmental", "defence");
        int result = calculateHighScorePosition(750);
        System.out.println(result);

    }

    public static void displayHighScorePosition(String playerName, String playerPosition){
        String message = String.format("%sさんのポジションは、%sです", playerName, playerPosition);
        System.out.println(message);
    }

    public static int calculateHighScorePosition(int score){
        int result = 4;
        if (score >= 1000){
            result = 1;
        } else if (score >= 500) {
            result = 2;
        } else if (score >= 100) {
            result = 3;
        }
        return result;
    }
}
