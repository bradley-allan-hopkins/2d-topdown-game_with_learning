/**
 * Title: PlaySound.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 16, 2022
 */
package ai.statemachine.actions;


import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import sounds.MasterVolume;
import sounds.SoundPlayer;

/**
 * The Class PlaySound. This class will play wav sound files as an action
 */
public class PlaySound extends Action {

	/** flag if sound has played*/
	boolean played = false;
	
	/** The player. */
	SoundPlayer player;
	
	/** The file name. */
	String fileName;
	
	/** The micro conversion 1 Second to MICRO. */
	final long MICRO = 1000000;
	
	/** The length. */
	Long length;
	
	/**
	 *  Start time.
	 */
	Long start;
	
	/**
	 * The volume.
	 */
	float volume;
	
	
	
	/**
	 * Play sound. THis will start the song at a specific time and end at a end time
	 *
	 * @param fileName the file name
	 * @param seconds the seconds - how long to play for
	 * @param startTime - when to start the sound
	 * @param volume the volume
	 */
	public PlaySound(String fileName, float seconds, float startTime,float volume) {
		this(fileName);
		this.length = (long) (seconds * MICRO);
		this.start = (long) (startTime * MICRO);
		this.volume = volume;
	}
	
	/**
	 * Play sound. THis will start the song at a specific time and end at a end time
	 *
	 * @param fileName the file name
	 * @param seconds the seconds - how long to play for
	 * @param startTime - when to start the sound
	 */
	public PlaySound(String fileName, float seconds, float startTime) {
		this(fileName);
		this.length = (long) (seconds * MICRO);
		this.start = (long) (startTime * MICRO);
	}
	
	/**
	 * Play sound. This will play the sound until a time in microSeconds
	 *
	 * @param fileName the file name
	 * @param length the length
	 */
	public PlaySound(String fileName, Long length) {
		this(fileName);
		this.length = length;
	}
	
	/**
	 * Play sound.
	 *
	 * @param fileName the file name
	 * @param volume the volume
	 */
	public PlaySound(String fileName, float volume) {
		this(fileName);
		this.volume = volume;
	}
	
	/**
	 * Play sound. This will play the complete sound
	 *
	 * @param fileName the file name
	 */
	public PlaySound(String fileName) {
		this.fileName = fileName;
		player = new SoundPlayer();
		volume = 90;
		player.load(fileName);
	}
	

	@Override
	public void performAction(float elapsedTime) {
		
		if (player.isLoaded() && player.clip.getMicrosecondPosition() 
				>= player.clip.getMicrosecondLength()) {
			//close clip 
			//System.out.println("closing");
			try {
				player.stop();
			} catch (UnsupportedAudioFileException | IOException
					| LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//player.clip.close();
		}
		//System.out.println("CLIP LOADED:" + player.isLoaded());
		if (!player.isLoaded()) {
			//player.load(fileName);
			if (start != null) player.clip.setMicrosecondPosition(start);
			//set the volume and play the clip
			player.setVolume(volume * MasterVolume.soundEffects);
			player.play();
			played = true;
		}
		
		//System.out.println(player.getCurrentTime());
		if (length != null && player.isTimeReached(length))
			try {
				//stop clip and close the file 
				player.stop();
				player.clip.close();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
	}

}
