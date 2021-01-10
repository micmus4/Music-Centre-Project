import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Main {

    private static ArrayList<Album> albums = new ArrayList<Album>(); // 'albums' contains an array of album and its songs.
    private static Scanner scanner = new Scanner(System.in);
    private static LinkedList<Song> playlist = new LinkedList<Song>(); // 'playlist' contains songs that you've chosen.


    public static void main(String[] args) {

        begin(); // adds some Albums and Songs to start with
        Album album;
        int minutes, seconds;
        int choice, index;
        String name, artist;


        while (true) {
            choice = 0;
            index = 0;
            System.out.println("\n\t0. Quit program.");
            System.out.println("\t1. Albums.");
            System.out.println("\t2. Playlist.");
            System.out.println("\t3. Songs.\n");
            System.out.print("Choose: ");
            int action = intValidation();
            System.out.println();

            if(action == 0){
                System.out.println("Quitting program...");
                return;
            }

            // ALBUMS
            if (action == 1) {
                showAlbumOptions();
                boolean albumLoop = true;
                while (albumLoop) {
                    System.out.println("(6 - to show options)");
                    System.out.print("Choose: ");
                    int newAction = intValidation();
                    switch (newAction) {

                        // closing Album management.
                        case 0:
                            System.out.println("Back to main menu...\n");
                            albumLoop = false;
                            break;


                        // adding new Album.
                        case 1: //
                            System.out.print("Enter artist/band name: ");
                            artist = scanner.nextLine();
                            System.out.print("Enter album's name: ");
                            name = scanner.nextLine();
                            if (addAlbum(name, artist)) {
                                System.out.println("Album " + name + " by " + artist + " was successfully added!\n");
                            } else {
                                System.out.println("Error! There is already album of the same name in our base...\n");
                                showAlbumOptions();
                            }
                            break;


                        // removing Album.
                        case 2:
                            System.out.print("Enter the name of the album that you wish to be removed: ");
                            name = scanner.nextLine();
                            album = findAlbum(name);
                            if (album == null) {
                                System.out.println("Error! " + name + " was not found in our base!");
                                showAlbumOptions();
                                break;
                            } else {
                                System.out.println("Is that the album that you wish to modify?\n");
                                System.out.println("\t" + album.getName() + " by " + album.getArtist() + "\n");
                            }
                            while (true) {
                                System.out.println("(1) - Yes    /    (2) - No");
                                System.out.print("Choose: ");
                                choice = intValidation();
                                if (choice == 2) {
                                    showAlbumOptions();
                                    break;
                                } else if (choice == 1) {
                                    if (removeAlbum(name)) {
                                        System.out.println("Album was successfully removed!");
                                    } else {
                                        continue;
                                    }
                                    break;
                                }
                            }
                            break;


                        // modification of Album.
                        case 3:
                            System.out.print("Enter the name of the album that you wish to modify: ");
                            name = scanner.nextLine();
                            index = findIndexOfAlbum(name);
                            if (index >= 0) {
                                System.out.println("Is that the album that you wish to modify?\n");
                                System.out.println("\t" + albums.get(index).getName() + " by " + albums.get(index).getArtist() + "\n");
                                while (true) {
                                    System.out.println("(1) - Yes    /    (2) - No");
                                    System.out.print("Choose: ");
                                    choice = intValidation();
                                    if (choice == 2) {
                                        showAlbumOptions();
                                        break;
                                    } else if (choice == 1) {
                                        if (modifyAlbum(index)) {
                                            System.out.println("Album was successfully modified!");
                                        } else {
                                            System.out.println("Error! Album with such name is already in the base!");
                                            showAlbumOptions();
                                        }
                                        break;
                                    }
                                }
                            }
                            break;





                        // showing existing Albums.
                        case 4:
                            showAlbums();
                            break;


                        // shows Songs that are in a specific Album.
                        case 5:
                            System.out.print("Enter name of the album that you wish to browse: ");
                            name = scanner.nextLine();
                            if (!displayAlbum(name)) {
                                System.out.println("Error! There is no such album in our base!");
                            }
                            break;


                        // available options in Album section.
                        case 6:
                            showAlbumOptions();
                            break;


                    }
                } // END OF ALBUMS.


                  // PLAYLISTS.
            } else if (action == 2) {

                showPlaylistOptions();
                boolean playlistLoop = true;
                while(playlistLoop){
                    System.out.println("(6 - to show options)");
                    System.out.print("Choose: ");
                    int playListAction = intValidation();
                    switch (playListAction){


                        // comes back to main menu.
                        case 0:
                            System.out.println("Back to main menu...\n");
                            playlistLoop = false;
                            break;


                        // adding Songs from Album to Playlist.
                        case 1:
                            System.out.print("Enter album name: ");
                            name = scanner.nextLine();
                            album = findAlbum(name);
                            if(album == null){
                                System.out.println("Album " + name + " is not in our base");
                                showPlaylistOptions();
                                break;
                            }
                            displayAlbum(name);
                            boolean end = false;
                            while(true) {
                                if (end){
                                    System.out.println("Do you want to continue adding songs?\n");
                                    System.out.println("\t1. Yes");
                                    System.out.println("\t2. No");
                                    choice = intValidation();
                                    if(choice == 2){
                                        System.out.println("Going back to playlist menu...\n");
                                        break;
                                    } if (choice != 1){
                                        System.out.println("Invalid choice\n");
                                        continue;
                                    }
                                }
                                System.out.println("Which song do you want to add?\n");
                                System.out.print("Enter song name: ");
                                String songName = scanner.nextLine();
                                Song song = album.findSong(songName);
                                if (song == null) {
                                    System.out.println("Error! There is no song " + songName + " in the album " + album.getName());
                                    end = true;
                                    showPlaylistOptions();
                                    continue;
                                }
                                if(isSongOnThePlayList(song)){
                                    System.out.println("This song is already in the playlist!");
                                    end = true;
                                    continue;
                                }
                                addToPlayList(song);
                                System.out.println(song.getName() + " was successfully added to the playlist");
                                end = true;
                            }
                            break;


                        // removes Song from Playlist.
                        case 2:
                            System.out.print("Enter the name of the song that you wish to remove from the playlist: ");
                            String songName = scanner.nextLine();
                            Song song = findSongInThePlaylist(songName);
                            if(song == null){
                                System.out.println("Error! There is no song " + songName + " in the playlist");
                                showPlaylistOptions();
                                break;
                            }
                            while(true) {
                                System.out.println("Is that the song that you wish to remove?:\n");
                                System.out.println("\t" + song.getSong() + "\n");
                                System.out.println("\t 1. Yes\n\t 2. No\n");
                                int removeChoice = intValidation();
                                if (removeChoice == 2) {
                                    showPlaylistOptions();
                                    break;
                                } else if (removeChoice != 1) {
                                    System.out.println("Enter either 1 or 2.\n");
                                } else {
                                    removeSongFromPlaylist(song);
                                    System.out.println(song.getName() + " was successfully removed from the playlist.");
                                    break;
                                }
                            }
                            break;



                        // swaps Songs in Playlist.
                        case 3:
                            displayPlaylist();
                            System.out.println("\nWhich tracks do you want to swap?\n");
                            System.out.print("\tIndex of track #1: ");
                            int trackOne = intValidation();
                            trackOne--;
                            Song song1 = findSongInThePlaylist(trackOne);
                            if(song1 == null){
                                System.out.println("Error! There is no song with index " + (trackOne + 1)  + "  in the playlist.\n");
                                showPlaylistOptions();
                                break;
                            }
                            System.out.print("\tIndex of track #2: ");
                            int trackTwo = intValidation();
                            trackTwo--;
                            Song song2 = findSongInThePlaylist(trackTwo);
                            if (song2 == null){
                                System.out.println("Error! There is no song with index " + (trackTwo + 1) + "  in the playlist.\n");
                                showPlaylistOptions();
                                break;
                            }
                            swapSongs(trackOne, trackTwo, song1, song2);
                            System.out.println("Songs were successfully swapped in the playlist.");
                            break;

                        case 4:
                            playPlaylist();
                            break;

                        // displaying Playlist.
                        case 5:
                            displayPlaylist();
                            break;


                        // shows Playlist options.
                        case 6:
                            showPlaylistOptions();
                    }

                }
                // END OF PLAYLIST.


                // SONGS.
            } else if (action == 3) {

                showSongOptions();
                boolean songLoop = true;
                while (songLoop) {
                    System.out.println("(3 - to show options)");
                    System.out.print("Choose: ");
                    choice = intValidation();
                    switch (choice) {


                        // closing Song management.
                        case 0:
                            System.out.println("Back to main menu...\n");
                            songLoop = false;
                            break;


                        // add Song to a specific Album.
                        case 1:
                            System.out.print("Enter album name that you wish to add song to: ");
                            name = scanner.nextLine();
                            album = findAlbum(name);
                            if (album == null) {
                                System.out.println(name + " album is not in our base...");
                                showSongOptions();
                                break;
                            } else {
                                System.out.print("Enter name of the song: ");
                                String songName = scanner.nextLine();
                                while (true) {
                                    System.out.println("Enter duration  of the song:\n");
                                    System.out.print("\t Minutes: ");
                                    minutes = intValidation();
                                    if (minutes < 0 || minutes > 59) {
                                        System.out.println("Error! Please enter number from 0 to 59.");
                                        continue;
                                    } else {
                                        System.out.print("\t Seconds: ");
                                        seconds = intValidation();
                                        if (seconds < 0 || seconds > 59) {
                                            System.out.println("Error! Please enter number from 0 to 59.");
                                            continue;
                                        }
                                        break;
                                    }
                                }
                                if (addSong(album, songName, minutes, seconds)) {
                                    System.out.println(songName + " was successfully added to " + album.getName());
                                } else {
                                    System.out.println("Error! There is already song " + songName + " in album "
                                            + album.getName() + ".");
                                }
                                break;
                            }


                            // removing Song from an Album.
                        case 2:
                            System.out.print("Enter album name that you wish to remove song from: ");
                            name = scanner.nextLine();
                            album = findAlbum(name);
                            if (album == null) {
                                System.out.println(name + " album is not in our base...");
                                showSongOptions();
                                break;
                            } else {
                                System.out.print("Enter name of the song: ");
                                String songName = scanner.nextLine();
                                Song song = album.findSong(songName);
                                System.out.println("Song: " + song.getSong() + " in album " + album.getName());
                                if (removeSong(album, songName)) {
                                    System.out.println("Song " + songName + " was successfully removed from album "
                                            + album.getName() + ".");
                                } else {
                                    System.out.println("Error! There is no song " + songName + " in album "
                                            + album.getName() + ".");
                                }
                            }
                            break;


                            // showing Song options.
                        case 3:
                            showSongOptions();
                            break;
                    }
                }
            }

            // in case someone entered number that is not 1, 2 or 3.
            else{
                System.out.println("Invalid choice.");
                break;
            }

        }
    }


    // adds some Albums and Songs.
    public static void begin(){
        addAlbum("Rockstar", "White2115");
        addAlbum("Mlody Ksiaze", "White2115");
        addSong(findAlbum("Rockstar"), "California", 4, 20);
        addSong(findAlbum("Rockstar"), "Gubie kroki", 2, 25);
        addSong(findAlbum("Rockstar"), "Moge dzis umierac", 1, 46);
        addSong(findAlbum("Mlody Ksiaze"), "GTR", 0, 59);
        addSong(findAlbum("Mlody Ksiaze"), "Morgan", 3, 17);

    }





    // validates correct input of Integer.
    public static int intValidation(){
        boolean isCorrect = scanner.hasNextInt();
        if(isCorrect){
            int action = scanner.nextInt();
            scanner.nextLine(); // enter
            return action;
        } else {
            System.out.println("Invalid value! Please try again...");
            scanner.next();
            return intValidation();
        }
    }



    // shows options in Album section of the program.
    public static void showAlbumOptions(){
        System.out.println("\t0. Back to main menu.");
        System.out.println("\t1. Add a new album.");
        System.out.println("\t2. Remove album.");
        System.out.println("\t3. Modify album.");
        System.out.println("\t4. Show albums.");
        System.out.println("\t5. List songs of the album.");
        System.out.println("\t6. Show options.\n");
    }



    // returns Album (or null if it does not exist) when given a name.
    public static Album findAlbum(String name){
        for(int i = 0; i < albums.size(); i++){
            Album album = albums.get(i);
            if(album.getName().equals(name)){
                return album;
            }
        }
        return null;
    }



    // returns index of an Album (or -1 if it does not exist) when given a name.
    public static int findIndexOfAlbum(String name){
        for(int i = 0; i < albums.size(); i++){
            Album album = albums.get(i);
            if(album.getName().equals(name)){
                return i;
            }
        }
        return -1;
    }



    // adds new Album as long as there is no other with the same name.
    public static boolean addAlbum(String name, String artist){
        if(findAlbum(name) == null){
            Album album = new Album(name, artist);
            albums.add(album);
            return true;
        }
        return false;
    }



    // removes Album as long as it exists.
    public static boolean removeAlbum(String name){
        if(findAlbum(name) != null){
            albums.remove(findAlbum(name));
            return true;
        } else {
            return false;
        }
    }




    // modifies Album when given its index.
    public static boolean modifyAlbum(int index){
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        if(findAlbum(name) != null){
            return false; // Album with that name is already in the base.
        } else {
            System.out.print("Enter new artist/brand: ");
            String artist = scanner.nextLine();
            albums.set(index, new Album(name, artist));
            return true; // Album successfully modified.

        }
    }




    // displays Songs of a specific Album.
    public static boolean displayAlbum(String name){
        Album album = findAlbum(name);
        if(album == null){
            return false;
        } else {
            ArrayList<Song> songs = album.getSongs();
            if(songs.size() == 0){
                System.out.println("There are no songs in this album...\n");
                return true;
            }
            System.out.println("List of songs:\n");
            for(int i = 0; i < songs.size(); i++){
                Song song = songs.get(i);
                System.out.println("\t" + (i+1) + ". " + song.getSong());
            }
            System.out.println();
            return true;
        }
    }



    // shows list of Albums.
    public static void showAlbums(){
        if(albums.size() == 0){
            System.out.println("There are no albums at the moment.");
            return;
        } else {
            System.out.println("Albums:\n");
        }
        for(int i = 0; i < albums.size(); i++){
            Album album = albums.get(i);
            System.out.println("\t" + (i+1) + ". " + album.getName() + " by " + album.getArtist());
        }
        System.out.println();
    }


    // adds Song into Album.
    public static boolean addSong(Album album, String songName, int minutes, int seconds){
        if(album.addSong(songName, minutes, seconds)) {
            return true;
        } else {
            return false;
        }
    }


    // removes Song from an Album.
    public static boolean removeSong(Album album, String songName){
        if(album.removeSong(songName)){
            return true;
        }
        return false;
    }


    // shows options in Album section of the program.
    public static void showSongOptions(){
        System.out.println("\t0. Back to main menu.");
        System.out.println("\t1. Add a new song.");
        System.out.println("\t2. Remove song.");
        System.out.println("\t3. Show options.\n");
    }


    // shows options in Playlist section of the program.
    public static void showPlaylistOptions(){
        System.out.println("\t0. Back to main menu.");
        System.out.println("\t1. Add a new song to playlist.");
        System.out.println("\t2. Remove song from playlist.");
        System.out.println("\t3. Change song positions.");
        System.out.println("\t4. Play playlist.");
        System.out.println("\t5. Display playlist.");
        System.out.println("\t6. Show options.\n");

    }


    // checks whether the Song is already in the Playlist.
    public static boolean isSongOnThePlayList(Song song){
        ListIterator<Song> Iterator = playlist.listIterator();
        while(Iterator.hasNext()){
            if(Iterator.next().equals(song)){
                return true;
            }
        }
        return false;
    }


    // finds Song in the Playlist when given its name and returns it.
    public static Song findSongInThePlaylist(String songName){
        ListIterator<Song> Iterator = playlist.listIterator();
        while(Iterator.hasNext()){
            Song song = Iterator.next();
            if(song.getName().equals(songName)){
                return song;
            }
        }
        return null;
    }


    // finds Song in the Playlist when given its index and returns it.
    public static Song findSongInThePlaylist(int index){
        ListIterator<Song> Iterator = playlist.listIterator();
        int matchIndex = 0;
        while(Iterator.hasNext()){
            Song song = Iterator.next();
            if(matchIndex == index){
                return song;
            }
            matchIndex++;
        }
        return null;
    }


    // displays Playlist.
    public static void displayPlaylist(){
        ListIterator<Song> Iterator = playlist.listIterator();
        int i = 1;
        System.out.println();
        while (Iterator.hasNext()){
            Song song = Iterator.next();
            System.out.println("\t" + i + ". " + song.getSong());
            i++;
        }
        System.out.println();
    }


    // adds Song into the Playlist.
    public static void addToPlayList(Song song){
        playlist.add(song);
    }


    // removes Song from Playlist.
    public static void removeSongFromPlaylist(Song song){
        playlist.remove(song);
    }



    // Swaps two Songs in Playlist with its places.
    public static void swapSongs(int index1, int index2, Song song1, Song song2){
        playlist.set(index1, song2);
        playlist.set(index2, song1);
    }



    // plays Playlist.
    public static void playPlaylist(){
        if(playlist.isEmpty()){ // checks whether the Playlist is empty.
            System.out.println("There are no songs in playlist. Go back and add some!");
            return;
        }
        ListIterator<Song> Iterator = playlist.listIterator();
        int whichSong = -1; // Number of track that is being currently played.
        System.out.println("\nNow playing:   " + Iterator.next().getSong() + "\n");
        whichSong++;
        boolean playlistLoop = true;
        boolean rightDirection = true;
        int playlistAction;
        while(playlistLoop){
            System.out.println("\t0. Quit playlist.");
            System.out.println("\t1. Play next song.");
            System.out.println("\t2. Play previous song.");
            System.out.println("\t3. Choose song.");
            System.out.println("\t4. Display playlist.\n");
            System.out.print("Choose: ");
            playlistAction = intValidation();
            switch (playlistAction){

                case 0:
                    System.out.println("Leaving playlist...");
                    return;


                // playing next Song in Playlist.
                case 1:
                    if(!rightDirection){
                        if(Iterator.hasNext()){
                            Iterator.next();
                        }
                        rightDirection = true;
                    }
                    if(Iterator.hasNext()){
                        System.out.println("\nNow playing:   " + Iterator.next().getSong() + "\n");
                        whichSong++;
                    } else {
                        System.out.println("You are at the end of the playlist.");
                    }
                    break;



                // playing previous Song in Playlist.
                case 2:

                    if(rightDirection){
                        if(Iterator.hasPrevious()){
                            Iterator.previous();
                        }
                        rightDirection = false;
                    }
                    if(Iterator.hasPrevious()){
                        System.out.println("\nNow playing:   " + Iterator.previous().getSong() + "\n");
                        whichSong--;
                    } else {
                        System.out.println("You are at the beginning of the playlist.");
                    }
                    break;



                // playing Song in Playlist when given its track.
                case 3:
                    int trackNumber; // number of track that you want to be played
                    while(true) {
                        System.out.print("Enter track number: ");
                        trackNumber = intValidation();
                        if (trackNumber < 1 || trackNumber > playlist.size()) {
                            System.out.println("Choose number from 1 to " + playlist.size() + ".");
                            continue;
                        }
                        trackNumber--;
                        break;
                    }

                    if(whichSong == trackNumber){
                        System.out.println("This track is currently being played.\n");
                        break;
                    }

                    if (whichSong < trackNumber){
                        while (whichSong + 1 != trackNumber){
                            if(!rightDirection){
                                if(Iterator.hasNext()){
                                    Iterator.next();
                                }
                                rightDirection = true;
                            }
                            Iterator.next();
                            whichSong++;
                        }
                        System.out.println("\nNow playing:   " + Iterator.next().getSong() + "\n");
                        whichSong++;

                    } else if (whichSong > trackNumber) {
                        while(whichSong - 1 != trackNumber){
                            if(rightDirection){
                                if(Iterator.hasPrevious()){
                                    Iterator.previous();
                                }
                                rightDirection = false;
                            }
                            Iterator.previous();
                            whichSong--;
                        }
                        System.out.println("\nNow playing:   " + Iterator.previous().getSong() + "\n");
                        whichSong--;
                    }
                    break;

                // displaying Playlist.
                case 4:
                    displayPlaylist();
                    break;
            }
        }
    }
}


