/**
 * Title: Entity.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 21, 2022
 */
package gameObjects.characters;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import gameObjects.GameObject;
import gameObjects.ID;
import gameObjects.Testing;
import gameObjects.collisions.shapes.Rectangle;
import gameObjects.collisions.shapes.Shape;
import graphics.Animation;
import graphics.Sprite;
import graphics.SpriteSheet;
import math.Vector2;
import state.GameStateManager;
import utils.ThreeTuple;

/**
 * The Class Entity.
 */
public abstract class Entity extends GameObject{

	/**
	 * The Enum Direction.
	 */
	protected enum Direction{

		/** The right. */
		RIGHT,
		/** The left. */
		LEFT,
		/** The down. */
		DOWN,
		/** The up. */
		UP,
		/** The idle. */
		IDLE;
	}

	protected Shape body;


	/** GameStateManager that entity exists in*/
	protected GameStateManager gsm;

	/** The animation. */
	protected Animation animation;

	/** The current animation. */
	protected int currentAnimation;

	/** The sprite sheet. */
	protected SpriteSheet spriteSheet;

	/** the animation speed */
	protected int animateSpeed;

	/** if the entity is running */
	protected boolean run = false;

	/** if the entity is moving left. */
	protected boolean left = false;

	/** The right. */
	protected boolean right = false;

	/** The up. */
	protected boolean up = false;

	/** The down. */
	protected boolean down = false;


	/**
	 * Entity.
	 * @param position - center position (x,y)
	 * @param width the width
	 * @param height the height
	 * @param speed the speed
	 * @param id the id
	 * @param sprite - all information about the spritesheet - location, height, width
	 * @param gsm - the gsm handling the state this entity exists in
	 */
	public Entity(Vector2 position, float width, float height,float speed, ID id,
			ThreeTuple<String,Integer,Integer> sprite, GameStateManager gsm) {
		super(position,width, height,speed, id);
		animation = new Animation();
		spriteSheet = new SpriteSheet(sprite.a,sprite.b,sprite.c);
		setAnimation(0,spriteSheet.getSpriteArray(0),-1);
		currentAnimation = -1;
		this.speed = speed;
		this.gsm = gsm;
		body = new Rectangle(this,getPosition(),unitWidth,unitHeight,speed,this.id);

	}

	/**
	 * Animate the entity by checking the direction based on orientation and selecting the
	 * Appropriate sprite sheet row
	 */
	public void animate() {

		if (getDirection() == Direction.UP && getVelY() != 0) {
			if ((currentAnimation != Direction.UP.ordinal() || animation.getDelay() == -1)) {
				setAnimation(Direction.UP.ordinal(),
						spriteSheet.getSpriteArray(Direction.UP.ordinal()), animateSpeed);
			}
			else setDelay(animateSpeed);
		} else if (getDirection() == Direction.DOWN && getVelY() != 0) {
			if ((currentAnimation != Direction.DOWN.ordinal() || animation.getDelay() == -1)) {
				setAnimation(Direction.DOWN.ordinal(),
						spriteSheet.getSpriteArray(Direction.DOWN.ordinal()), animateSpeed);
			}
			else setDelay(animateSpeed);
		} else if (getDirection() == Direction.LEFT && getVelX() != 0) {
			if ((currentAnimation != Direction.LEFT.ordinal() || animation.getDelay() == -1)) {
				setAnimation(Direction.LEFT.ordinal(),
						spriteSheet.getSpriteArray(Direction.LEFT.ordinal()), animateSpeed);
			}
			else setDelay(animateSpeed);
		} else if (getDirection() == Direction.RIGHT && getVelX() != 0) {
			if ((currentAnimation != Direction.RIGHT.ordinal() || animation.getDelay() == -1)) {
				setAnimation(Direction.RIGHT.ordinal(),
						spriteSheet.getSpriteArray(Direction.RIGHT.ordinal()), animateSpeed);
			}
			else setDelay(animateSpeed);
		}
		/*else if (fallen) {
            if (currentAnimation != FALLEN || animation.getDelay() == -1) {
                setAnimation(FALLEN, spriteSheet.getSpriteArray(FALLEN), 15);
            }
        }*/
		else {
			// if(!attacking && currentAnimation > 4) {
			//   setAnimation(currentAnimation - ATTACK, sprite.getSpriteArray(currentAnimation - ATTACK), -1);
			//  } else if(!attacking) {
			// if(currentAnimation != Direction.IDLE.ordinal()) {
			//      setAnimation(Direction.IDLE.ordinal(), spriteSheet.getSpriteArray(Direction.IDLE.ordinal()), 10);
			// } else if(!hasIdle) {
			if (currentAnimation == -1)return;
			setAnimation(currentAnimation, spriteSheet.getSpriteArray(currentAnimation), -1);
			//  }
			//  }
		}
	}



	/**
	 * Gets the variable "Animation".
	 * @return animation - Animation
	 */
	public Animation getAnimation() { return animation; }

	/**
	 * Method that returns the current animation number
	 * @return int - the line number in the sprite sheet
	 */
	public int getCurrentAnimation() {
		return currentAnimation;
	}

	/**
	 * Method that returns the direction of the entity based on the current orientation
	 * @return direction - Direction
	 */
	public Direction getDirection() {
		//right
		if (kinematic.getOrientation() >= 0 &&
				kinematic.getOrientation() <= (Math.PI/4)
				||
				kinematic.getOrientation() <= (2 * Math.PI) &&
				kinematic.getOrientation() >= (7 * Math.PI/4)
				||
				kinematic.getOrientation() >= (-Math.PI/4) &&
				kinematic.getOrientation() <= 0) {

			return Direction.RIGHT;
		}

		//left
		else if (kinematic.getOrientation() <= ((5 * Math.PI)/4) &&
				kinematic.getOrientation() >= (3 * Math.PI/4)
				||
				kinematic.getOrientation() <= (-3 * Math.PI)/4 &&
				kinematic.getOrientation() >= (- 5 * Math.PI/4))
		{
			return Direction.LEFT;
		}

		//down
		else if (kinematic.getOrientation() < ( 7 * Math.PI/4) &&
				kinematic.getOrientation() > ( 5 * Math.PI/4)
				||
				kinematic.getOrientation() < (-Math.PI/4) &&
				kinematic.getOrientation() > ((-3 * Math.PI)/4))
		{

			return Direction.DOWN;

		}

		//up
		else if (kinematic.getOrientation() > (Math.PI/4) &&
				kinematic.getOrientation() < (3 * Math.PI/4) )
			//||
			//kinematic.getOrientation() < ((-3 * Math.PI)/2) &&
			//kinematic.getOrientation() >= (-2 * Math.PI))
		{

			return Direction.UP;
		}

		return null;
	}
	/**
	 * Public method used to get
	 * @return the gsm
	 */
	public GameStateManager getGsm() {
		return gsm;
	}


	@Override
	public Shape[] getShapes() {
		Shape[] shapes=  new Shape[1];
		shapes[0] = body;
		return shapes;
	}

	/**
	 * Gets the variable "Speed".
	 * @return float - Speed
	 */
	@Override
	public float getSpeed() {
		return speed;
	}

	/**
	 * Public method used to get
	 * @return the spriteSheet
	 */
	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
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
		g.drawImage(animation.getImage().image, (int)(getPosX() - unitWidth/2 - x ),
				(int)(getPosY() - unitHeight/2 - y) ,
				(int)unitWidth,(int)unitHeight, observer);


		if (Testing.viewBoundingBoxes) {
			body.getBox().viewBoundingBox(g,Color.GREEN,x,y);
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
	 * Method that sets the animation
	 * @param arrayNum - the line in the sprite sheet starting at 0
	 * @param speed - speed of animation
	 * @param length - how many sprites in line
	 */

	public void setAnimation(int arrayNum, int speed, int length) {
		setAnimation(arrayNum,getSpriteSheet().getSpriteArray(arrayNum),speed);
		getAnimation().setLength(length);
	}



	/**
	 * Sets the animation.
	 *
	 * @param i the i
	 * @param frames the frames
	 * @param delay the delay
	 */
	public void setAnimation(int i, Sprite[] frames, int delay) {
		currentAnimation = i;
		animation.setFrames(i, frames);
		animation.setDelay(delay);

	}

	/**
	 * Sets the delay.
	 *
	 * @param animateSpeed the new delay
	 */
	public void setDelay(int animateSpeed) {
		if (run) animation.setDelay(animateSpeed/4);
		else animation.setDelay((animateSpeed));
	}

	/**
	 * Public method used to set
	 * @param gsm the gsm to set
	 */
	public void setGsm(GameStateManager gsm) {
		this.gsm = gsm;
	}

	/**
	 * Public method used to set
	 * @param spriteSheet the spriteSheet to set
	 */
	public void setSpriteSheet(SpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
	}
}

