/**
 * Title: CanSeeObjectCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.statemachine.conditions;

import gameObjects.GameObject;
import gameObjects.collisions.LineOfSight;
import tiles.TileManager;

/**
 * The Class CanSeeObjectCondition. This condition returns true when the agent can see the 
 * object via ray casting using the LineOfSight object 
 */
public class CanSeeObjectCondition extends Condition {
	
	/** The agent. */
	GameObject agent;
	
	/** The obj. */
	GameObject obj;
	
	/** The sight. */
	LineOfSight sight;
	
	/**
	 * Can see object condition.
	 *
	 * @param agent the agent
	 * @param obj the obj
	 * @param tm the tm
	 */
	public CanSeeObjectCondition(GameObject agent, GameObject obj,TileManager tm) {
		this.agent = agent;
		this.obj = obj;
		sight = new LineOfSight(tm);
	}


	@Override
	public boolean test() {
		return sight.lineOfSight(agent, agent.getKinematic().getVelocity(), obj, 0.01);
	}

}
