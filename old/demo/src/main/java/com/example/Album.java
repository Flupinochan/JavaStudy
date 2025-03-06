package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="albums")
public class Album implements Comparable<Album>{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="album_id")
    private int albumId;

    @Column(name="album_name")
    private String albumName;

    @OneToMany
    @JoinColumn(name="album_id")
    private List<Song> playList = new ArrayList<>();

    public Album() {
    }

    public Album(String albumName) {
        this.albumName = albumName;
    }

    public Album(int albumId, String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<Song> getPlayList() {
        return this.playList;
    }

    @Override
    public String toString() {
        this.playList.sort(Comparator.comparing(Song::getTrackNumber));
        StringBuilder sb = new StringBuilder();
        for (Song song: playList) {
            sb.append("\n\t").append(song);
        }
        sb.append("\n");
        return "album [albumId=" + this.albumId + ", albumName=" + this.albumName + "songs=" + sb + "]";
    }

    // オブジェクト比較用メソッドを追加
    @Override
    public int compareTo(Album o) {
        return this.albumName.compareTo(o.getAlbumName());
    }

}
