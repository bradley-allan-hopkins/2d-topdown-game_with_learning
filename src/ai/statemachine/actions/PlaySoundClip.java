/**
 * Title: PlaySoundClip.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.statemachine.actions;

import sounds.SoundPlayer;

/**
 * The Class PlaySoundClip.
 */
public class PlaySoundClip extends Action {

	/** played */
	boolean played;
	
	/** The player. */
	SoundPlayer player;
	
	/** The file name. */
	String fileName;
	
	/** The micro conversion 1 Second to MICRO. */
	final long MICRO = 1000000;
	
	/** The length. */
	Long length;
	
	/** the volume */
	float volume;
	
	/**
	 * Play sound.
	 *
	 * @param fileName the file name
	 * @param volume the volume
	 */
	public PlaySoundClip(String fileName, float volume) {
		this.volume = volume;
		this.fileName = fileName;
		this.player = new SoundPlayer();
		player.load(fileName);
	}
	

	@Override
	public void performAction(float elapsedTime) {
		if (!played) {
			player.play();
			player.setVolume(volume);
			played = true;
		}
	}

}
