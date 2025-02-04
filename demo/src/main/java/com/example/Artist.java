package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "artists")
public class Artist {
    
    @Id
    @Column(name = "artist_id")
    private int artistId;

    @Column(name = "artist_name")
    private String artistName;

    // アーティストは複数のアルバムを持つ (アーティストが親)
    // アーティストを保存すれば、アルバムも保存される (Cascade) ※アーティストもアルバムもそれぞれ変更した時は、それぞれCommitしなくてはならないが同時にCommitされるようにする
    // アーティストが削除されれば、アルバムも全て削除される (orphanRemoval)
    // artist_idが2つのテーブル(artistsとalbums)を結合するための外部キー (Foreign Key)
    @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="artist_id")
    private List<Album> albums = new ArrayList<>();

    public Artist() {
    }

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    public Artist(int artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public List<Album> getAlbums() {
        return this.albums;
    }

    public String getArtistName() {
        return this.artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void addAlbum(String albumName){
        this.albums.add(new Album(albumName));
    }

    public void removeDuplicates(){
        var set = new TreeSet<>(this.albums); // TreeSetは、Setであり、ソートされて重複が排除される
        this.albums.clear();
        this.albums.addAll(set);
    }

    @Override
    public String toString() {
        return "Artist [artistId=" + this.artistId + ", artistName=" + this.artistName + ", albums=" + this.albums + "]";
    }

}
