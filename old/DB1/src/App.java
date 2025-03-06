import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

public class App {
    private final static String CONNECT_URL = "jdbc:mysql://localhost:3306/music";
    private final static String DB_USER_NAME = "devuser";
    private final static String DB_PASSWORD = "2525Lucky!";

    public static void main(String[] args) throws Exception {
        dbConnect();
        dbConnect2();
    }

    // DriverManagerを使用して接続する方法
    public static void dbConnect(){
        // try()の引数のconnectionは、tryブロックが終了すると自動的に.close()される
        try(Connection connection = DriverManager.getConnection(CONNECT_URL, DB_USER_NAME, DB_PASSWORD)){
            System.out.println("DB Connect Success!");
        }catch(SQLException e){
            System.out.println("DB Connection Failed...");
        }
    }

    // DataSourceを使用して接続する方法 ※DataSourceの方が新しくて、機能が多いため推奨される
    public static void dbConnect2(){
        var dataSource = new MysqlDataSource();
        dataSource.setURL(CONNECT_URL);
        dataSource.setUser(DB_USER_NAME);
        dataSource.setPassword(DB_PASSWORD);
        try (Connection connection = dataSource.getConnection()){
            System.out.println("DB Connect Success2!");
        } catch (SQLException e) {
            System.out.println("DB Connection Failed...");
        }
    }
}
