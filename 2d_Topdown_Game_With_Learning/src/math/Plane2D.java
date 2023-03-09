/**
 * Title: Plane2D.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 2, 2022
 */
package math;

import utils.TwoTuple;

/**
 * The Class Plane2D. This class uses Vector2 from the game world to create Vector3 points
 * to create and calculate planes accordingly. Points must be entered counterclockWise
 */
public class Plane2D {
	
	/** plane = Ax + By + Cz + D = 0*/
	float a, b, c, d;
	
	/** Vertices */
	 Vector3 p1, p2, p3;
	 
	/** Height of plane - technically plane continues past */
	 final int HEIGHT = 1;
	
	 
	/**
	 * Plane 2 D. 
	 *
	 * @param p1 - Point 1 Must be counter clockWise
	 * @param p2 - Point 2
	 */
	public Plane2D(Vector2 p1, Vector2 p2) {
		this.p1 = new Vector3(p1.getX(), p1.getY(),0);
		this.p2 = new Vector3(p2.getX(), p2.getY(),0);
		this.p3 = new Vector3(p2.getX(), p2.getY(),HEIGHT);
		calculatePlane();
	}
	
	/**
	 * This method calculates the plane and fills the a,b,c,and d variables. 
	 */
	public void calculatePlane() {
		
		Vector3 v1 = p2.subtractTwoVectors(p1); //vector AB
		Vector3 v2 = p3.subtractTwoVectors(p2); //Vector BC
		Vector3 cross = v1.cross(v2);
		
		
		this.a = cross.getX(); // != -0 ? cross.getX(): 0;
		this.b = cross.getY();// != -0 ? cross.getY(): 0;
		this.c = cross.getZ();// != -0 ? cross.getZ(): 0;
		this.d = -((a * p1.getX()) + (b * p1.getY()) + (c * p1.getZ()));
	}
	
	/**
	 * This method calculates the intersection point where the line/vector intersects the plane.
	 * The two parameters are the beginning point and the end point.
	 *
	 * @param v1 the first position
	 * @param v2 the final position
	 * @return vector the intersection Vector
	 */
	public Vector2 getIntersect(Vector2 v1, Vector2 v2) {
		// find parametric form

		//f(t) = (v1.x,v1.y,v1.z) + t(v2.x-v1.x, v2.y-v1.y, v2.z-v1.z)
		float dx = v1.getX();
		float dy = v1.getY();
		//float dz = v1.getZ();
		//float dz = HEIGHT;

		//f(t) = (dx,dy,dz) + t(tx,ty,tz)
		float tx = (v2.getX() - v1.getX());
		float ty = (v2.getY() - v1.getY());
		//float tz = (v2.getZ() - v1.getZ());
		//float tz = (HEIGHT -HEIGHT); //always zero

		// resolve t
		//float constant = (a * dx) + (b * dy) + (c * dz);
		float constant = (a * dx) + (b * dy) ;
		//float tcoef = (a * tx) + (b * ty) + (c * tz);
		float tcoef = (a * tx) + (b * ty);
		float t = (-d - constant) / tcoef;
		//t = 0;

		// resolve
		float x_int = dx + (tx * t);
		float y_int = dy + (ty * t);
		//float z_int = dz + (tz * t);
		
		return new Vector2(x_int, y_int);
	}
	
	/**
	 * 
	 * Method that returns the intersection point and 'time' when intersection occurs 
	 * @param v1 - the original point 
	 * @param ray - the ray vector from the original point 
	 * @return TwoTuple - intersection point, 'time'
	 */
	public TwoTuple<Vector2,Float> getIntersectWithRay(Vector2 v1, Vector2 ray) {
		// find parametric form

		//f(t) = (v1.x,v1.y,v1.z) + t(v2.x-v1.x, v2.y-v1.y, v2.z-v1.z)
		
		float dx = v1.getX();
		float dy = v1.getY();
		float dz = 0;

		float tx = ray.getX() ;
		float ty = ray.getY() ;
		float tz = 0;

		//f(t) = (dx,dy,dz) + t(tx,ty,tz)
		
		// resolve t
		float constant = (a * dx) + (b * dy) + (c * dz);
		float tcoef = (a * tx) + (b * ty) + (c * tz);
		//printPlane();
		//System.out.println("NORM:" + getNorm());
		//System.out.println("CON:" + constant + ",tcoef:" + tcoef);
		float t = (-d - constant) / tcoef;
		//t = 0;

		// resolve
		float x_int = dx + (tx * t);
		float y_int = dy + (ty * t);
		//float z_int = dz + (tz * t);
		
		return new TwoTuple<Vector2,Float>(new Vector2(x_int, y_int),t);
	}
	


	/**
	 *This method calculates the norm vector for the plane. 
	 * @return vector - normal
	 */
	public Vector2 getNorm() {
		Vector3 v1 = p2.subtractTwoVectors(p1);
		Vector3 v2 = p3.subtractTwoVectors(p2);
		Vector3 norm = v2.cross(v1);
		norm.normalize();
		
		if (norm.getX() == -0)norm.setX(0);
		if (norm.getY() == -0)norm.setY(0);
		if (norm.getZ() == -0)norm.setZ(0);
		
		//return norm;//normal vector of plane 
		return new Vector2(norm.getX(),norm.getY());
	}
	
	/**
	 * Method that get the center point between the end two points 
	 * @return Vector2 center points
	 */
	public Vector2 getMidPoint() {
		float x = (p1.getX() + p2.getX())/2;
		float y = (p1.getY() + p2.getY())/2;
		return new Vector2(x,y);
	}

	
	/**
	 * Method that prints the plane calculation
	 */
	public void printPlane() {
		System.out.println("(" + a + "x + " + b + "y + " + c + "z + " + d + " = 0");
	}
}
