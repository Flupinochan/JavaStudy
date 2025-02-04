import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws Exception {
        // SQLiteデータベースのファイルパス
        String dbFilePath = "./sqlite/db/text.db";
        String jdbc = "jdbc:sqlite:" + dbFilePath;

        // SQL文
        String sql = "SELECT id, name FROM table1";

        // データベース接続
        try (Connection conn = DriverManager.getConnection(jdbc);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
