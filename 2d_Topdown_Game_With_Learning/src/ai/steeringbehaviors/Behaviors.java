/**
 * Title: Behaviors.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 28, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors;

/**
 * The Interface Behaviors.
 */
public interface Behaviors 
{
	
	/**
	 * The method will return a steeringOutput based on the chosen behavior
	 * @return steering output - Steering
	 */
	public SteeringOutput getSteering();
}
