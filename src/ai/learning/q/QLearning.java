/**
 * Title: QLearning.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.learning.q;

import utils.TwoTuple;

/**
 * The Class QLearning.
 */
public class QLearning {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		//Ant ant = new Ant(new Vector2(20,20),40f,40f,0.4f,ID.Player,
		//new ThreeTuple<String,Integer,Integer>("images/computer1.png",46,30));
		//Food food = new Food(new Vector2(100,100),10,10);
		//ProblemOne problem = new ProblemOne(ant,food);
	}

	/** memory of last state*/
	State state;


	/**the store for q values, we use this to make decisions based on the learning */
	QValueStore store;

	/**
	 * Q learning.
	 *
	 * @param state the state
	 * @param stateSize the state size
	 * @param actionSize the action size
	 * @param filename the filename
	 * @param problemName the problem name
	 * @param actions the actions
	 */
	public QLearning(State state,int stateSize, int actionSize,String filename,
			String problemName,String[] actions) {
		store = new QValueStringArrayStore(stateSize,actionSize,filename, problemName);
		this.state = state;
	}

	/**
	 * Q learning.
	 *
	 * @param problem the problem
	 * @param iterations the iterations
	 * @param alpha - learning rate - 0 -> 1 : higher equals more learning, lower less learning
	 * @param gamma - the discount rate - 0.75 good value : uses next state value
	 * @param rho - randomness for exploration 0->1 0 = use what it already knows,
	 * 1 = always try new things
	 * @param nu - length of walk 0 -> 1 0 = always use last state 1 = always random state,
	 * online learning use 0 as the agent can not switch states randomly
	 */
	//updates the store by investigating the problem
	public void qLearningOffline(ReinforcementProblem problem, int iterations, float alpha,
			float gamma, float rho, float nu) {
		//get a starting state
		State state = problem.getRandomState();

		//repeat a number of times
		for (int i = 0; i < iterations; i++) {
			//pick a new state every once in while
			if (Math.random() < nu) {
				state = problem.getRandomState();
			}

			//get a list of available actions
			String[] actions = problem.getAvailableActions(state);
			String action;
			//should we use a random action this time?
			if (Math.random() < rho) {
				int index = (int) (Math.random() * actions.length);
				action = actions[index]; //action = oneOf(actions)
			}
			//other wise pick the best action
			else
				action = store.getBestAction(state);

			//carry out the action and retrieve the reward and new state
			TwoTuple<Float, State> rewardAndState = problem.takeAction(state, action);

			//get the current q from the store
			float Q = store.getQValue(state,action);

			//get the q of the best action from the new state
			float maxQ = store.getQValue(rewardAndState.b, store.getBestAction(rewardAndState.b));

			//perform the q learning
			Q = (1 - alpha) * Q + alpha * (rewardAndState.a + gamma * maxQ);

			//store the new Q-value
			store.storeQValue(state, action, Q);

			//And update the state
			state = rewardAndState.b;
		}
	}

	/**
	 * Q learning.
	 *
	 * @param problem the problem
	 * @param alpha - learning rate - 0 -> 1 : higher equals more learning, lower less learning
	 * @param gamma - the discount rate - 0.75 good value : uses next state value
	 * @param rho - randomness for exploration 0->1 0 = use what it already knows,
	 * 		1 = always try new things
	 */
	public void qLearningOnline(ReinforcementProblem problem, float alpha,
			float gamma, float rho) {

		//find current state
		state = problem.getCurrentState();

		//get a list of available actions
		String[] actions = problem.getAvailableActions(state);
		String action;
		
		//should we use a random action this time?
		float random = (float) Math.random();

		if (random < rho) {
			int index = (int) (Math.random() * actions.length);
			action = actions[index]; 
		}

		//other wise pick the best action
		else
			action = store.getBestAction(state);

		//structure starts with no available actions as some states do not allow some actions
		//check for null action spots - if null pick random action available for this state
		if (action == null) {
			int index = (int) (Math.random() * actions.length);
			action = actions[index]; 
		}
		//carry out the action and retrieve the reward and new state
		TwoTuple<Float, State> rewardAndState = problem.takeAction(state, action);

		//get the current q from the store
		float Q = store.getQValue(state,action);

		//System.out.println("Q:" + Q);

		//get the q of the best action from the new state
		float maxQ = store.getQValue(rewardAndState.b, store.getBestAction(rewardAndState.b));


		Q = ((1 - alpha) * Q) + (alpha * (rewardAndState.a + gamma * maxQ));

		//store the new Q-value
		store.storeQValue(state, action, Q);

	}

	/**
	 * Save learning.
	 *
	 * @return true, if successful
	 */
	public boolean saveLearning() {
		return store.saveFile();
	}

	/**
	 * Size.
	 *
	 * @return int
	 */
	public int size() {
		return store.size();
	}

}
