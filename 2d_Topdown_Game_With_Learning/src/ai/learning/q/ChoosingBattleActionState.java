/**
 * Title: ChoosingBattleActionState.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.learning.q;


/**
 * The Class ChoosingBattleActionState. This is the states for the ChooseAction Problem. This
 * state uses the agents health and health bottles as well as the players health to determine
 * the state 
 */
public class ChoosingBattleActionState extends State {

	/** character health*/
	int myHealth;
	
	/** number of health bottles */
	int healthBottles;
	
	/** player health */
	int playerHealth;
	
	/**
	 * Choosing battle action state.
	 *
	 * @param healthBottles the health bottles
	 * @param playerHealth the player health
	 * @param myHealth the my health
	 */
	public ChoosingBattleActionState(int healthBottles, int playerHealth,int myHealth) {
		this.myHealth = myHealth;
		this.healthBottles = healthBottles;
		this.playerHealth = playerHealth;
	}
	

	@Override
	public int getStateNUM() {
		/*create stateNum so that every state will be a unique number 
		*Example 'h'ealth = 0-10 (11 choices) , 'o'rientation = 0 -6 (7 choices), 
		*'d'istance = total width = 1000/10 = 0 - 100 (101 choices), 'l'ocations = 0 -224 (225)
		*h = 11, o = 7, d = 101, l = 225
		*[h# * (o * d * l)] + [o# * (d * l)] + [d# * (l)] + [l] = index
		*[h# * (7 * 101 * 225)] + [o# * (101 * 225)] + [d# * (225)] + [l]
		*/
		
		//shrink up health for less states 
		int myNewHealth = myHealth/10; //10 states per person 
		int playerNewHealth = playerHealth/10; 
		
		/* myNewHealth = total of 11 states 0-10 -> M
		* playerNewHealth = total of 11 states 0 - 10  ->P
		* healthBottles = total of 4 states 0 - 3 ->B
		* arrayIndex = (healthBottles * (P(11) * M(11)) + (playerNewHealth * P(11)) + (myNewHealth)
		*example (3 * (121)) + (9 * 11) + (10) = state 472 
		*/
		
		int arrayIndex = (healthBottles * (121)) + (playerNewHealth * 11) + myNewHealth;
		return arrayIndex;
	}

}
