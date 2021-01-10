import java.util.ArrayList;

public class Album {

    private String artist;
    private String name;
    private ArrayList<Song> songs;

    public Album(String name, String artist) {
        this.artist = artist;
        this.name = name;
        this.songs = new ArrayList<Song>();
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public Song findSong(String name){
        for(int i = 0; i < songs.size(); i++){
            Song song = songs.get(i);
            if(song.getName().equals(name)){
                return song;
            }
        }
        return null;
    }

    public boolean addSong(String name, int minutes, int seconds){
        if(findSong(name) == null) {
            getSongs().add(new Song(name, minutes, seconds));
            return true;
        } else {
            return false;
        }
    }

    public boolean removeSong(String songName){
        Song song = findSong(songName);
        if(song != null){
            songs.remove(song);
            return true;
        }
        return false;
    }


}
