/**
 * Title: Animation.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 21, 2022
 *  Class skeleton used from com.zerulus.game
 *  
 *  April 15 * commented out states
 */
package graphics;

/**
 * The Class Animation.
 */
public class Animation {

	/** The frames. */
	private Sprite[] frames;

	/** The states. */
	private int[] states;

	/** The current frame. */
	private int currentFrame;

	/** The num frames. */
	private int numFrames;

	/** The count. */
	private int count;

	/** The delay. */
	private int delay;

	/** The times played. */
	private int timesPlayed;

	/**
	 * Instantiates a new animation().
	 */
	public Animation() {
		timesPlayed = 0;
		states = new int[10];
	}

	/**
	 * Animation.
	 *
	 * @param frames the frames
	 */
	public Animation(Sprite[] frames) {
		setFrames(0, frames);
		timesPlayed = 0;
		states = new int[10];
	}

	/**
	 * Gets the variable "Count".
	 * @return int - Count
	 */
	public int getCount() { return count; }

	/**
	 * Gets the variable "Delay".
	 * @return int - Delay
	 */
	public int getDelay() { return delay; }

	/**
	 * Gets the variable "Frame".
	 * @return int - Frame
	 */
	public int getFrame() { return currentFrame; }

	/**
	 * Gets the variable "Image".
	 * @return sprite - Image
	 */
	public Sprite getImage() {
		return frames[currentFrame];
	}

	/**
	 * Checks for played.
	 *
	 * @param i the i
	 * @return true, if successful
	 */
	public boolean hasPlayed(int i) { return timesPlayed == i; }

	/**
	 * Checks for played once.
	 * @return true, if successful
	 */
	public boolean hasPlayedOnce() { return timesPlayed > 0; }

	/**
	 * Sets the delay.
	 * @param i the new delay
	 */
	public void setDelay(int i) { delay = i; }

	/**
	 * Sets the frame.
	 * @param i the new frame
	 */
	public void setFrame(int i) { currentFrame = i; }

	/**
	 * Sets the frames.
	 *
	 * @param state the state
	 * @param frames the frames
	 */
	public void setFrames(int state, Sprite[] frames) {
		this.frames = frames;
		currentFrame = 0;
		count = 0;
		timesPlayed = 0;
		delay = 2;
		//if(states[state] == 0) {
			numFrames = frames.length;
		//} else {
		//	numFrames = states[state];
		//}
	}

	/**
	 * Sets the num frames.
	 *
	 * @param i the i
	 * @param state the state
	 */
	public void setNumFrames(int i, int state) {
		states[state] = i;
	}
	
	/**
	 * Sets the length.
	 *
	 * @param i the new length
	 */
	public void setLength(int i) {
		numFrames = i;
	}
	 
	/**
	 * Update.
	 * @return true if update is performed
	 */
	public boolean update() {
		if(delay == -1) return false;

		count++;

		if(count >= delay) {
			currentFrame++;
			count = 0;
		}
		if(currentFrame >= numFrames) {
			currentFrame = 0;
			timesPlayed++;
		}
		return true;
	}

}
