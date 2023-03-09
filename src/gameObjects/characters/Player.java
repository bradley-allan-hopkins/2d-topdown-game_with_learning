/**
 * Title: Player.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import ai.steeringbehaviors.SteeringOutput;
import gameObjects.GameObject;
import gameObjects.ID;
import gameObjects.Testing;
import gameObjects.characters.fighters.CreateFighters;
import gameObjects.characters.minotaur.Minotaur;
import gameObjects.collisions.CollisionResolution;
import gameObjects.collisions.shapes.Rectangle;
import gameObjects.collisions.shapes.Shape;
import math.Vector2;
import state.GameStateManager;
import state.states.FightState;
import state.states.PlayState;
import utils.ThreeTuple;


/**
 * player class that is used to create a player that has the ability to move around the
 * game .
 *
 * @author bradl
 */public class Player extends Entity{

	 /** The Enum PaddleWay. */
	 enum Movement {
		 /** The stop. */
		 STOP,
		 /** The go. */
		 GO}
	 
 	/** The world health. */
 	private int worldHealth = 100;

	 /** The world health bottles. */
 	private int worldHealthBottles = 3;

	 /** The x. */
 	float x = 0;

	 /** The y. */
 	float y = 0;

	 /** The horizontal. */
 	private	Movement horizontal;

	 /** The vertical. */
 	private Movement vertical;
	 
 	/** The steering. */
 	SteeringOutput steering = new SteeringOutput();


	 /**
 	 * Player.
 	 *
 	 * @param position the position
 	 * @param width the width
 	 * @param height the height
 	 * @param speed the speed
 	 * @param id the id
 	 * @param sprite the sprite
 	 * @param gsm the gsm
 	 */
 	public Player(Vector2 position, float width, float height, float speed,
			 ID id, ThreeTuple<String, Integer, Integer> sprite,GameStateManager gsm) {
		 super(position, width, height, speed, id, sprite,gsm);
		 init();
	 }

	 /**
 	 * Gets the variable "CollisionResolution". This collision resolution will send the player
 	 * into a battle screen via FightState. It first will call the object create fighters 
 	 * to create the fighters for the battle.
 	 *
 	 * @return collision resolution - CollisionResolution
 	 */
 	@Override
	 public CollisionResolution getCollisionResolution() {
		 return new CollisionResolution() {
			 @Override
			 public void resolveCollision(ThreeTuple<Vector2, Vector2, Float> tuple, GameObject obj) {
				 if (obj instanceof Enemy) {
					 System.out.println("FIGHT");
					 if (gsm.isStateActive(GameStateManager.FIGHT))return;
					 gsm.add(GameStateManager.FIGHT);
					 //fix glitch where released key is missed
					 resetMovement();
					 
					 //pause music 
					((PlayState)getGsm().getState(GameStateManager.PLAY)).soundPlayer.pause();
					 
					 ((FightState)gsm.getState(GameStateManager.FIGHT)).setGameWorldEnemy(obj);
					 gsm.pauseState(GameStateManager.PLAY);
					 ((FightState)gsm.getState(GameStateManager.FIGHT)).addFighters(
							 CreateFighters.createFighters(getThis(), ((Minotaur)obj), gsm,
									 ((FightState)gsm.getState(GameStateManager.FIGHT)).tm),
							 true);
				 }
				 else stopCollision(tuple,obj);
			 }
		 };
	 }
	 
 	/**
 	 * Gets the variable "Shapes".
 	 *
 	 * @return shape[] - Shapes
 	 */
 	@Override
	 public Shape[] getShapes() {
		 Shape[] shapes=  new Shape[1];
		 shapes[0] = body;
		 return shapes;
	 }

	 /**
 	 * Gets the variable "This".
 	 *
 	 * @return player - This
 	 */
 	public Player getThis() {
		 return this;
	 }


	 /**
	  * Public method used to get
	  * @return the worldHealth
	  */
	 public int getWorldHealth() {
		 return worldHealth;
	 }

	 /**
	  * Public method used to get
	  * @return the worldHealthBottles
	  */
	 public int getWorldHealthBottles() {
		 return worldHealthBottles;
	 }

 	@Override
	 public void init() {

		 body = new Rectangle(this,getPosition(),30,30,3,this.id);
		 //change bounding box
		 getBox().setWidth(40);
		 getBox().setHeight(20);
		 getBox().setCentreOffset(new Vector2(0,15));
		 body.getBox().setWidth(40);
		 body.getBox().setHeight(20);
		 body.getBox().setCentreOffset(new Vector2(0,15));
		 box.updateBoundingBox(getPosition());
		 body.getBox().updateBoundingBox(getPosition());
		 animateSpeed = 16;
	 }


	 /**
	  * Key pressed.
	  * @param e the e
	  */
	 public void keyPressed(KeyEvent e) {
		 switch(e.getKeyCode()) {
			 //p1 inputs
			 case KeyEvent.VK_UP:
				 vertical = Movement.GO;
				 up = true;
				 break;
			 case KeyEvent.VK_DOWN:
				 vertical = Movement.GO;
				 down = true;
				 break;
			 case KeyEvent.VK_RIGHT:
				 horizontal = Movement.GO;
				 right = true;
				 break;
			 case KeyEvent.VK_LEFT:
				 horizontal = Movement.GO;
				 left = true;
				 break;
			 case KeyEvent.VK_R:
				 speed = 3;
				 run = true;
				 break;
			 default :
				 break;
		 }
	 }


	 /**
	  * Key released.
	  * @param e the e
	  */
	 public void keyReleased(KeyEvent e) {
		 switch(e.getKeyCode()) {
			 //p1 inputs
			 case KeyEvent.VK_UP:
				 vertical = Movement.STOP;
				 up = false;
				 break;
			 case KeyEvent.VK_DOWN:
				 vertical = Movement.STOP;
				 down = false;
				 break;
				 //p2 inputs
			 case KeyEvent.VK_LEFT:
				 horizontal = Movement.STOP;
				 left = false;
				 break;
			 case KeyEvent.VK_RIGHT:
				 horizontal = Movement.STOP;
				 right = false;
				 break;
			 case KeyEvent.VK_R:
				 speed = 2;
				 run = false;
				 break;
			 default :
				 break;
		 }
	 }

	 /**
 	 * Movement.
 	 *
 	 * @param elapsedTime the elapsed time
 	 */
 	public void movement(float elapsedTime){

		 //redundant coding but due to lag in keyboard keyReleased it is necessary
		 if(vertical == Movement.STOP && up == false && down == false) {
			 kinematic.getVelocity().setY(0);
			 y = 0;
		 }
		 if (horizontal == Movement.STOP && left == false && right == false) {
			 kinematic.getVelocity().setX(0);
			 x = 0;
		 }
		 //set variables based on keys
		 if(up) {

			 y = -speed;
		 }
		 if(down) {
			 y = speed;
		 }
		 if(left) {
			 x = -speed;
		 }
		 if(right) {
			 x = speed;
		 }

		 //set linear and call update to change players kinematic
		 steering.setLinear(new Vector2(x,y));
		 kinematic.update(steering, speed, elapsedTime);
		 //update all parts of object
		 body.getKinematic().update(steering, speed, elapsedTime);

		 kinematic.setOrientation((float) -Math.atan2(getVelY(),getVelX()));

	 }


 	@Override
	 public void movement(float elapsedTime, SteeringOutput output) {
		 // TODO Auto-generated method stub

	 }


 	@Override
	 public void render(Graphics2D g, ImageObserver observer,float x, float y) {
		 g.drawImage(animation.getImage().image, (int)(getPosX() - unitWidth/2 - x ),
				 (int)(getPosY() - unitHeight/2 - y) ,
				 (int)unitWidth,(int)unitHeight, observer);

		 body.render(g, observer, x, y);


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
 	 * Reset movement.
 	 *
 	 */
 	public void resetMovement() {
		 up = false;
		 down = false;
		 right = false;
		 left = false;
		 vertical = Movement.STOP;
		 horizontal = Movement.STOP;
	 }

	 /**
	  * Public method used to set
	  * @param worldHealth the worldHealth to set
	  */
	 public void setWorldHealth(int worldHealth) {
		 this.worldHealth = worldHealth;
	 }

	 /**
	  * Public method used to set
	  * @param worldHealthBottles the worldHealthBottles to set
	  */
	 public void setWorldHealthBottles(int worldHealthBottles) {
		 this.worldHealthBottles = worldHealthBottles;
	 }

 	@Override
	 public void update(float elapsedTime) {
		 movement(elapsedTime);


		 //look into why i must update constantly, should be referenced to position Vector2
		 getBox().updateBoundingBox(getPosition());
		 body.getBox().updateBoundingBox(getPosition());

		 animate();
		 animation.update();
	 }
 }
