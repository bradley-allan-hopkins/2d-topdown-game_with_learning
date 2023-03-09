/**
 * Title: AnimateAction.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package graphics;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import ai.statemachine.actions.Action;

/**
 * The Class AnimateAction. This action is used in the HierarchicalState machine to render 
 * the objects 
 */
public abstract class AnimateAction extends Action {


	@Override
	public void performAction(float elapsedTime) {/** Not used to render*/}
	
	/**
	 * Render.
	 *
	 * @param g the graphics 
	 * @param observer the observer
	 * @param x the x offset from camera
	 * @param y the y offset from camera
	 */
	public abstract void render(Graphics2D g, ImageObserver observer,float x, float y);

}
