package game;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Class: SoundEffect
 * @author Agnes Wang, Shi Liang, Teddy Wei
 * <br>Purpose: Provides a static helper method to play short sound effects in the game.
 * <br>Restrictions: Only works with supported audio formats (e.g., WAV). Does not support streaming long audio files.
 * <br>For example:
 * <pre>
 *    SoundEffect.play("/assets/sounds/pickup.wav");
 *    SoundEffect.play("/assets/sounds/collision.wav");
 * </pre>
 */
public class SoundEffect {
	
	/**
     * ensures: Plays a short audio clip from the given resource path.
     * <br>Exceptions are caught and logged without crashing the program.
     * @param soundFile the path to the audio file within the resources (e.g., "/assets/sounds/pickup.wav")
     */
	public static void play(String soundFile) {
	    try {
	        URL url = SoundEffect.class.getResource(soundFile);
	        if (url == null) {
	            System.err.println("Sound file not found: " + soundFile);
	            return;
	        }

	        try (AudioInputStream audio = AudioSystem.getAudioInputStream(url)) {
	            Clip clip = AudioSystem.getClip();
	            clip.open(audio);
	            clip.start();
	        }
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	        System.err.println("Error playing sound: " + soundFile);
	        e.printStackTrace();
	    }

	}

}

