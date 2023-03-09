/**
 * Title: LineOfSight.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package gameObjects.collisions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import gameObjects.GameObject;
import math.Vector2;
import state.states.PlayState;
import tiles.TileManager;
import tiles.blocks.LineOfSightBlock;
import utils.ThreeTuple;

/**
 * The Class LineOfSight. THis class tests the agents line of sight 
 */
public class LineOfSight {
	
	/** The tm. */
	TileManager tm;
	
	/**
	 * Line of sight.
	 *
	 * @param tm the tm
	 */
	public LineOfSight(TileManager tm) {
		this.tm = tm;
	}


	/**
	 * Find smallest time.
	 *
	 * @param maxTime the max time
	 * @param originObj the origin obj
	 * @param ray the ray
	 * @return float
	 */
	public float findSmallestTime(float maxTime, GameObject originObj, Vector2 ray) {
		//list of line of sight objects

		Vector2 origin = originObj.getPosition();

		List<LineOfSightBlock> blocks = tm.lineOfSightBlocks;
		//list of characters that can block line of sight
		List<GameObject> characters = PlayState.entityHandler.getList();

		float time = maxTime;
		//check against line of sight objects
		for (int i = 0 ; i < blocks.size();i++) {
			ThreeTuple<Vector2,Vector2,Float> info =
					CollisionDetection.rayVsObj(origin,ray,blocks.get(i));
			//find smallest time
			if (info != null && info.c > 0 && info.c < time) time = info.c;
		}
		//next check against characters in game
		for (int i = 0; i < characters.size();i++) {
			//Don't test self
			if (originObj != characters.get(i)){
				ThreeTuple<Vector2,Vector2,Float> info =
						CollisionDetection.rayVsObj(origin,ray,characters.get(i));
				//find smallest time
				if (info != null && info.c > 0 && info.c < time) time = info.c;
			}
		}
		if (time < 0) System.out.println(time);
		return time;
	}


	/**
	 * Line of sight.
	 *
	 * @param originObj the origin obj
	 * @param ray the ray
	 * @param obj the obj
	 * @param angle the angle
	 * @return true, if successful
	 */
	public boolean lineOfSight(GameObject originObj, Vector2 ray,
			GameObject obj,double angle) {
		//get origin position
		Vector2 origin = originObj.getPosition();

		//check centre line first
		ThreeTuple<Vector2,Vector2,Float> objInfo =
				CollisionDetection.rayVsObj(origin,ray,obj);
		if (objInfo != null) {
			float time = objInfo.c;
			float newTime = findSmallestTime(time,originObj,ray);
			//if time is not changed ray hits object
			if (time == newTime) return true;
		}

		//whiskers
		Vector2 leftWhisker = rotate(ray,-angle);
		Vector2 rightWhisker = rotate(ray,angle);

		//check left whisker
		objInfo = CollisionDetection.rayVsObj(origin,leftWhisker,obj);
		if (objInfo != null) {
			float time = objInfo.c;
			float newTime = findSmallestTime(time,originObj,leftWhisker);
			//if time is not changed ray hits object
			if (time == newTime) return true;
		}
		//check right whisker
		objInfo = CollisionDetection.rayVsObj(origin,rightWhisker,obj);
		if (objInfo != null) {
			float time = objInfo.c;
			float newTime = findSmallestTime(time,originObj,rightWhisker);
			//if time is not changed ray hits object
			if (time == newTime) return true;
		}
		
		//if ray does not hit object return false;
		return false;
	}

	/**
	 * Render line of sight.
	 *
	 * @param g the g
	 * @param x the x
	 * @param y the y
	 * @param originObj the origin obj
	 * @param ray the ray
	 * @param obj the obj
	 * @param angle the angle
	 */
	public static void renderLineOfSight(Graphics2D g,float x, float y,GameObject originObj, Vector2 ray,
			GameObject obj,double angle) {

		Vector2 origin = originObj.getPosition();

		//check centre line first
		//Vector2 rayStopped = ray.scaler(findSmallestTime(Float.POSITIVE_INFINITY,originObj,ray));
		Vector2 rayStopped = ray;
		g.drawLine((int)(origin.getX() - x), (int)(origin.getY() - y),
				(int)(origin.getX() + rayStopped.getX()),
				(int)(origin.getY() + rayStopped.getY()));
		g.drawLine((int)(origin.getX() - x), (int)(origin.getY() - y),
				(int)(origin.getX() - rayStopped.getX()),
				(int)(origin.getY() - rayStopped.getY()));

		//whiskers
		g.setColor(Color.RED);
		Vector2 leftWhisker = rotate(ray,-angle);
		Vector2 rightWhisker = rotate(ray,angle);

		//left whisker
		//rayStopped = leftWhisker.scaler(findSmallestTime(
			//	Float.POSITIVE_INFINITY,originObj,leftWhisker));
		rayStopped = leftWhisker;
		g.drawLine((int)(origin.getX() - x), (int)(origin.getY() - y),
				(int)(origin.getX() + rayStopped.getX()),
				(int)(origin.getY() + rayStopped.getY()));

		//right whisker
		g.setColor(Color.GREEN);
		//rayStopped = rightWhisker.scaler(findSmallestTime(
			//	Float.POSITIVE_INFINITY,originObj,rightWhisker));
		rayStopped = rightWhisker;
		g.drawLine((int)(origin.getX() - x), (int)(origin.getY() - y),
				(int)(origin.getX() + rayStopped.getX()),
				(int)(origin.getY() + rayStopped.getY()));

	}

	/**
	 * Rotate.
	 *
	 * @param vector the vector
	 * @param angle the angle
	 * @return vector 2
	 */
	public static Vector2 rotate(Vector2 vector,double angle) { // angle in radians

		//normalize(vector); // No  need to normalize, vector is already ok...
		float x1 = (float)(vector.getX() * Math.cos(angle) - vector.getY() * Math.sin(angle));
		float y1 = (float)(vector.getX() * Math.sin(angle) + vector.getY() * Math.cos(angle)) ;

		return new Vector2(x1, y1);
	}

}
