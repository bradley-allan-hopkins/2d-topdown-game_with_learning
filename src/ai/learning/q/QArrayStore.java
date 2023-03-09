/**
 * Title: QArrayStore.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.learning.q;

import ai.statemachine.actions.Action;

import utils.TwoTuple;

/**
 * The Class QArrayStore. This holds the 2D array that stores states and their actions with 
 * associated actions 
 */
public class QArrayStore {


	/**
	 * The size.
	 */
	int size;
	/**
	 * The holder.
	 */
	StoreList[][] holder;

	/**
	 * Instantiates a new q store holder().
	 *
	 * @param actionSize the action size
	 * @param stateSize the state size
	 */
	public QArrayStore(int actionSize,int stateSize) {
		holder = new StoreList[stateSize][actionSize];
	}

	/**
	 * The Class StoreList.
	 */
	class StoreList extends TwoTuple<Float,Action>{

		/**
		 * Store list.
		 *
		 * @param a the a
		 * @param b the b
		 */
		public StoreList(Float a, Action b) {
			super(a, b);
			// TODO Auto-generated constructor stub
		}

	}
	
	/**
	 * Update value. This method will update the value for the action for the state. 
	 * If the state does not exist, create a new state. If the action does not exist create
	 * a new action with value
	 *
	 * @param state the state
	 * @param action the action
	 * @param value the value
	 * @return true, if successful
	 */
	public boolean updateValue(State state, Action action, float value) {
		int number = state.getStateNUM();
		int index = findAction(holder[number],action);
		
		//if action already exists update value
		if (index >= 0) {	
			holder[number][index].a = value;
			return true;
		}
		//if no action exists add new action to list 
		int emptyIndex = findNextEmpty(holder[number]);
		
		//array full - size up array 
		if (emptyIndex == -1) {
			//test to see if I need to size up 
			System.out.println("State:" + number + " is too full for more actions");
			return false;
		}
		
		//else add new action and value 
		holder[number][emptyIndex] = new StoreList(value,action);
		size++;
		
		return true;
	}
	
	
	/**
	 * Find action.
	 *
	 * @param list the list
	 * @param action the action
	 * @return int
	 */
	public static int findAction(StoreList[] list, Action action) {
		//go through action list 
		String actionClass = action.getClass().getSimpleName();
		for (int i = 0; i < list.length;i++) {
			//if both are the same TYPE of action
			if (list[i] == null)continue;
			if (list[i].b.getClass().getSimpleName().
					equalsIgnoreCase(actionClass)) {
				//change value
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Find next empty.
	 *
	 * @param list the list
	 * @return int
	 */
	public static int findNextEmpty(StoreList[] list) {
		for (int i = 0; i < list.length;i++) {
			if (list[i] == null) return i;
		}
		return -1;
	}
	
	/**
	 * Size.
	 *
	 * @return int
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Gets the variable "Value".
	 *
	 * @param state the state
	 * @param action the action
	 * @return float - Value
	 */
	public float getValue(State state, Action action) {
		int number = state.getStateNUM();
		//get the action list 
		int index = findAction(holder[number],action);
		//if action already exists update value
		if (index >= 0) {	
			return holder[number][index].a ;
		}

		//if no action exists yet return 0
		return 0;
	}
	
	
	/**
	 * The main method. This is used for testing 
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
	}
	
}

	