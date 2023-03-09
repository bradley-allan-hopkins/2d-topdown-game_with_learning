/**
 * Title: Polygon.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 24, 2022
 */
package gameObjects.collisions.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import ai.steeringbehaviors.SteeringOutput;
import gameObjects.GameObject;
import gameObjects.ID;
import gameObjects.Testing;
import gameObjects.collisions.BoundingBox;
import gameObjects.collisions.CollisionDetection;
import math.Plane2D;
import math.Vector2;
import utils.ThreeTuple;

/**
 * The Class Polygon.
 */
public class Polygon extends Shape{

	/** Transformed Points. */
	private Vector2[] tPoints;
	
	
	/** Original Center */
	Vector2 center;
	
	/** The base points. */
	public Vector2[] bPoints;
	
	

	/**
	 * Polygon.
	 * @param holder - object holding this shape 
	 * @param center  - center position (x,y)
	 * @param width - width of the polygon (bounding box)
	 * @param height - height of the polygon (bounding box)
	 * @param speed the speed
	 * @param id the id
	 * @param points - the vertices for the polygon 
	 */
	public Polygon(GameObject holder,Vector2 center,float width, float height,float speed,
			ID id, List<Vector2> points) {
		super(holder,center, width,height,speed,id);
		this.center = new Vector2(center.getX(),center.getY());
		initializePolygon(points);
	}
	
	/**
	 * Method that sets all the points and creates the proper bounding box 
	 * @param points - the vertices 
	 */
	public void initializePolygon(List<Vector2> points) {
		bPoints = new Vector2[points.size()];
		tPoints = new Vector2[points.size()];
		for (int i = 0; i < points.size();i++) {
			Vector2 point = new Vector2(points.get(i).getX(),points.get(i).getY());
			bPoints[i] = point;
			tPoints[i] = point;
		}
		ThreeTuple<Float, Float, Vector2> info = findSize(points);
		box = new BoundingBox(info.c,info.a,info.b);
		
	}
	
	/**
	 * Adds the base points.
	 *
	 * @param point the point
	 * @param i - array location to add point
	 */
	public void addPoints(Vector2 point,int i ) {
		bPoints[i] = point;
		tPoints[i] = point;
	}
	
	
	
	/**
	 * Method that 
	 * @param angle - the angle to rotate the polygon 
	 * @param elapsedTime - game loop time 
	 * @param pos - original position 
	 */
	public void updatePoints(float angle,float elapsedTime,Vector2 pos) {

			Vector2 ray = pos.subtractTwoVectors(center);
			System.out.println("RAY:" + ray);
			for (int i = 0;i < bPoints.length;i++) {
				//Vector2 newPoint = rotatePoint(angle,bPoints[i]);
				tPoints[i] = bPoints[i].addTwoVectors(ray);
			}
			box.updateBoundingBox(pos);
			updateBox();
	}
	
	/**
	 * Method that rotates the polygon around the center point
	 * @param angle - angle to rotate the polygon 
	 * @param point - the point that is rotating around the center position 
	 * @return Vector2 - the new point
	 */
	public Vector2 rotatePoint(float angle, Vector2 point) {
		double sinA = Math.sin(-angle);
		double cosA = Math.cos(-angle);
		//rotate around origin of shape
		//float originX = box.getCentreOfBox().getX();
		//float originY = box.getCentreOfBox().getY();
		float originX = center.getX();
		float originY = center.getY();
		
		return new Vector2(
				(float)(((point.getX() - originX) * cosA) - ((point.getY() - originY) * sinA) + originX),
				(float)(((point.getX() - originX) * sinA) + ((point.getY() - originY) * cosA) + originY));
	}


	@Override
	public void render(Graphics2D g, ImageObserver observer, float x, float y) {
		
		g.setColor(Color.ORANGE);
		for (int i = 0; i < tPoints.length; i++) {
			g.drawLine((int)(tPoints[i].getX() - x), (int)( tPoints[i].getY() - y),
					(int)(tPoints[(i + 1) % tPoints.length].getX() - x ), 
					(int)(tPoints[(i + 1) % tPoints.length].getY() - y));
			g.drawOval((int)tPoints[i].getX() - 3,(int) tPoints[i].getY() - 3, 6, 6);
		}
		
		g.drawOval((int)box.getCentreOfBox().getX()-5,(int) box.getCentreOfBox().getY() - 5, 10, 10);
		
		if (Testing.viewBoundingBoxes) {
			box.viewBoundingBox(g, Color.RED,x,y);
		}
		Vector2[] points = new Vector2[tPoints.length];
		for (int i = 0; i < points.length;i++)
			points[i] = tPoints[i];
		Plane2D[] planes = CollisionDetection.getPlanes(points);
		g.setColor(Color.GREEN);
		for (int i = 0;i < planes.length;i++) {
			Vector2 midpoint = planes[i].getMidPoint();
			Vector2 norm = planes[i].getNorm().scaler(40);
			g.drawOval((int)midpoint.getX() - 2,(int)midpoint.getY()- 2, 4, 4);
			g.drawLine((int)midpoint.getX(), (int)midpoint.getY(), (int)(midpoint.getX() + norm.getX()),(int)( midpoint.getY() + norm.getY()));
		}
		
	}
	
	/**
	 * Method that updates the bounding box information to always surround the polygon
	 */
	public void updateBox() {
		List<Vector2> points = new ArrayList<Vector2>();
		for (int i = 0;i < tPoints.length;i++) {
			points.add(tPoints[i]);
		}
		ThreeTuple<Float,Float,Vector2> info = findSize(points);
		//box.setCenterOfBox(info.c);
		box.setWidth(info.a);
		box.setHeight(info.b);
	}
	
	/**
	 * Find size.
	 *
	 * @param points the points
	 * @return three tuple - width, height, center
	 */
	public static ThreeTuple<Float,Float,Vector2> findSize(List<Vector2> points) {
		float x = points.get(0).getX();
		float y = points.get(0).getY();
		float leftWidth = 0;
		float rightWidth = 0;
		float topHeight = 0;
		float bottomHeight = 0;
		
		for (int i = 0; i < points.size();i++) {
			Vector2 point = points.get(i);
			float temp;
			if (point.getX() < x) {
				temp = x - point.getX(); 
				if (temp > leftWidth) leftWidth = temp;
			}
			else if (point.getX() > x) {
				temp = point.getX() - x;
				if (temp > rightWidth) rightWidth = temp;
			}
			if (point.getY() < y) {
				temp = y - point.getY(); 
				if (temp > topHeight) topHeight = temp;
			}
			else if (point.getY() > y) {
				temp = point.getY() - y;
				if (temp > bottomHeight) bottomHeight = temp;
			}
		}
		float width = leftWidth + rightWidth;
		float height = topHeight + bottomHeight;
		float newX = x - leftWidth;
		float newY = y - topHeight;
		
		Vector2 centerPos = new Vector2(newX + width/2, newY + height/2);
		
		return new ThreeTuple<Float,Float,Vector2>(width,height,centerPos);
	}

	@Override
	public void update(float elapsedTime) {/* Not used*/}

	@Override
	public void init() {/* Not Used*/}
	/**
	 * Public method used to get
	 * @return the tPoints
	 */
	public Vector2[] getTPoints() {
		return tPoints;
	}

	/**
	 * Public method used to set
	 * @param tPoints the tPoints to set
	 */
	public void setTPoints(Vector2[] tPoints) {
		this.tPoints = tPoints;
	}

	@Override
	public void movement(float elapsedTime, SteeringOutput output) {
		// TODO Auto-generated method stub
		
	}
	

}
