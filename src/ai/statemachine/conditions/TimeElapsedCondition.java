/**
 * Title: TimeElapsedCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 29, 2022
 */
package ai.statemachine.conditions;

import ai.statemachine.actions.Timer;

/**
 * The Class TimeElapsedCondition. Returns true if the timer is past the time set 
 */
public class TimeElapsedCondition extends Condition {

	/** The timer. */
	private Timer timer;
	
	/**
	 * Time elapsed condition.
	 *
	 * @param time the time
	 */
	public TimeElapsedCondition(float time) {
		timer = new Timer(time);
	}
	

	@Override
	public boolean test() {
		//move the timer forward
		timer.performAction(1);
		if (timer.isTimerDone()) {
			timer.resetTimer();
			return true;
		}
		return false;
	}


	/**
	 * Public method used to get
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}


	/**
	 * Public method used to set
	 * @param timer the timer to set
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

}
