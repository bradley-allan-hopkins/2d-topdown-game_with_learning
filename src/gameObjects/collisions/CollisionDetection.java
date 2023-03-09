/**
 * Title: CollisionHandler.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 9, 2022
 */
package gameObjects.collisions;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ai.steeringbehaviors.SteeringOutput;
import gameObjects.Camera;
import gameObjects.GameObject;
import gameObjects.ID;
import gameObjects.collisions.shapes.Polygon;
import gameObjects.collisions.shapes.Shape;
import math.Plane2D;
import math.Vector2;
import state.states.PlayState;
import tiles.TileManager;
import tiles.blocks.Block;
import utils.ThreeTuple;
import utils.TwoTuple;

/**
 * The Class CollisionHandler.
 */
public class CollisionDetection  {
	
	/** The tm. */
	TileManager tm;
	
	/**
	 * Collision detection.
	 *
	 * @param tm the tm
	 */
	public CollisionDetection(TileManager tm) {
		this.tm = tm;
	}
	
	/**
	 * Gets the variable "Collision".
	 *
	 * @param obj the obj
	 * @param radius the radius
	 * @return list - Collision
	 */
	public List<GameObject> getCollision(GameObject obj, float radius){
		List<GameObject> obstacles = tm.obstacles;
		List<GameObject> list = new ArrayList<GameObject>();
		for (int i = 0; i < obstacles.size();i++) {
			if (obstacles.get(i).getBox().getCentreOfBox().subtractTwoVectors(
					obj.getBoundingBox().getCentreOfBox()).getLength() < radius)
				list.add(obstacles.get(i));
		}
		return obstacles;
	}
	/**
	 * get a list of all possible collisions with blocks. Possible collisions is based on 
	 * radius distance".
	 *
	 * @param obj the obj
	 * @param radius the radius
	 * @return list - PossibleBlockCollisions
	 */
	public List<GameObject> getPossibleBlockCollisions(GameObject obj, float radius){
		//list of all the blocks able to be part of collisions
		Block[] blocks = tm.getObstacleBlocks().getBlocks();
		//list of Blocks within radius 
		List<GameObject> list = new ArrayList<GameObject>();
		for (int i = 0; i < blocks.length;i++) {
			if (blocks[i].getBox().getCentreOfBox().subtractTwoVectors(
					obj.getBoundingBox().getCentreOfBox()).getLength() < radius)
				list.add(blocks[i]);
		}
		if (list.size() > 0) return list;
		return null;
	}
	
	/**
	 * get a list of all possible collisions with characters. Possible collisions is based on 
	 * radius distance".
	 *
	 * @param obj the obj
	 * @param radius the radius
	 * @return list - PossibleCharacterCollisions
	 */
	public static List<GameObject> getPossibleCharacterCollisions(GameObject obj, float radius){
		//list of all characters that can cause collisions in the game
		List<GameObject> characters = PlayState.entityHandler.getList();
		//list of characters within radius
		List<GameObject> list = new ArrayList<GameObject>();
		for (int i = 0; i < characters.size();i++) {
			//dont't add character to list, only possible targets
			if (obj != characters.get(i)){
				if (characters.get(i).getBox().getCentreOfBox().subtractTwoVectors(
						obj.getBox().getCentreOfBox()).getLength() < radius)
					list.add(characters.get(i));
				
			}
		}
		if (list.size() > 0) return list;
		return null;
	}
	

	/**
	 * public method that checks for all possible collisions. This method should be called 
	 * every update
	 *
	 * @param elapsedTime the elapsed time
	 * @param cam - camera is used to only look for collisions inside camera view 
	 */
	public void checkForCollision(float elapsedTime,Camera cam) {
		
		//all possible collisions
		List<TwoTuple<GameObject,List<GameObject>>> possibleC = broadPhaseCollisions(cam);
		
		for (int i = 0; i < possibleC.size(); i++) {
			//GameObject character
			GameObject obj1 = possibleC.get(i).a;
			
			//List of targets
			List<GameObject> targets = possibleC.get(i).b;

			//sort targets based on distance from character
			Collections.sort(targets,new Comparator<GameObject>() {

				@Override
				public int compare(GameObject o1, GameObject o2) {
					 
					return (int) (obj1.getBoundingBox().getCentreOfBox().subtractTwoVectors(
							o1.getBoundingBox().getCentreOfBox()).getLength() - 
							obj1.getBoundingBox().getCentreOfBox().subtractTwoVectors(
									o2.getBoundingBox().getCentreOfBox()).getLength());
				}
			});

			// for each objects parts 
			Shape[] obj1Shapes = obj1.getShapes();
			for (int p1 = 0; p1 < obj1Shapes.length;p1++) {
				
				//for each target
				
				for (int t = 0;t < targets.size();t++) {
					//for each targets parts
					Shape[] obj2Shapes = targets.get(t).getShapes();
					if (obj2Shapes == null)
						{
						continue;
						
						}
					for (int p2 = 0;p2 < obj2Shapes.length;p2++){
						ThreeTuple<Vector2, Vector2, Float> tuple = objVsObj(obj1Shapes[p1],obj2Shapes[p2],
								elapsedTime);
						//if the returned Object is not null call targets collisionResolutuion Object
						if (tuple != null) {
							targets.get(t).getCollisionResolution().resolveCollision(tuple, obj1);
							continue;
						}
					}
				}
				obj1.tested = true;
				obj1Shapes[p1].tested = true;
			}
		}
		//reset all testing 
		for (int i = 0; i < possibleC.size(); i++) {
			possibleC.get(i).a.resetTested();
		}
	}
	
	
	/**
	 * Broad phase collisions.
	 * @param cam - used to find possible collision inside of camera view
	 * @return list
	 */
	public List<TwoTuple<GameObject,List<GameObject>>> broadPhaseCollisions(Camera cam) {
		//loop through characters on screen
		List<GameObject> list = PlayState.entityHandler.getList();
		
		//only grab objects just outside of camera view
		
		List<TwoTuple<GameObject,List<GameObject>>> all = 
				new ArrayList<TwoTuple<GameObject,List<GameObject>>>();
		
		//all collidableObjects
		List<GameObject> allObj = new ArrayList<GameObject>();
		List<GameObject> obstacles = tm.obstacles;
		Block[] blocks = null;
		if (tm.isObstaclesBlocksNotNull()) {
			blocks = tm.getObstacleBlocks().getBlocks();
		}
		else 
			blocks = new Block[0];
		
		//only use items within camera view and slightly out
		Camera bigCam = new Camera(new Vector2(cam.getBox().getMin().getX()  - 50,
				cam.getBox().getMin().getY() - 50),cam.getUnitWidth() + 100,
				cam.getUnitHeight() + 100,ID.CAMERA);
		bigCam.getBox().setCentreOffset(new Vector2(bigCam.getBox().getWidth()/2,bigCam.getBox().getHeight()/2));
						
		for (int i = 0; i < list.size();i++) {
			if (CollisionDetection.testOverLap(bigCam, list.get(i)))
				allObj.add(list.get(i));
		}
		for (int i = 0; i < blocks.length;i++) {
			if (CollisionDetection.testOverLap(bigCam, blocks[i]))
				allObj.add(blocks[i]);
		}
		for (int i = 0; i < obstacles.size();i++) {
			if (CollisionDetection.testOverLap(bigCam, obstacles.get(i)))
				allObj.add(obstacles.get(i));
		}
		
		//this is good place to shrink for less n2
		for (int i = 0; i < allObj.size();i++) {
			//list of possible collision with GameObject i
			
			
			List<GameObject> possibleCollisions = new ArrayList<GameObject>();
			for (int j = 0; j < allObj.size();j++) {
				//if object is not moving, not need to check for possible collisions
				if (allObj.get(i).getKinematic().getVelocity().getX() == 0 && 
						allObj.get(i).getKinematic().getVelocity().getY() == 0) 
					continue;
				// do not allow checks to itself
				if (allObj.get(i) != allObj.get(j)) {
					//if obj2 is close to obj1 
					float obj1Radius = Math.max(allObj.get(i).getUnitWidth(), 
							allObj.get(i).getUnitHeight());
					float obj2Radius =  Math.max(allObj.get(j).getUnitWidth(), 
							allObj.get(j).getUnitHeight());
					float radius = Math.max(obj1Radius, obj2Radius);
					if (radius < 100) radius = 100;
					if (allObj.get(i).getBox().getCentreOfBox().subtractTwoVectors(
							allObj.get(j).getBox().getCentreOfBox()).getLength() <= radius) {
						//add possible collision to list
						possibleCollisions.add(allObj.get(j));
					}
				}
			}
			//if possibleCollision has any potential collisions
			if (possibleCollisions.size() > 0) {
				all.add(new TwoTuple<GameObject,List<GameObject>>(
						allObj.get(i),possibleCollisions));
			}
			
		}
		return all;
	}
	
	/**
	 * Divide by ray.
	 *
	 * @param v1 the v 1
	 * @param ray the ray
	 * @param i the i
	 * @return vector 2
	 */
	public static Vector2 divideByRay(Vector2 v1, Vector2 ray,int i) {
		float x1 = 0;
		float y1 = 0;
		
		//check if ray.x is 0
		if (ray.getX() != 0)
			x1 = v1.getX() / ray.getX();
		//if ray.x is 0 used infinity calc based on i (either near or far)
		else {
			if (i == 0) x1 = Float.NEGATIVE_INFINITY;
			else if (i == 1) x1 = Float.POSITIVE_INFINITY;
		}
		//check if ray.y is 0
		if (ray.getY() != 0)
			y1 = v1.getY() / ray.getY();
		//if ray.y is 0 used infinity calc based on i (either near or far)
		else {
			if (i == 0) y1 = Float.NEGATIVE_INFINITY;
			else if (i == 1) y1 = Float.POSITIVE_INFINITY;
		}
		return new Vector2(x1,y1);
	}
	
	/** 
	 * Ray vs Polygon Object
	 * @param origin the origin
	 * @param ray the ray
	 * @param obj the target GameObject
	 * @return three tuple
	 */
	
	private static ThreeTuple<Vector2, Vector2, Float> polyVsPolyObj(GameObject obj1,
			GameObject obj2){
		//array of points 
		Vector2[] obj1Points = getPoints(obj1);
		Vector2[] obj2Points = getPoints(obj2);
		
		//create plans for each side of obj2
		//Plane2D[] obj2Planes = getPlanes(obj2Points);
		
		Vector2 ray = new Vector2(obj1.getVelX(),obj1.getVelY());
		//opposite ray of object one
		
		//test each obj1 point against each obj2 plane for intersection
		//each obj1 point
		Vector2 contactPoint = null;
		float time = Float.POSITIVE_INFINITY;
		Vector2 norm = null;
		//int object = 0;
		
		
		
		//check each object for collision due to points
		for (int object = 0;object < 2 ;object++) {
			List<TwoTuple<Plane2D,Vector2>> intersections = 
					new ArrayList<TwoTuple<Plane2D,Vector2>>();
			if (object == 1) {
				//opposite ray of object one
				ray.scaleVector(-1);
				Vector2[] temp = obj1Points;
				obj1Points = obj2Points;
				obj2Points = temp;
			}
			
			for (int i = 0; i < obj1Points.length ; i++) {
				//each obj2 plane
				Vector2 line1S = obj1Points[i];//start
				Vector2 line1E = line1S.addTwoVectors(ray);//end
				
				//for (int j = 0 ; j < obj2Planes.length; j++) {
				for (int j = 0; j < obj2Points.length;j++) {	
					
					Vector2 line2S = obj2Points[j];
					Vector2 line2E = obj2Points[(j + 1) % obj2Points.length];
					
					// Standard "off the shelf" line segment intersection
					float h = (line2E.getX()- line2S.getX()) * (line1S.getY() - line1E.getY()) - 
							(line1S.getX() - line1E.getX()) * (line2E.getY() - line2S.getY());
					float t1 = ((line2S.getY() - line2E.getY()) * (line1S.getX() - line2S.getX()) + 
							(line2E.getX() - line2S.getX()) * (line1S.getY() - line2S.getY())) / h;
					float t2 = ((line1S.getY() - line1E.getY()) * (line1S.getX() - line2S.getX()) + 
							(line1E.getX() - line1S.getX()) * (line1S.getY()- line2S.getY())) / h;
	
					if (t1 >= 0.0f && t1 < 1.0f && t2 >= 0.0f && t2 < 1.0f)
					{
						//if intersection is inside of line get plane for line
						intersections.add(new TwoTuple<Plane2D,Vector2>(
								new Plane2D(obj2Points[j],obj2Points[(j + 1) % obj2Points.length]),
								line1S));
						System.out.println("T1:" + t1 + ",POINT#:" + i + ",Shape:" + object );
					}
	
					
					/*TwoTuple<Vector2,Float> information = obj2Planes[j].getIntersectWithRay(obj1Points[i], ray);
					
					if (isIntersectionInObj(information.a,obj2)){
						if (information.b >= 0.0 && information.b <= 1.0){
							System.out.println("COLLISION = FOR point " + i + ":" + obj1Points[i]  + ",and plane:" +j+ ",Time:" + information.b);
							if (information.b < time) {
								
								contactPoint = information.a;
								time = information.b;
								norm = obj2Planes[j].getNorm();
							}
						}
					}*/
				}
			}
			if (intersections.size() > 0) {
				for (int i = 0; i < intersections.size();i++) {
					Vector2 point = intersections.get(i).b;
					Plane2D plane = intersections.get(i).a;
					
					TwoTuple<Vector2,Float> information = plane.getIntersectWithRay(point, ray);
					System.out.println("COLLISION = FOR point:" + point  + ",and plane:" + plane.getNorm() + ",Time:" + information.b);
					if (information.b < time) {
						
						contactPoint = information.a;
						time = information.b;
						norm = plane.getNorm();
					}
					
					/*if (information.b < time) {
						if (norm == null) {
							norm = plane.getNorm();//first pass
							contactPoint = information.a;
							time = information.b;
						}
						else {
							if (intersections.size() > 1 )
								if (i == 0) norm = plane.getNorm();
								//if norm are different add together
								else if (norm.getX() != plane.getNorm().getX() 
										|| norm.getY() != plane.getNorm().getY()) {
									norm = norm.addTwoVectors(plane.getNorm());	
								}
								
							else norm = plane.getNorm();
							contactPoint = information.a;
							time = information.b;
							//norm = plane.getNorm();
							
							if (object == 1)norm.scaleVector(-1);
							
							else if (information.b == time && norm != null) {
								if (norm.getX() == plane.getNorm().getX() 
										&& norm.getY() == plane.getNorm().getY()) {
										continue;
								}
								
							}
						}
					}*/
				}
			}
		}

		
		if (contactPoint != null) {
			return new ThreeTuple<Vector2,Vector2,Float>(contactPoint,norm, time);
		}
		return null;	
	}
	/**
	 * 
	 * Method that returns an array of planes based on points supplied 
	 * @param points - the vertices of the plane 
	 * @return Plane2D array
	 */
	public static Plane2D[] getPlanes(Vector2[] points) {
		Plane2D[] planes = new Plane2D[points.length];
		for (int i = 0;i < points.length;i++) {
			planes[i] = new Plane2D(points[i],points[(i+1)%points.length]);
		}
		return planes;
	}
	
	/**
	 * This method returns all the points in the shape
	 * Method that 
	 * @param obj - the object that the method will check against 
	 * @return Array of Vector2 points
	 */
	private static Vector2[] getPoints(GameObject obj) {
		Vector2[] objPoints;
		
		//get points for object 
		//if object is polygon get points from object
		if (obj instanceof Polygon) {
			objPoints = new Vector2[((Polygon)obj).getTPoints().length];
			for (int i = 0;i < ((Polygon)obj).getTPoints().length; i++)
				objPoints[i] = ((Polygon)obj).getTPoints()[i];
		}
		//if object is not a polygon calculate the 4 points from the bounding box
		else {
			int pointsInSquare = 4;
			objPoints = new Vector2[pointsInSquare];
			for (int i = 0;i < pointsInSquare;i++)
				objPoints[i] = obj.getBox().getPoint(i);
		}
		return objPoints;
	}
	
	/**
	 * Ray vs obj.
	 *
	 * @param origin the origin
	 * @param ray the ray
	 * @param obj the target GameObject
	 * @return three tuple - contact point, norm, 'time'
	 */
	public static ThreeTuple<Vector2,Vector2,Float> rayVsObj(Vector2 origin, Vector2 ray, GameObject obj) {
		//find the first point in which ray intersects with the object
		Vector2 tNear = divideByRay(obj.getBox().getMin().subtractTwoVectors(origin), ray,0);

		//find the second point in which the ray intersects with the object
		Vector2 tFar = divideByRay(obj.getBox().getMax().subtractTwoVectors(origin),ray,1);

		//swap near and far if ray is coming from opposite direction
		if (tNear.getX() > tFar.getX()) swapX(tNear,tFar);
		if (tNear.getY() > tFar.getY()) swapY(tNear,tFar);
		
		//if these conditions apply than ray does not intersect obj
		if (tNear.getX() > tFar.getY() || tNear.getY() > tFar.getX()) 
			return null;
		
		//"time" when first and second intersections occur
		float tHitNear = Math.max(tNear.getX(), tNear.getY());
		float tHitFar = Math.min(tFar.getX(), tFar.getY());
		
		//if "time" is less then 0, intersection point is behind object
		if (tHitFar < 0) return null;
		
		//exact contact point of collision
		Vector2 contactPoint = origin.addTwoVectors(ray.scaler(tHitNear));
		//check to make sure intersection lies within object
		if (!isIntersectionInObj(contactPoint, obj)) return null;
		
		Vector2 norm = new Vector2(0,0);
		//find norm
		if (tNear.getX() > tNear.getY()) {
			if (ray.getX() < 0)
				norm = new Vector2(1,0);
			else 
				norm = new Vector2(-1,0);
		}
		else if (tNear.getX() < tNear.getY()) {
			if (ray.getY() < 0)
				norm = new Vector2(0,1);
			else 
				norm = new Vector2(0,-1);
		}
		//return the contact point, normal of collision, and "time" of collision
		return new ThreeTuple<Vector2,Vector2,Float>(contactPoint, norm, Math.abs(tHitNear));
	}
	
	/**
	 * Obj vs obj.
	 *
	 * @param obj1 the obj 1
	 * @param obj2 the obj 2
	 * @param elapsedTime the elapsed time
	 * @return ThreeTuple (contact point, norm, 'time')
	 */
	public static ThreeTuple<Vector2, Vector2, Float> objVsObj(GameObject obj1, GameObject obj2, 
			float elapsedTime) {
		//returned information
		ThreeTuple<Vector2, Vector2, Float> information;

		//if previous movement is 0 then do not check 
		if (obj1.getKinematic().getVelocity().getX() == 0 && 
				obj1.getKinematic().getVelocity().getY() == 0) {
			return null;
		}
		//check first if object is a polygon
		if (obj1 instanceof Polygon || obj2 instanceof Polygon) {
			//quick check if inside bounding box, ignore if not in bounding box
			if (testOverLap(obj1,getExpandedRectangle(obj1,obj2))) {
				//send objects to polyVsPoly method without expanding object
				information = polyVsPolyObj(obj1, obj2);
				return information;
			}
			return null;
		}
		//create anonymous inner class that is bigger (Minkowski Sum) componsating for future 
		//movement as well
		float velX = 0;
		float velY = 0;
		//if the object tested previously test against updated position
		if (obj2.tested) {
			//System.out.println("ALREADY TESTED");
			velX = obj2.getKinematic().getVelocity().getX();
			velY = obj2.getKinematic().getVelocity().getY();
		}
		GameObject expandedObj = getExpandedRectangle(obj1,obj2,velX,velY);
		//use elapsed time to determine length of ray. 1 = frame, thus movement will be based
		//on one frame
		information = rayVsObj(obj1.getBox().getCentreOfBox(), 
				obj1.getKinematic().getVelocity().scaler(elapsedTime),
				expandedObj);
		
		if (information == null)
			return null;
		
		//if "time" is less than 1 than collision happened before end of ray 
		if (information.c >= 0 && information.c < 1.0f) {
			//now that objects enter into bounding box test against polygon shape if GameObject
			//is a polygon

			return information;
		}
		else if (testOverLap(obj1,obj2)) {
			return information;
		}
			
		return null;
				
	}
	
	/**
	 * Checks if is intersection in obj.
	 *
	 * @param intersection the intersection
	 * @param obj the obj
	 * @return true, if is intersection in obj
	 */
	public static boolean isIntersectionInObj(Vector2 intersection, GameObject obj) {
		float xI = intersection.getX();
		float yI = intersection.getY();
		BoundingBox box = obj.getBoundingBox();
		
		//if intersection lies in the bounding box
		if ((xI >= box.getMin().getX() && xI <= box.getMax().getX())
				&&
			(yI >= box.getMin().getY() && yI <= box.getMax().getY()))
			return true;
		
		return false;
	}
	
	
	/**
	 * Gets an expanded object used for collision detection without velocity, calls 
	 * getExpandedRectangle with 0 velocity
	 *
	 * @param ob1 the ob 1
	 * @param obj2 the obj 2
	 * @return game object - ExpandedRectangle
	 */
	public static GameObject getExpandedRectangle(GameObject ob1, GameObject obj2) {
		return getExpandedRectangle(ob1,obj2,0,0);
	}

	/**
	 * Gets the variable "ExpandedRectangle".
	 *
	 * @param obj1 the obj 1
	 * @param obj2 the obj 2 - the object that will be expanded
	 * @param velX the vel X
	 * @param velY the vel Y
	 * @return game object - ExpandedRectangle
	 */
	public static GameObject getExpandedRectangle(GameObject obj1, GameObject obj2,float velX,
			float velY) {
		return new GameObject(
					new Vector2(obj2.getBox().getCentreOfBox().getX() + velX, //original obj2 box x position
					obj2.getBox().getCentreOfBox().getY() + velY), //original obj2 y position
					(int)(obj2.getBox().getWidth()+ obj1.getBox().getWidth()),//bigger width
					(int)(obj2.getBox().getHeight() + obj1.getBox().getHeight()), //bigger Height
					obj2.getSpeed(),obj2.id) {
						@Override
						public void update(float e) {/*do not use*/}
						@Override
						public void render(Graphics2D g, ImageObserver observer,float x, float y) {/*do not use*/}
						@Override
						public void init() {/*do not use*/}
						@Override
						public void movement(float elapsedTime,
								SteeringOutput output) {	
						}
		};
	}
	
	/**
	 * This method required no velocity and then calls getExpandedPolygon() with 0 velocity
	 * @param obj1 - object used to resize obj2
	 * @param obj2 - object being resized 
	 * @return expanded GameObject
	 * 
	 */
	public static GameObject getExpandedPolygon(GameObject obj1, GameObject obj2) {
		return getExpandedPolygon(obj1, obj2, 0, 0);
	}
	
	/**
	 * Method that returns a expanded polygon object used for collision detection
	 * @param obj1 - object used to resize obj2
	 * @param obj2 - object being resized 
	 * @param velX - change in velocity to compensate for moved object
	 * @param velY - change in velocity to compensate for moved object
	 * @return expanded polygon object
	 */
	public static GameObject getExpandedPolygon(GameObject obj1, GameObject obj2, float velX,
			float velY) {
		return null;
		/*GameObject expandedObj = new Polygon(obj2,
				new Vector2(
				obj2.getBox().getCentreOfBox().getX() + velX, //original obj2 box x position
				obj2.getBox().getCentreOfBox().getY() + velY), //original obj2 y position
				(obj2.getBox().getWidth()+ obj1.getBox().getWidth()),//bigger width
				(obj2.getBox().getHeight() + obj1.getBox().getHeight()), //bigger Height
				obj2.getSpeed(),obj2.id,((Polygon)obj2).getTPoints());
		//change position of each point to expand shape
		float width = obj1.getBox().getWidth()/2;
		float height = obj1.getBox().getHeight()/2;
		System.out.println("width:" + width + ",Height:" + height);
		for (int i = 0;i < ((Polygon)obj2).bPoints.length;i++) {
			Vector2 point = new Vector2(((Polygon)obj2).bPoints[i].getX(),
					((Polygon)obj2).bPoints[i].getY());
			float originX = obj2.getPosX();
			float originY = obj2.getPosY();
			System.out.println("x:" + originX + ",Y:" + originY);
			if (point.getX() < originX) {
				if (point.getY() < originY) {
					point.setX(point.getX() - width );
					point.setY(point.getY() - height);
				}
				else if (point.getY() > originY) {
					point.setX(point.getX() - width );
					point.setY(point.getY() + height);
				}
				else point.setX(point.getX() - width );
			}
			
			else if (point.getX() > originX) {
				if (point.getY() < originY) {
					point.setX(point.getX() + width );
					point.setY(point.getY() - height);
				}
				else if (point.getY() > originY) {
					point.setX(point.getX() + width );
					point.setY(point.getY() + height);
				}
				else point.setX(point.getX() + width );
			}
			else if (point.getX() == originX) {
				if (point.getY() < originY)
					point.setY(point.getY() - height);
				else if (point.getY() > originY)
					point.setY(point.getY() + height);
			}
			
			((Polygon)expandedObj).addPoints(point,i);
		}
			System.out.println("POINTS:" + ((Polygon)expandedObj).bPoints.length);
	return expandedObj;*/
	}
	
	
	
	/**
	 * Test over lap.
	 *
	 * @param obj1 the obj 1
	 * @param obj2 the obj 2
	 * @return true, if successful
	 */
	public static boolean testOverLap(GameObject obj1, GameObject obj2) {
		Vector2 minObj1 = obj1.getBoundingBox().getMin();
		Vector2 maxObj1 = obj1.getBoundingBox().getMax();
		Vector2 minObj2 = obj2.getBoundingBox().getMin();
		Vector2 maxObj2 = obj2.getBoundingBox().getMax();
		
		float d1x = minObj2.getX() - maxObj1.getX();
		float d1y = minObj2.getY() - maxObj1.getY();
		float d2x = minObj1.getX() - maxObj2.getX();
		float d2y = minObj1.getY() - maxObj2.getY();
		
		//gap between objects
		float gap = 0.0f;
		
		//not overlapping
		if ( d1x > gap || d1y > gap || d2x > gap || d2y > gap) {
			return false;
		}
		return true;
	}
		
	/**
	 * Swap X.
	 *
	 * @param v1 the v 1
	 * @param v2 the v 2
	 */
	public static void swapX(Vector2 v1, Vector2 v2) {
		float temp = v1.getX();
		v1.setX(v2.getX());
		v2.setX(temp);
	}
	
	/**
	 * Swap Y.
	 *
	 * @param v1 the v 1
	 * @param v2 the v 2
	 */
	public static void swapY(Vector2 v1, Vector2 v2) {
		float temp = v1.getY();
		v1.setY(v2.getY());
		v2.setY(temp);
	}
}
