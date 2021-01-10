public class Song {

    private String name;
    private int minutes;
    private int seconds;

    public Song(String name, int minutes, int seconds) {
        this.name = name;
        this.minutes = minutes;
        this.seconds = seconds;
    }


    public String getName() {
        return name;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public String getSong(){
        return getName() + "   (" + getMinutes() + ":" + getSeconds() + ")";
    }


}
