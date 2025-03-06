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

import com.mysql.cj.jdbc.MysqlDataSource;
import java.awt.SystemTray;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class App5{

    private static String ARTIST_INSERT = "INSERT INTO music.artists (artist_name) VALUES (?)";
    private static String ALBUM_INSERT = "INSERT INTO music.albums (artist_id, album_name) VALUES (?, ?)";
    private static String SONG_INSERT = "INSERT INTO music.songs (album_id, track_number, song_title) VALUES (?, ?, ?)";


    public static void main(String[] args) {
        // DB接続設定
        Properties props = envRead();
        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));
        try {
            dataSource.setContinueBatchOnError(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        
        // SQL実行
        try (Connection connection = dataSource.getConnection()){
            addDataFromFile(connection);

            String sql = "SELECT * FROM music.albumview WHERE artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Bob Dylan");
            ResultSet resultSet = ps.executeQuery();
            printResultSet(resultSet);

        } catch (SQLException e) {
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

    private static int addArtist(PreparedStatement ps, Connection conn, String artistName) throws SQLException{
        int artistId = -1;
        ps.setString(1, artistName);
        int insertCount = ps.executeUpdate();
        if (insertCount > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys(); // AUTO INCREMENTによって自動生成された値(主キーのIDなど)を取得するメソッド
            if (generatedKeys.next()) {
                artistId = generatedKeys.getInt(1);
                System.out.println("Auto-incremented ID: " + artistId);
            }
        }
        return artistId;
    }

    private static int addAlbum(PreparedStatement ps, Connection conn, int artistId, String albumName) throws SQLException{
        int albumId = -1;
        ps.setInt(1, artistId);
        ps.setString(2, albumName);
        int insertCount = ps.executeUpdate();
        if (insertCount > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys(); // AUTO INCREMENTによって自動生成された値(主キーのIDなど)を取得するメソッド
            if (generatedKeys.next()) {
                albumId = generatedKeys.getInt(1);
                System.out.println("Auto-incremented ID: " + albumId);
            }
        }
        return albumId;
    }

    private static void addSong(PreparedStatement ps, Connection conn, int albumId, int trackNo, String songTitle) throws SQLException{
        ps.setInt(1, albumId);
        ps.setInt(2, trackNo);
        ps.setString(3, songTitle);
        ps.addBatch();
    }

    private static void addDataFromFile(Connection conn) throws SQLException{
        List<String> records = null;
        try {
            // Projectフォルダからの相対パス(.javaファイルからの相対パスではないことに注意)
            records = Files.readAllLines(Path.of("./data/NewAlbums.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String lastAlbum = null;
        String lastArtist = null;
        int artistId = -1;
        int albumId = -1;
        try (
            PreparedStatement psArtist = conn.prepareStatement(ARTIST_INSERT, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psAlbum = conn.prepareStatement(ALBUM_INSERT, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psSong = conn.prepareStatement(SONG_INSERT, Statement.RETURN_GENERATED_KEYS);
        ){
            conn.setAutoCommit(false);
            for (String record: records) {
                String[] columns = record.split(",");
                if (lastArtist == null || !lastArtist.equals(columns[0])) {
                    lastArtist = columns[0];
                    artistId = addArtist(psArtist, conn, lastArtist);
                }
                if (lastAlbum == null || !lastAlbum.equals(columns[1])) {
                    lastAlbum = columns[1];
                    albumId = addAlbum(psAlbum, conn, artistId, lastAlbum);
                }
                addSong(psSong, conn, albumId, Integer.parseInt(columns[2]), columns[3]);
            }
            int[] inserts = psSong.executeBatch();
            int totalInserts = Arrays.stream(inserts).sum();
            System.out.printf("%d song records added%n", inserts.length);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        }

    }
}