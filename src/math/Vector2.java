/**
 * 
 */
package math;
/**
 * Title: Vector2.java 
 * Description: Used to create Vector Objects 
 * @author Bradley Hopkins
 * @version 1.0
 * @since Nov 21,2020
 */

/**
 * The Class Vector.
 */
public class Vector2{
	
	/** x position */
	private float x;
	
	/** y position */
	private float y;
	
	/**
	 * Vector.
	 *
	 * @param x - float - x position
	 * @param y - float - y position
	 */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
		
	/**
	 * Normalize the Vector making all values 1 or less
	 */
	public void normalize() {
		float length = (float) Math.sqrt((x*x) + (y*y));
		//make sure not dividing by zero
		if (length > -0.01 && length < 0.01) {
			this.x = 0;
			this.y = 0;

		}
		else {
			this.x = this.x/length;
			this.y = this.y/length;
		}
	}
		
	/**
	 * Scale vector.
	 * @param scaler - float - value to scale position by
	 */
	public void scaleVector(float scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * This method returns a new Vector that has been scaled 
	 * @param scaler - float - value to scale 
	 * @return Vector - scaled Vector
	 */
	public Vector2 scaler(float scaler) {
		return new Vector2(x*scaler, y*scaler);
	}
	
	/**
	 * Divide from vector.
	 * @param divider - float - value to divide all position by
	 */
	public void divideFromVector(float divider) {
		if (divider == 0) return;
		this.x /= divider;
		this.y /= divider;
	}
	
	/**
	 * Divide from vector.
	 * @param v - Vector2
	 * @return Vector2
	 */
	public Vector2 divideVectors(Vector2 v) {
		if (v.getX() == 0 || v.getY() == 0) return null;
		
		float x1 = this.x / v.getX();
		float y1 = this.y / v.getY();
		return new Vector2(x1, y1);
	}
	
	/**
	 * Adds two vectors together and returns the new vector
	 * @param v - Vector that is added to this vector
	 * @return vector - new Vector 
	 */
	public Vector2 addTwoVectors(Vector2 v) {
		return new Vector2(x + v.x, y + v.y);
	}
	
	/**
	 * Adds a Vector to this Vector 
	 * @param v - Vector
	 */
	public void addToVector(Vector2 v) {
		this.x += v.x;
		this.y += v.y;
	}
	
	/**
	 * Subtract two vectors and return the new Vector
	 * @param v the v
	 * @return vector
	 */
	public Vector2 subtractTwoVectors(Vector2 v) {
		return new Vector2(x - v.x, y - v.y);
	}
	
	/**
	 * Subtract a Vector from this Vector.
	 * @param v - Vector
	 */
	public void subtractFromVector(Vector2 v) {
		this.x -= v.x;
		this.y -= v.y;
	}
	

	/**
	 * Returns a new Vector that is multiplied by this one. 
	 *
	 * @param v the v
	 * @return vector
	 */
	public Vector2 multiply(Vector2 v) {
		float x1 = (this.x * v.getX());
		float y1 = (this.y * v.getY());
		return new Vector2(x1,y1);
	}
	
	/**
	 * Returns the dot product = A . B
	 *
	 * @param v the v
	 * @return float
	 */
	public float dot(Vector2 v) {
		return ((this.x * v.getX()) + (this.y * v.getY()));
	}
	/**
	 * Reflect vector. This method calculates the reflected vector about a surface using the 
	 * surfaces Norm. 
	 *
	 * @param norm - the surface norm 
	 * @return vector - reflected vector
	 */
	public Vector2 reflect(Vector2 norm) {
		//r = d - 2(d.n)n
		//Vector2 v = new Vector2(this.x, this.y);
		//v.normalize();
		//float calc1 = 2 * this.dot(norm);
		//Vector2 calc2 = norm.scaler(calc1);
		//return this.subtractTwoVectors(calc2);
		 return this.subtractTwoVectors(norm.scaler(2).scaler(this.dot(norm)));
		
	}
	
	
	/**
	 * Magnitude is the same as length
	 * @return float - Magnitude
	 */
	public float getMagnitude() {
		return getLength();
	}
	
	/**
	 * Gets the variable "Length".
	 * @return float - Length
	 */
	public float getLength(){
		return (float)Math.sqrt((x*x) + (y*y));
	}

	/**
	 * Prints the coordinates.
	 */
	public void printCoordinates() {
		System.out.println(this.getClass().getSimpleName() + " Vector:" + x + "," + y);
	}
	
	//----------Getters and Setters ------------
	
	/**
	 * Gets the variable "X".
	 * @return float - X
	 */
	public float getX(){
		return x;
	}
	
	/**
	 * Gets the variable "Y".
	 * @return float - Y
	 */
	public float getY(){
		return y;
	}
	

	/**
	 * Sets the x.
	 * @param x the new x
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Sets the y.
	 * @param y the new y
	 */
	public void setY(float y) {
		this.y = y;
	}

	
	@Override 
	/** public method used to print object information*/
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	/** 
	 * Method that allows Vector2 arrays to grow
	 * @param array - the array the 'vector' is being added to
	 * @param vector - the new vector2
	 * @return an array of Vector2 objects 
	 */
	public static Vector2[] addToVectorArray(Vector2[] array,Vector2 vector) {
		if (array == null) array = new Vector2[0];
		Vector2[] temp = new Vector2[array.length + 1];
		for (int i = 0; i < array.length;i++) {
			temp[i] = array[i];
		}
		temp[temp.length - 1] = vector;
		return temp;
	}
}
