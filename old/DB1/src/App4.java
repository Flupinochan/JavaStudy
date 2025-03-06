import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.awt.SystemTray;
import java.sql.DatabaseMetaData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App4 {

    private static String USE_SCHEMA = "USE storefront"; // DBを選択
    private static int MYSQL_DB_NOT_FOUND = 1049;

    public static void main(String[] args) {
        Properties props = envRead();

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));

        try(Connection conn = dataSource.getConnection()){
            DatabaseMetaData metaData = conn.getMetaData();
            // System.out.println(metaData.getSQLStateType());

            // テスト実行
            printTableColumnSetting(conn);
            insertRecordForTable(conn);

            // スキーマの存在をチェック
            if(!checkSchema(conn)){
                System.out.println("storefront schema does not exists");
                // スキーマを作成
                setUpSchema(conn);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    // 環境変数ファイルからDB設定を読み込む
    public static Properties envRead(){
        Properties props = new Properties();
        try {
            String envFileUri = "./music.properties";
            props.load(Files.newInputStream(Path.of(envFileUri), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }

    // スキーマの存在をチェック
    private static boolean checkSchema(Connection conn) throws SQLException{
        try(Statement statement = conn.createStatement()){
            statement.execute(USE_SCHEMA);
        }catch(SQLException e){
            e.printStackTrace();
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());

            if(conn.getMetaData().getDatabaseProductName().equals("MySQL") && e.getErrorCode() == MYSQL_DB_NOT_FOUND){
                return false;
            }else throw e;
        }
        return true;
    }

    // スキーマを作成
    private static void setUpSchema(Connection conn) throws SQLException {
        String createSchema = "CREATE SCHEMA storefront";

        String createOrder = """
                CREATE TABLE storefront.order(
                order_id int NOT NULL AUTO_INCREMENT,
                order_date DATETIME NOT NULL,
                PRIMARY KEY (order_id)
                )""";

        String createOrderDetails = """
                CREATE TABLE storefront.order_details (
                order_detail_id int NOT NULL AUTO_INCREMENT,
                item_description text,
                order_id int DEFAULT NULL,
                PRIMARY KEY (order_detail_id),
                CONSTRAINT FK_ORDERID FOREIGN KEY (order_id)
                REFERENCES storefront.order (order_id) ON DELETE CASCADE
                )""";

        try (Statement statement = conn.createStatement()){
            System.out.println("Creating storefront Database");
            statement.execute(createSchema);
            if(checkSchema(conn)){
                statement.execute(createOrder);
                System.out.println("Successfully Created Order");
                statement.execute(createOrderDetails);
                System.out.println("Successfully Order Details");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // テーブルのカラム情報を出力
    private static void printTableColumnSetting(Connection conn) throws SQLException{
        try (Statement statement = conn.createStatement()){
            String query = "DESCRIBE storefront.order;";
            ResultSet resultSet = statement.executeQuery(query);
            var meta = resultSet.getMetaData();

            // ヘッダーの出力
            String[] header = {"Field", "Type", "Null", "Key", "Default", "Extra"};
            for (String col : header) {
                System.out.printf("%-15s", col);
            }
            System.out.println();

            while(resultSet.next()){
                for(int i = 1; i <= meta.getColumnCount(); i++){
                    System.out.printf("%-15s", resultSet.getString(i));
                }
                System.out.println();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // orderテーブルにレコードを追加
    private static void insertRecordForTable(Connection conn) throws SQLException{
        // Datetimeを取得
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);

        try(Statement statement = conn.createStatement()){
            String sql = "INSERT INTO storefront.order (order_date) VALUES ('%s')".formatted(formattedNow);
            boolean insertResult = statement.execute(sql);
            int insertCount = statement.getUpdateCount();
            if(insertCount > 0){
                System.out.println("orderテーブルに新規レコードを追加しました");
                String sql2 = "SELECT * FROM storefront.order;";
                ResultSet resultSet = statement.executeQuery(sql2);
                var meta = resultSet.getMetaData();
                while(resultSet.next()){
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        System.out.printf("%-15s", resultSet.getString(i));
                    }
                    System.out.println();
                }
            }else{
                System.out.println("orderテーブルに新規レコードの追加が失敗しました");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
