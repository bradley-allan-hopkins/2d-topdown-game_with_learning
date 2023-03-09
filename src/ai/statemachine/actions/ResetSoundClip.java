/**
 * Title: ResetSoundClip.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.statemachine.actions;

/**
 * The Class ResetSoundClip. The action resets the sound clip so that it can play again. 
 */
public class ResetSoundClip extends Action {

	/** The player. */
	PlaySoundClip player;
	
	/**
	 * Reset sound clip.
	 *
	 * @param player the player
	 */
	public ResetSoundClip(PlaySoundClip player) {
		this.player = player;
	}

	@Override
	public void performAction(float elapsedTime) {
		player.played = false;
		if (player.player.clip != null)
			player.player.clip.setMicrosecondPosition(0);
	}

}
