import java.util.ArrayList;
import java.util.LinkedList;

public class Album {
    private String name;
    private String artist;
    private ArrayList<Song> songs;

    public Album(String name, String artist){
        this.name = name;
        this.artist = artist;
        this.songs = new ArrayList<Song>();
    }

    public boolean addSong(String title, double duration){
        Song song = new Song(title, duration);
        if(findSong(title) == null){
            songs.add(song);
            return true;
        }else{
            return false;
        }
    }

    private Song findSong(String title) {
        for (Song song : this.songs) {
            if (song.getTitle().equals(title)) {
                return song;
            }
        }
        return null;
    }

    public boolean addToPlayList(int trackNumber, LinkedList<Song> songs){
        for (Song song: songs){
            if (!songs.contains(song)){
                songs.add(trackNumber, song);
                return true;
            }
        }
        return false;
    }

    public boolean addToPlayList(String title, LinkedList<Song> songs){
        for (Song song: songs){
            if (!songs.contains(song)){
                if(findSong(title) != null){
                    songs.add(song);
                    return true;
                }
            }
        }
        return false;
    }
}
