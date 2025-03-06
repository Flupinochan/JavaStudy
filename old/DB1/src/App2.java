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

public class App2 {
    public static void main(String[] args) {
        Properties props = envRead();
        dbConnect(props);
    }

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

    // executeQuery実行例
    public static MysqlDataSource dbConnect(Properties props){
        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));

        try {
            // LIMIT 10と同じで、最大10件のみ取得
            dataSource.setMaxRows(10);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
        ){
            String albumName = "Tapestry";
            String query = "SELECT * FROM music.albumview WHERE album_name='%s';".formatted(albumName);

            // int artistId = 7;
            // String query = "SELECT * FROM music.artists WHERE artist_id='%d' LIMIT 10;".formatted(artistId);

            // LIMIT句はデータベースの種類に依存するが、以下に書き換えれば依存しない (ただし複雑なSQLになる)
            // String query = """
            //         WITH RankedRows AS (
            //                             SELECT
            //                                 *,
            //                                 ROW_NUMBER() OVER (ORDER BY artist_id) AS row_num
            //                             FROM music.artists
            //                         )
            //                         SELECT * FROM RankedRows WHERE row_num <= 10""";

            ResultSet resultSet = statement.executeQuery(query);
            var meta = resultSet.getMetaData();

            // SQL取得1
            // for (int i = 1; i <= meta.getColumnCount(); i++) {
            //     System.out.printf("%d %s %s %n",
            //         i,
            //         meta.getCatalogName(i),
            //         meta.getColumnType(i)
            //     );
            // }

            System.out.println("=".repeat(40));

            // // SQL取得2
            // while (resultSet.next()) { 
            //     System.out.printf("%d %s %s %n",
            //         resultSet.getInt("track_number"),
            //         resultSet.getString("artist_name"),
            //         resultSet.getString("song_title")
            //     );
            // }

            // SQL取得3
            while(resultSet.next()){
                for(int i = 1; i <= meta.getColumnCount(); i++){
                    System.out.printf("%-15s", resultSet.getString(i));
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("DB Connection Failed...");
        }
        return dataSource;
    }
}
