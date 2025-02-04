package com.example;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.EntityManagerFactory;

// JPQL Query: SQLに近いがオブジェクトを利用する
// CriteriaBuilder Query: チェーンして組み立てる
// Native Query: 普通のSQL

public class MainQuery {
    public static void main(String[] args) {
        List<Artist> artists = null;
        try (
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example");
            EntityManager em = emf.createEntityManager();
        ){
            var transaction = em.getTransaction();
            transaction.begin();
            // artists = getArtistsJPQL(em, "%Greatest Hits%");
            // artists.forEach(System.out::println);

            Stream<Artist> streamArtists = getArtistsSQL(em, "Bl%");
            // Stream<Artist> streamArtists = getArtistsBuilder(em, "Bl%");

            // StreamからTreeMap(Dict)を作成する
            // キーはgetArtistName、値はgetAlbums().size()で、Albums配列のサイズ
            // Integer::sumはオプションで、同じgetArtistNameは合計して1つにする
            var map = streamArtists.limit(10).collect(Collectors.toMap(Artist::getArtistName, (a) -> a.getAlbums().size(), Integer::sum, TreeMap::new));
            map.forEach((k, v) -> System.out.println(k + ": " + v));

            // var names = getArtistsNames(em, "%Stev%");
            // names.forEach(System.out::println);
            // names.map(a -> new Artist(a.get("id", Integer.class), (String) a.get("name"))).forEach(System.out::println); // jpqlのasで指定したidとnameエイリアス名を取得
            transaction.commit(); // Queryだけの場合もCommitしておく
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static List<Artist> getArtistsJPQL(EntityManager em, String matchedValue){
        // String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE :partialName"; // Artistクラスを取得
        // String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE ?1"; // :変数名 もしくは ?数値 のどちらかでパラメータを利用可能
        String jpql = "SELECT artist FROM Artist as artist JOIN albums as album WHERE album.albumName LIKE ?1 OR album.albumName LIKE ?2"; // Artistとalbumsを取得
        var query = em.createQuery(jpql, Artist.class);
        query.setParameter(1, matchedValue);
        query.setParameter(2, "%Best Of%");
        return query.getResultList();
    }

    // private static List<Tuple> getArtistsNames(EntityManager em, String matchedValue){
    // Streamは、Listを効率よく扱うためのラップ
    private static Stream<Tuple> getArtistsNames(EntityManager em, String matchedValue){
        // String jpql = "SELECT a.artistName FROM Artist a WHERE a.artistName LIKE ?1";
        String jpql = "SELECT a.artistId as id, a.artistName as name FROM Artist a WHERE a.artistName LIKE ?1";
        // var query = em.createQuery(jpql, String.class); // a.artistNameの場合はAritstクラスではなく、String文字列のListが取得される
        var query = em.createQuery(jpql, Tuple.class); // a.artistIdとa.artistNameの2つ以上のカラムを取得する場合は、JPA Tupleを使用すると良い
        query.setParameter(1, matchedValue);
        // return query.getResultList();
        return query.getResultStream();
    }

    // CriteriaBuilderによるクエリ
    private static Stream<Artist> getArtistsBuilder(EntityManager em, String matchedValue){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Artist> criteriaQuery = builder.createQuery(Artist.class);
        Root<Artist> root = criteriaQuery.from(Artist.class); // FROM Artist
        criteriaQuery
                    .select(root)
                    .where(builder.like(root.get("artistName"), matchedValue))
                    .orderBy(builder.asc(root.get("artistName")));
        return em.createQuery(criteriaQuery).getResultStream();
    }

    // NativeQuery
    private static Stream<Artist> getArtistsSQL(EntityManager em, String matchedValue){
        var query = em.createNativeQuery("SELECT * FROM music.artists WHERE artist_name LIKE ?1", Artist.class);
        query.setParameter(1, matchedValue);
        return query.getResultStream();
    }
}
