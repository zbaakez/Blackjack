package Model;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Music class is used to handle the playing Music
 * @author Busted
 */
public class Music {

    public static AudioClip audioPlayer;
    private static String lastSong="s"; // so the same song doesnt play twice

    /*  Cant get this to work
    public static void muteMusic(){
        audioPlayer.setVolume(0);
    }*/
    /**
     * Method that stops the playing Music
     */
    public static void stopMusic(){
        audioPlayer.stop();
    }
    /**
     * Method that resumes the playing Music
     */
    public static void resumeMusic(){
        File directory;
        switch (Data.valueMap.get("szene")) {
            case 2 -> directory = new File("resources/music/tirol");
            case 3 -> directory = new File("resources/music/beach");
            case 4 -> directory = new File("resources/music/universe");
            default -> directory = new File("resources/music/casino"); //case 1, so directory is always initialized
        }

        if (directory.length()==0){
            Data.valueMap.put("sound", 0); //sound off
            return;
        }
        audioPlayer.play();
    }

    /**
     * Method that starts the Music
     * gets source for song/audio out of musicHandler() method
     */
    public static void playMusic() {

        //switch through theme to get corresponding path
        File directory;
        switch (Data.valueMap.get("szene")) {
            case 2 -> directory = new File("resources/music/tirol");
            case 3 -> directory = new File("resources/music/beach");
            case 4 -> directory = new File("resources/music/universe");
            default -> directory = new File("resources/music/casino"); //case 1, so directory is always initialized
        }

        if (directory.length()==0){
            Data.valueMap.put("sound", 0); //sound off
            return;
        }

        if(Data.valueMap.get("sound") == 1) {
            String audioPath = musicHandler();
            assert audioPath != null;
            Media hit = new Media(Paths.get(audioPath).toUri().toString());

            audioPlayer = new AudioClip(hit.getSource());
            audioPlayer.setVolume(0.021);
            audioPlayer.play();
        }

    }

    /**
     * Method that handles the Music
     * checks theme and plays music corresponding to the theme
     * plays random music of corresponding path
     * @return path of file that should be played as String
     */
    private static String musicHandler(){

        File[] files;
        Random rand = new Random();
        File directory;

        //switch through theme to get corresponding path
        switch (Data.valueMap.get("szene")) {
            case 2 -> directory = new File("resources/music/tirol");
            case 3 -> directory = new File("resources/music/beach");
            case 4 -> directory = new File("resources/music/universe");
            default -> directory = new File("resources/music/casino"); //case 1, so directory is always initialized
        }

        if (directory.length()==0){
            Data.valueMap.put("sound", 0); //sound off
        }

        files=directory.listFiles(); // gets all files of directory
        if(files == null){
            Data.valueMap.put("sound", 0); //sound off
            audioPlayer.stop();
        }
        else if(files != null) {
            while (true) { //shuffle through files (music)
                try {
                    File file = files[rand.nextInt(files.length)];
                    if (file.getName().endsWith(".mp3") && (!file.getName().equals(lastSong) || files.length == 1)) {//viable file to play
                        lastSong = file.getName(); // so the same song doesnt play twice
                        return file.getPath();
                    }
                }
                catch (Exception e){
                    Data.valueMap.put("sound", 0); //sound off
                }
            }
        }
        return null;
    }

}
