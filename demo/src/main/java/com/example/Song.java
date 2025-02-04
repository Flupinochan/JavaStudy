package com.example;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="songs")
public class Song {

    @Id
    @Column(name="song_id")
    private int songId;

    @Column(name="song_title")
    private String songTitle;

    @Column(name="track_number")
    private int trackNumber;

    public Song() {
    }

    public String getSongTitle() {
        return songTitle;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Song{");
        sb.append("songId=").append(songId);
        sb.append(", songTitle=").append(songTitle);
        sb.append(", trackNumber=").append(trackNumber);
        sb.append('}');
        return sb.toString();
    }

}
