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

public class App3{
    // Mainコード
    public static void main(String[] args) {
        Properties props = envRead();
        dbConnect(props);
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

    // SQL実行
    public static void dbConnect(Properties props){
        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));

        try (Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
        ){
            // .execute()
            // 戻り値は、boolean
            // true: 結果が ResultSet（データ取得クエリ）である場合
            // false: 結果が 更新件数（UPDATE、INSERT、DELETE など）である場合
            String artist = "Elf";
            String query = "SELECT * FROM artists WHERE artist_name='%s'".formatted(artist);
            boolean result = statement.execute(query);
            System.out.println("Result = " +result);
            // true(ResultSet)の場合は、以下でSQL実行結果を取得可能
            if(result){
                var rs = statement.getResultSet();
                if(rs != null && rs.next()){
                    System.out.println("Artist was found");
                }else{
                    System.out.println("Artist was not found");
                }
            }

            String tableName = "music.artists";
            String columnName = "artist_name";
            String columnValue = "Elf";
            if(!executeSelect(statement, tableName, columnName, columnValue)){
                System.out.println("Record Found");
                insertRecord(statement, tableName, new String[]{columnName}, new String[]{columnValue});
            }else{
                // deleteRecord(statement, tableName, columnName, columnValue);
                // updateRecord(statement, tableName, tableName, columnValue, columnName, columnValue.toUpperCase());
                try {
                    deleteArtistAlbum(connection, statement, artist, columnName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                executeSelect(statement, "music.albumview", "album_name", columnValue);
                executeSelect(statement, "music.albums", "album_name", columnValue);
            }
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    // resultSetを標準出力する
    private static boolean printRecords(ResultSet resultSet) throws SQLException{
        boolean foundData = false;
        while(resultSet.next()){
            var meta = resultSet.getMetaData();
            for(int i = 1; i <= meta.getColumnCount(); i++){
                System.out.printf("%-15s", resultSet.getString(i));
            }
            System.out.println();
            foundData = true;
        }
        return foundData;
    }

    // SQLをパラメータ化
    private static boolean executeSelect(Statement statement, String table, String columnName, String columnValue) throws SQLException{
        String query = "SELECT * FROM %s WHERE %s='%s'".formatted(table, columnName, columnValue);
        var rs = statement.executeQuery(query);
        if (rs != null) {
            return printRecords(rs);
        }else{
            return false;
        }
    }

    // Insertの実行
    private static boolean insertRecord(Statement statement, String table, String[] columnNames, String[] columnValues) throws SQLException {
        String colNames = String.join(",", columnNames);
        String colValues = String.join(",", columnValues);
        String query = "INSERT INTO %s (%s) VALUES ('%s')".formatted(table, colNames, colValues);
        System.out.println(query);
        boolean insertResult = statement.execute(query);
        // System.out.println("Insert Result = " + insertResult);
        int recordInserted = statement.getUpdateCount(); // Insertの成功判定
        if (recordInserted > 0) {
            executeSelect(statement, table, columnNames[0], columnValues[0]);
        }
        return recordInserted > 0;
    }

    // Delete
    private static boolean deleteRecord(Statement statement, String table, String columnName, String columnValue) throws SQLException{
        String query = "DELETE FROM %s WHERE %s = '%s'".formatted(table, columnName, columnValue);
        System.out.println(query);
        statement.execute(query);
        int recordsDeleted = statement.getUpdateCount(); // Deleteの成功判定
        if (recordsDeleted > 0) {
            executeSelect(statement, table, columnName, columnValue);
        }
        return recordsDeleted > 0;
    }

    // Update
    private static boolean updateRecord(Statement statement, String table,
                                        String matchedColumn, String matchedValue,
                                        String columnName, String columnValue) throws SQLException{
        String query = "UPDATE %s SET %s = '%s' WHERE %s = '%s'".formatted(table, columnName, columnValue, matchedColumn, matchedValue);
        System.out.println(query);
        statement.execute(query);
        int recordsDeleted = statement.getUpdateCount(); // Deleteの成功判定
        if (recordsDeleted > 0) {
            executeSelect(statement, table, columnName, columnValue);
        }
        return recordsDeleted > 0;
    }

    private static void deleteArtistAlbum(Connection conn, Statement statement, String artistName, String albumName) throws SQLException{
        try {
            System.out.println("AUTO_COMMIT: " + conn.getAutoCommit());

            conn.setAutoCommit(false);

            // 音楽の削除
            String deleteSongs = """
                    DELETE FROM music.songs WHERE album_id = (SELECT ALBUM_ID FROM music.albums WHERE album_name = '%s')"""
                    .formatted(albumName);
            // アルバムの削除
            String deleteAlbums = "DELETE FROM music.albums WHERE album_name = '%s".formatted(albumName);
            // アーティストの削除
            String deleteArtist = "DELETE FROM music.artists WHERE artist_name = '%s'".formatted(artistName);

            // SQLをまとめて実行するためにバッチの作成
            statement.addBatch(deleteSongs);
            statement.addBatch(deleteAlbums);
            statement.addBatch(deleteArtist);
            int[] results = statement.executeBatch();

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        }
        conn.setAutoCommit(true);
    }
}