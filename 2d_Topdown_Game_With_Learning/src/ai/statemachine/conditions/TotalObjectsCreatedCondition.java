/**
 * Title: TotalObjectsCreatedCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 27, 2022
 */
package ai.statemachine.conditions;

import gameObjects.handlers.Handler;
/**
 * The Class TotalObjectsCreatedCondition. Returns true if the quanity of Objects is created 
 * in the game 
 */
public class TotalObjectsCreatedCondition extends Condition {

	/** The handler. */
	Handler handler;
	
	/** The type name. */
	String typeName;
	
	/** The quantity. */
	int quantity;
	
	/**
	 * Total objects created condition.
	 *
	 * @param handler the handler
	 * @param quantity the quantity
	 * @param c the c
	 */
	public TotalObjectsCreatedCondition(Handler handler,int quantity, Class<?> c) {
		this.handler = handler;
		this.quantity = quantity;
		this.typeName = c.getSimpleName();
	}
	
	@Override
	public boolean test() {
		int total = 0;
		for (int i = 0; i < handler.getList().size();i++) {
			if (handler.getList().get(i).getClass().getSimpleName().equalsIgnoreCase(typeName)) {
				total++;
				if (total >= quantity)return true;
			}
		}
		return false;
	}

}
