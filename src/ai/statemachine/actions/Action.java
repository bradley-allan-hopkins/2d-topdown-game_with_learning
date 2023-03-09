/**
 * Title: Action.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 11, 2022
 */
package ai.statemachine.actions;

import java.io.Serializable;

/**
 * The Class Action. The action is something the GameObject can perform 
 */
public abstract class Action implements Serializable{

	/**
	 * This is the action performed
	 *
	 * @param elapsedTime the elapsed time
	 */
	public abstract void performAction(float elapsedTime);
	
	/**
	 * Method that adds array to another array.
	 *
	 * @param array1 - the original array
	 * @param array2 - the array adding its elements to the original array
	 * @return an array of Action objects
	 */
	public static Action[] combineArrays(Action[] array1, Action[] array2) {
		//if the second array is null or it contains nothing return original array 
		if (array2 == null || array2.length == 0) return array1;
		
		//create bigger array big enough to fit elements from both arrays 
		int totalSize = array1.length + array2.length;
		Action[] newArray = new Action[totalSize];
		
		//add data from 1st array
		for (int i = 0; i < array1.length;i++) {
			newArray[i] = array1[i];
		}
		//add data from 2nd array
		for (int i = 0; i < array2.length;i++) {
			newArray[array1.length + i] = array2[i];
		}
		return newArray;
	}
	
	
	/**
	 *  
	 * Method that allows Actions array to grow.
	 *
	 * @param array - the array the 'action' is being added to
	 * @param action the action
	 * @return an array of Action objects
	 */
	public static Action[] addToActionArray(Action[] array,Action action) {
		Action[] actionArray = array;
		if (array == null) actionArray = new Action[0];
		Action[] temp = new Action[actionArray.length + 1];
		for (int i = 0; i < actionArray.length;i++) {
			temp[i] = actionArray[i];
		}
		temp[temp.length - 1] = action;
		return temp;
	}
	
	/**
	 * To string.
	 *
	 * @return string
	 */
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
