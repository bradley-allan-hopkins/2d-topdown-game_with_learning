/**
 * Title: SaveLearning.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters.fighters.minotaur;

import ai.statemachine.actions.Action;

/**
 * The Class SaveLearning. This action saves the learning data to the hard drive. Call this 
 * action when players die 
 */
public class SaveLearning extends Action {

	/** The learning. */
	ChooseLearning learning;
	
	/**
	 * Save learning.
	 *
	 * @param learning the learning
	 */
	public SaveLearning(ChooseLearning learning) {
		this.learning = learning;
	}
	

	@Override
	public void performAction(float elapsedTime) {
		learning.performAction(elapsedTime);
		ChooseLearning.learning.saveLearning();
	}

}
