package com.example;

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

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        Properties props = readDBInformation();
        MysqlDataSource dataSource = settingMysqlDataSource(props);

        try(Connection connection = dataSource.getConnection();){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM music.albumview LIMIT 5";
            ResultSet resultSet = statement.executeQuery(sql); 
            // printResultSet(resultSet);

            // JPA (Hibernate)
            // コードの書き方は基本的にどのプロバイダーでも同じであり、裏側でどのプロバイダーで処理するか、が変わるだけ
            try (
                var sessionFactory = Persistence.createEntityManagerFactory("com.example");
                EntityManager entityManager = sessionFactory.createEntityManager();
            ){
                var transaction = entityManager.getTransaction();
                transaction.begin();

                // entityManager.persist(new Artist("Muddy Water")); // INSERT文が実行されて、Muddy WaterアーティストがDBに作成される
                Artist artist = entityManager.find(Artist.class, 103); // SELECT文が主キーのID=202で実行される
                // artist.setArtistName("Muddy Waters"); // UPDATE文が実行される
                // entityManager.remove(artist); // DELETE文が実行される
                // entityManager.merge(artist); // Artistの状態をDBに反映する(UPDATE)
                System.out.println(artist);
                // artist.addAlbum("The Best of Muddy Waters"); .add()メソッドを使用して追加するメソッドをArtistクラスに定義して利用
                // artist.removeDuplicates(); // オブジェクトからartistを削除して、commit()すれば、オブジェクトの状態がDBに同期される remove()を使用しなくても消せる
                // System.out.println(artist);
                transaction.commit();
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    // DB接続用環境変数をファイルから読み込む
    public static Properties readDBInformation(){
        Properties props = new Properties();
        try{
            String filePath = "./music.properties";
            props.load(Files.newInputStream(Path.of(filePath), StandardOpenOption.READ));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return props;
    }

    // DB接続設定、dataSourceを返す
    public static MysqlDataSource settingMysqlDataSource(Properties props){
        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));
        return dataSource;
    }

    // ResultSetをフォーマットして標準出力する
    public static void printResultSet(ResultSet resultSet) throws SQLException{
        var meta = resultSet.getMetaData();
        System.out.println();
        System.out.println("=".repeat(100));
        // カラム名を出力
        for(int i = 1; i <= meta.getColumnCount(); i++){
            System.out.printf("%-20s", meta.getColumnName(i).toUpperCase());
        }
        System.out.println();
        // 各レコードを出力
        while(resultSet.next()){
            for (int i = 1; i <= meta.getColumnCount(); i++){
                System.out.printf("%-20s", resultSet.getString(i));
            }
            System.err.println();
        }
    }
}