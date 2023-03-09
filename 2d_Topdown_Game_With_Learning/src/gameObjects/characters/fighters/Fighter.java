/**
 * Title: Fighter.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import ai.statemachine.GameObjectStateMachine;
import ai.statemachine.HierarchicalStateMachine;
import ai.steeringbehaviors.SteeringOutput;
import gameObjects.ID;
import gameObjects.Testing;
import gameObjects.characters.Entity;
import graphics.Sprite;
import math.Vector2;
import state.GameStateManager;
import utils.ThreeTuple;
import utils.TwoTuple;

/**
 * The Class Fighter. This class is for all fighters in the fight scenes 
 */
public class Fighter extends Entity {
	
	/** List of who attacked this fighter: used for attacking while defending*/
	protected List<Fighter> whoAttacked;
	 
	/** number of health bottles */
	int healthBottles = 3;


	/** boolean if turn is done*/
	protected boolean turnDone;
	

	/** The state M. */
	GameObjectStateMachine stateM;
	
	/** The top level. */
	HierarchicalStateMachine topLevel;
	
	/** The dead. */
	private boolean dead;
	
	
	/** The selected. */
	protected boolean selected;
	/** Health of entity */
	private int health;
	
	/** attack range */
	protected TwoTuple<Integer,Integer> attackRange;
	
	/** The defense for the player*/
	protected int defense;

	/** if the fighter is defending */
	boolean defend;

	/**
	 * Fighter.
	 *
	 * @param position the position
	 * @param width the width
	 * @param height the height
	 * @param speed the speed
	 * @param id the id
	 * @param sprite the sprite
	 * @param gsm the gsm
	 */
	public Fighter(Vector2 position, float width, float height, float speed,
			ID id, ThreeTuple<String, Integer, Integer> sprite,GameStateManager gsm) {
		super(position, width, height, speed, id, sprite,gsm);
		this.selected = false;
		this.whoAttacked = new ArrayList<Fighter>();
		this.health = 100;
	}


	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void movement(float elapsedTime, SteeringOutput output) {
		// TODO Auto-generated method stub

	}

	/**
	 * Public method used to get
	 * 
	 * @return the defend
	 */
	public boolean isDefend() {
		return defend;
	}

	/**
	 * Public method used to set
	 * 
	 * @param defend
	 *            the defend to set
	 */
	public void setDefend(boolean defend) {
		this.defend = defend;
	}

	/**
	 * Increase health. Health can not exceed 100
	 *
	 * @param health
	 *            the health
	 */
	public void increaseHealth(int health) {
		this.health += health;
		if (this.health > 100)
			this.health = 100;
	}
	
	/**
	 * 
	 * Method that sets the health of the fighter
	 * @param health - the new health
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Gets the variable "Health".
	 *
	 * @return int - returns the current health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Decrease health. Health can not be below 0
	 *
	 * @param health
	 *            the health
	 * @return int - returns the current health after decrease
	 */
	public int decreaseHealth(int health) {
		this.health -= health;
		if (this.health < 1)
			this.health = 0;
		return this.health;
	}
	
	
	/**
	 * 
	 * Method that set the hierarchicalStateMachine 
	 * @param hsm - the hierarchicalStateMachine that will run the character
	 */
	public void setHStateM(HierarchicalStateMachine hsm) {
		this.topLevel = hsm;
	}
	
	/**
	 * 
	 * Method that returns the current machine 
	 * @return HierarchicalStateMachine - this can return null
	 */
	public HierarchicalStateMachine getHStateM() {
		return topLevel;
	}
	
	/**
	 * Public method used to get
	 * @return the stateM
	 */
	public GameObjectStateMachine getStateM() {
		return stateM;
	}

	/**
	 * Public method used to set
	 * @param stateM the stateM to set
	 */
	public void setStateM(GameObjectStateMachine stateM) {
		this.stateM = stateM;
	}
	
	/**
	 * Render.
	 *
	 * @param g the g
	 * @param observer the observer
	 * @param x the x
	 * @param y the y
	 */
	@Override
	public void render(Graphics2D g, ImageObserver observer,float x, float y) {
		Sprite image = animation.getImage();
		
		if (selected)image.setBrightness(30);
		else image.setBrightness(0);
		
		g.drawImage(image.image, (int)(getPosX() - unitWidth/2 - x ),
				(int)(getPosY() - unitHeight/2 - y) ,
				(int)unitWidth ,(int)unitHeight, observer);
		
		
		
		if (Testing.viewBoundingBoxes) {
			box.viewBoundingBox(g, Color.GREEN,x,y);
			g.setColor(Color.BLUE);
			//display direction ray
			for (int i = 0;i < 4 ; i++) {
			g.drawLine((int)box.getPoint(i).getX(), (int)box.getPoint(i).getY(), 
					(int)(box.getPoint(i).getX() + getVelX() * speed),
					(int)(box.getPoint(i).getY() + getVelY() * speed));
			//g.drawLine((int)getPosX(), (int)getPosY(), (int)(getPosX() + getVelX() * 100),
					//(int)(getPosY() + getVelY() * 100));
			}
		}
	}
	
	
	/**
	 * 
	 * Method that sets the selected boolean - this is used for the actions so that the action
	 * is performed on the selected fighter
	 * @param selected - true if fighter is selected, false if fighter is not 
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * 
	 * Method that gets the boolean selected 
	 * @return - true is fighter is selected, false if the fighter is not selected 
	 */
	public boolean getSelected() {
		return selected;
	}

	/**
	 * Public method used to get
	 * @return the dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Public method used to set
	 * @param dead the dead to set
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * Public method used to get
	 * @return the attackRange
	 */
	public TwoTuple<Integer, Integer> getAttackRange() {
		return attackRange;
	}

	/**
	 * Public method used to set
	 * @param attackRange the attackRange to set
	 */
	public void setAttackRange(TwoTuple<Integer, Integer> attackRange) {
		this.attackRange = attackRange;
	}
	
	/**
	 * Public method used to get
	 * @return the defense
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * Public method used to set
	 * @param defense the defense to set
	 */
	public void setDefense(int defense) {
		this.defense = defense;
	}
	

	/**
	 * Public method used to get
	 * @return the turnDone
	 */
	public boolean isTurnDone() {
		return turnDone;
	}


	/**
	 * Public method used to set
	 * @param turnDone the turnDone to set
	 */
	public void setTurnDone(boolean turnDone) {
		this.turnDone = turnDone;
	}
	
	/**
	 * Public method used to get
	 * @return the whoAttacked
	 */
	public List<Fighter> getWhoAttacked() {
		return whoAttacked;
	}


	/**
	 * Public method used to set
	 * @param whoAttacked the whoAttacked to set
	 */
	public void setWhoAttacked(List<Fighter> whoAttacked) {
		this.whoAttacked = whoAttacked;
	}
	
	/**
	 * Public method used to get
	 * @return the healthBottles
	 */
	public int getHealthBottles() {
		return healthBottles;
	}

	/**
	 * Public method used to set
	 * @param healthBottles the healthBottles to set
	 */
	public void setHealthBottles(int healthBottles) {
		this.healthBottles = healthBottles;
	}
	
}
