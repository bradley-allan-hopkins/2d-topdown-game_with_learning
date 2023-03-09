/**
 * Title: Minotaur.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package gameObjects.characters.minotaur;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import ai.statemachine.HSMBase.UpdateResult;
import ai.statemachine.HierarchicalStateMachine;
import ai.statemachine.State;
import ai.statemachine.Transition;
import ai.statemachine.actions.Action;
import ai.statemachine.conditions.AndCondition;
import ai.statemachine.conditions.NotCondition;
import ai.statemachine.conditions.TimeElapsedCondition;
import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;
import gameObjects.GameObject;
import gameObjects.ID;
import gameObjects.Testing;
import gameObjects.characters.Enemy;
import gameObjects.characters.Player;
import gameObjects.characters.fighters.CreateFighters;
import gameObjects.collisions.CollisionResolution;
import gameObjects.collisions.LineOfSight;
import graphics.AnimateAction;
import math.Vector2;
import state.GameStateManager;
import state.states.FightState;
import state.states.PlayState;
import utils.Random;
import utils.ThreeTuple;

/**
 * The Class Minotaur. The minotaur that will be located in the world map
 */
public class Minotaur extends Enemy{
	

	/** The animate. */
	HierarchicalStateMachine animate;
	
	/**
	 * Minotaur.
	 *
	 * @param position the position
	 * @param width the width
	 * @param height the height
	 * @param speed the speed
	 * @param id the id
	 * @param sprite the sprite
	 * @param gsm the gsm
	 */
	public Minotaur(Vector2 position, float width, float height, float speed,
			ID id, ThreeTuple<String, Integer, Integer> sprite, GameStateManager gsm) {
		super(position, width, height, speed, id, sprite,gsm);
		init();
	}


	@Override
	public void update(float elapsedTime) {
		//movement(elapsedTime, new SteeringOutput());
		animation.update();

		if (topLevel != null) {
			UpdateResult result = topLevel.update();
			Action[] actions = result.getActions();
			for (Action a : actions) {
				a.performAction(elapsedTime);
			}
		}
		if (animate != null) {
			UpdateResult result = animate.update();
			Action[] actions = result.getActions();
			for (Action a : actions) {
				a.performAction(elapsedTime);
			}
		}
	}
	
	
	@Override
	public void init() {
		body.getBox().setWidth(40);
		body.getBox().setHeight(50);
		body.getBox().setCentreOffset(new Vector2(0,0));
		box.updateBoundingBox(getPosition());
		body.getBox().updateBoundingBox(getPosition());
		

		setAnimation(0,getSpriteSheet().getSpriteArray(1), 20);
		getAnimation().setLength(7);
		animateSpeed = 20;
		
		createAnimateMachine();
	}
	

	/**
	 * Movement.
	 *
	 * @param elapsedTime the elapsed time
	 * @param output the output
	 */
	@Override
	public void movement(float elapsedTime, SteeringOutput output) {
		
		//output = wanderPriority.getSteering();
		kinematic.update(output,speed/4 ,elapsedTime);
		//update all parts of object
		body.getKinematic().update(output, speed, elapsedTime);

		box.updateBoundingBox(kinematic.getPosition());
		body.getBoundingBox().updateBoundingBox(getPosition());
	}
	
	/**
	 * Gets the variable "This".
	 * @return minotaur - This
	 */
	public Minotaur getThis() {
		return this;
	}

	/**
	 * Override the collision resolution, if characters collide send to FightState
	 *
	 * @return collision resolution - CollisionResolution
	 */
	@Override
	public CollisionResolution getCollisionResolution() {
		return new CollisionResolution() {
			@Override
			public void resolveCollision(ThreeTuple<Vector2, Vector2, Float> tuple, GameObject obj) {
				if (obj instanceof Player) {
					System.out.println("FIGHT");
					if (getGsm().isStateActive(GameStateManager.FIGHT))return;
					//fix glitch where released key is missed
					((Player)obj).resetMovement();
					
					//add the fightstate 
					getGsm().add(GameStateManager.FIGHT);
					((FightState)getGsm().getState(GameStateManager.FIGHT)).
					setGameWorldEnemy(getThis());
					
					//pause music 
					((PlayState)getGsm().getState(GameStateManager.PLAY)).soundPlayer.pause();
					
					//pause the playstate
					getGsm().pauseState(GameStateManager.PLAY);
					
					//randomly choose who goes first 
					int random = Random.getRndInteger(0,1);
					boolean playerGoesFirst = true;
					if (random == 0) playerGoesFirst = false;
					//add fighters to battle
					((FightState)getGsm().getState(GameStateManager.FIGHT)).addFighters(
							CreateFighters.createFighters(((Player)obj), getThis(), getGsm(),
									((FightState)getGsm().getState(GameStateManager.FIGHT)).tm),
							playerGoesFirst);
				}
				else stopCollision(tuple,obj);
			}
		};
	}
	
	/**
	 * Creates the animate machine.
	 */
	public void createAnimateMachine() {
		animate = new HierarchicalStateMachine("Animation");
		
		State leftFacing = new State("Facing left",0,animate);
		State rightFacing = new State("Facing Right",0,animate);
		
		rightFacing.addTransitions(new Transition(new AndCondition(new FacingLeftCondition(this),
				new TimeElapsedCondition(0.1f)),leftFacing,rightFacing.getLevel()));
		rightFacing.addActions(new AnimateRight(this));
		
		leftFacing.addTransitions(new Transition(new AndCondition
				(new NotCondition(new FacingLeftCondition(this)),
				new TimeElapsedCondition(0.1f)),
				rightFacing,leftFacing.getLevel()));
		leftFacing.addActions(new AnimateLeft(this));
		
		animate.initialState(rightFacing);
	}
	

	@Override
	public void render(Graphics2D g, ImageObserver observer,float x, float y) {
		
		//run the statemachine to render the minotaur
		if (animate != null) {
			UpdateResult result = animate.update();
			Action[] actions =  result.getActions();
			for (Action a : actions) {
				((AnimateAction)a).render(g, observer, x, y);
			}
		}
		

		
		if (Testing.viewBoundingBoxes) {
			body.getBox().viewBoundingBox(g,Color.GREEN,x,y);
			g.setColor(Color.BLUE);
			LineOfSight.renderLineOfSight(g, x, y, this,Kinematic.asVector(
					kinematic.getOrientation()).scaler(1000),
					PlayState.player, 0.01);
			

			
		}
	}
	

}
