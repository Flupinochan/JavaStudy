interface Player {}

record BaseballPlayer(String name, String position) implements Player {}
record FootballPlayer(String name, String position) implements Player {}

public class Main1 {
    public static void main(String[] args) {
        System.out.println("");

        Team<BaseballPlayer> phillies3 = new Team<>("Philadelphia Phillies");
        Team<BaseballPlayer> astros3 = new Team<>("Houston Astros");
        scoreResult(phillies3, 3, astros3, 5);

        var harper1 = new BaseballPlayer("B Harper", "Right Fielder");
        var marsh1 = new BaseballPlayer("B Marsh", "Right Fielder");
        phillies3.addTeamMember(harper1);
        phillies3.addTeamMember(marsh1);
        phillies3.listTeamMembers();
        
        Team<FootballPlayer> phillies4 = new Team<>("Philadelphia Phillies");
        Team<FootballPlayer> astros4 = new Team<>("Houston Astros");
        scoreResult(phillies4, 3, astros4, 5);

        var marsh2 = new FootballPlayer("B Marsh", "Right Fielder");
    }

    public static void scoreResult(Team<BaseballPlayer> team1, int t1_score, Team<BaseballPlayer> team2, int t2_score){
        String message = team1.setScore(t1_score, t2_score);
        team2.setScore(t1_score, t2_score);
        System.out.printf("%s %s %s %n", team1, message, team2);
    }

}