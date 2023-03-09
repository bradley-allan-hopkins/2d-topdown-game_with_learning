/**
 * Title: Timer.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 29, 2022
 */
package ai.statemachine.actions;

/**
 * The Class Timer. This object is a timer that uses System time to calculate how much time
 * has passed
 */
public class Timer extends Action {

	/** The last time. */
	long lastTime = System.nanoTime();

	/** The amount of ticks. */
	final double amountOfTicks = 60.0;

	/** The ns. */
	double ns = 1000000000 / amountOfTicks;

	/** The delta. */
	double delta = 0;

	/** The updates. */
	int updates = 0;

	/** The frames. */
	float frames = 0;

	/** The timer. */
	long timer = System.currentTimeMillis();

	/** The seconds to stop. */
	float secondsToStop;

	/**
	 * Instantiates a new timer().
	 */
	public Timer() {

	}

	/**
	 * Timer.
	 *
	 * @param seconds the seconds
	 */
	public Timer(float seconds) {
		this.secondsToStop = seconds;
	}

	/**
	 * Checks if is timer done.
	 *
	 * @return true, if is timer done
	 */
	public boolean isTimerDone() {
		if (frames >= secondsToStop)return true;
		return false;
	}

	@Override
	public void performAction(float elapsedTime) {

		long now = System.nanoTime();
		delta += (now - lastTime) / ns;
		lastTime = now;
		if (delta >= 1) {
			delta--;
		}
		updates++;
		if (updates >= 15) {
			frames += 0.25;
			updates = 0;
		}
	}

	/**
	 * Reset timer to 0
	 */
	public void resetTimer() {
		this.delta = 0;
		this.updates = 0;
		this.frames = 0;
		this.timer = System.currentTimeMillis();
	}

	/**
	 * Time elapsed.
	 *
	 * @return float
	 */
	public float timeElapsed() {
		return frames;
	}
	
	/** 
	 * print time information 
	 * 
	 */
	public void print() {
		System.out.println("Frames:" + frames);
		System.out.println("Delta:" + delta);
		System.out.println("updates:" + updates);
		System.out.println("Seconds To Stop:" + secondsToStop);
	}

}
