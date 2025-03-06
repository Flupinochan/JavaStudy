package main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class App8 {
    public static void main(String[] args) {
        try (
            var sessionFactory = Persistence.createEntityManagerFactory("main");
            EntityManager entityManager = sessionFactory.createEntityManager();
        ){
            var transaction = entityManager.getTransaction();
            transaction.begin();
            // entityManager.persist(new Artist("Muddy Water")); // レコードを新規追加
            Artist artist = entityManager.find(Artist.class, 203); // 主キー(IDが203)のレコードを取得
            System.out.println(artist);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
