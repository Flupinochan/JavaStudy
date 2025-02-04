import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.awt.SystemTray;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import org.w3c.dom.css.CSSValue;

public class App7 {
    private static final int ARTIST_COLUMN = 0;
    private static final int ALBUM_COLUMN = 1;
    private static final int SONG_COLUMN = 3;

    public static void main(String[] args) {
        // DB接続設定
        Properties props = envRead();
        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));

        // ファイルからデータ取得
        Map<String, Map<String, String>> albums = null;
        try (var lines = Files.lines(Path.of("./data/NewAlbums.csv"))){
            albums = lines.map(s -> s.split(","))
                    .collect(
                        Collectors.groupingBy(s -> s[ARTIST_COLUMN],
                        Collectors.groupingBy(s -> s[ALBUM_COLUMN],
                        Collectors.mapping(s -> s[SONG_COLUMN], 
                        Collectors.joining("\",\"", "[\"", "\"]")
                    ))));
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // albums.forEach((artist, artistAlbums) -> {
        //     artistAlbums.forEach((key, value) -> {
        //         System.out.println(key + ": " + value);
        //     });
        // });
        
        // SQL実行
        try(Connection connection = dataSource.getConnection()){
            // // CALLの実行(Stored Procedureの呼び出し)は、prepareCallで行う
            // CallableStatement cs = connection.prepareCall("CALL music.addAlbumInOutCounts(?, ?, ?, ?)");
            // albums.forEach((artist, albumMap) -> {
            //     albumMap.forEach((album, songs) -> {
            //         try {
            //             cs.setString(1, artist);
            //             cs.setString(2, album);
            //             cs.setString(3, songs);
            //             cs.setInt(4, 10);
            //             cs.registerOutParameter(4, Types.INTEGER); // OUTによって出力を受け取る
            //             cs.execute();
            //             System.out.printf("%d songs were added for %s%n", cs.getInt(4), album);
            //         } catch (SQLException e) {
            //             System.err.println(e.getErrorCode() + " : " + e.getMessage());
            //         }
            //     });
            // });

            // String sql = "SELECT * FROM music.albumview WHERE artist_name = ?";
            // PreparedStatement ps = connection.prepareStatement(sql);
            // ps.setString(1, "Bob Dylan");
            // ResultSet resultSet = ps.executeQuery();
            // printResultSet(resultSet);

            // Functionの呼び出し
            CallableStatement csf = connection.prepareCall("{ ? = CALL music.calcAlbumLength(?) }");
            csf.registerOutParameter(1, Types.DOUBLE);
            albums.forEach((artist, albumMap) -> {
                albumMap.keySet().forEach((albumName) -> {
                    try {
                        csf.setString(2, albumName);
                        csf.execute();
                        double result = csf.getDouble(1);
                        System.out.printf("Length of %s is %.1f%n", albumName, result);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                });
            });

        }catch (SQLException e){
            e.printStackTrace();
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

    // ResultSetを標準出力する
    private static void printResultSet(ResultSet resultSet) throws SQLException{
        var meta = resultSet.getMetaData();
        System.out.println("=".repeat(100));

        // カラム名を出力
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            System.out.printf("%-20s", meta.getColumnName(i).toUpperCase());
        }
        System.out.println();

        // 各レコードを出力
        while (resultSet.next()) {
            for (int i = 1; i <= meta.getColumnCount(); i++){
                System.out.printf("%-20s", resultSet.getString(i));
            }
            System.out.println();
        }
    }
}
