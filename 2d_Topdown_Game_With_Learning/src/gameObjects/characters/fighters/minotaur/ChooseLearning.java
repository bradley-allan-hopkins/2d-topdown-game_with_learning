/**
 * Title: ChooseLearning.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters.fighters.minotaur;

import ai.learning.q.ChooseActionProblem;
import ai.learning.q.QLearning;
import ai.learning.q.State;
import ai.statemachine.actions.Action;
import gameObjects.GameObject;
import gameObjects.characters.fighters.Fighter;

/**
 * The Class ChooseLearning. This action allows the computer player to use learning to choose
 * the actions in the battle
 */
public class ChooseLearning extends Action {

	/** The learning. Static so all players use the same learning*/
	static QLearning learning;
	
	/** The fighter. */
	Fighter fighter;
	
	/** The problem. */
	ChooseActionProblem problem;
	
	/**
	 * Choose learning.
	 *
	 * @param obj the obj
	 */
	public ChooseLearning(GameObject obj) {
		problem = new ChooseActionProblem(obj);
		int stateSize = 400;
		int actionSize = 3;
		State state = ChooseActionProblem.getFirstState();//not random

		learning = new QLearning(state,stateSize,actionSize,
				"chooseLearning.txt","Choice",problem.getAvailableActions(state));
	}
	

	@Override
	public void performAction(float elapsedTime) {
		float alpha = 0.9f;// learning rate 
		float gamma = 0.7f;//next state value (discount rate)
		float rho = 0.7f; //randomness
		
		learning.qLearningOnline(problem, alpha, gamma, rho);

	}

}
