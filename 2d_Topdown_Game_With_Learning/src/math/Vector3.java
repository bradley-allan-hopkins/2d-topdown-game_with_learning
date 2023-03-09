/**
 * Title: Vector.java 
 * Description: Used to create Vector Objects 
 * @author Bradley Hopkins
 * @version 1.0
 * @since Nov 21,2020
 */

package math;

/**
 * The Class Vector.
 */
public class Vector3{
	
	/** x position */
	private float x;
	
	/** y position */
	private float y;
	
	/** z position */
	private float z;
	
	/**
	 * Vector.
	 *
	 * @param x - float - x position
	 * @param y - float - y position
	 * @param z - float - z position
	 */
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.setZ(z);
	}
		
	/**
	 * Normalize the Vector making all values 1 or less
	 */
	public void normalize() {
		float length = (float) Math.sqrt((x*x) + (y*y) + (getZ()*getZ()));
		//make sure not dividing by zero
		if (length > -0.01 && length < 0.01) {
			this.x = 0;
			this.y = 0;
			this.setZ(0);
		}
		else {
			this.x = this.x/length;
			this.y = this.y/length;
			this.setZ(this.getZ()/length);
		}
	}
		
	/**
	 * Scale vector.
	 * @param scaler - float - value to scale position by
	 */
	public void scaleVector(float scaler) {
		this.x *= scaler;
		this.y *= scaler;
		this.setZ(this.getZ() * scaler);
	}
	
	/**
	 * This method returns a new Vector that has been scaled 
	 * @param scaler - float - value to scale 
	 * @return Vector - scaled Vector
	 */
	public Vector3 scaler(float scaler) {
		return new Vector3(x*scaler, y*scaler, getZ()*scaler);
	}
	
	/**
	 * Divide from vector.
	 * @param divider - float - value to divide all position by
	 */
	public void divideFromVector(float divider) {
		this.x /= divider;
		this.y /= divider;
		this.setZ(this.getZ() / divider);
	}
	
	/**
	 * Divide from vector.
	 * @param v - vector that is used to divide
	 * @return Vector3
	 */
	public Vector3 divideVectors(Vector3 v) {
		if (v.getX() == 0 || v.getY() == 0 || v.getZ() == 0) return null;
		
		float x1 = this.x / v.getX();
		float y1 = this.y / v.getY();
		float z1 = this.z / v.getZ();
		return new Vector3(x1, y1, z1);
		
	
	}
	
	
	/**
	 * Adds two vectors together and returns the new vector
	 * @param v - Vector that is added to this vector
	 * @return vector - new Vector 
	 */
	public Vector3 addTwoVectors(Vector3 v) {
		return new Vector3(x + v.x, y + v.y, getZ() + v.getZ());
	}
	
	/**
	 * Adds a Vector to this Vector 
	 * @param v - Vector
	 */
	public void addToVector(Vector3 v) {
		this.x += v.x;
		this.y += v.y;
		this.setZ(this.getZ() + v.getZ());
	}
	
	/**
	 * Subtract two vectors and return the new Vector
	 * @param v the v
	 * @return vector
	 */
	public Vector3 subtractTwoVectors(Vector3 v) {
		return new Vector3(x - v.x, y - v.y, z - v.getZ());
	}
	
	/**
	 * Subtract a Vector from this Vector.
	 * @param v - Vector
	 */
	public void subtractFromVector(Vector3 v) {
		this.x -= v.x;
		this.y -= v.y;
		this.setZ(this.getZ() - v.getZ());
	}
	

	
	/**
	 * Returns a new Vector that is multiplied by this one. 
	 *
	 * @param v the v
	 * @return vector
	 */
	public Vector3 cross(Vector3 v) {
		float x1 = (this.y * v.getZ()) - (this.z * v.getY());
		float y1 = (this.z * v.getX()) - (this.x * v.getZ());
		float z1 = (this.x * v.getY()) - (this.y * v.getX());
		return new Vector3(x1,y1,z1);
	}
	
	/**
	 * Returns the dot product = A . B
	 *
	 * @param v the v
	 * @return float
	 */
	public float dot(Vector3 v) {
		return ((this.x * v.getX()) + (this.y * v.getY()) + (this.getZ() * v.getZ()));
	}
	/**
	 * Reflect vector. This method calculates the reflected vector about a surface using the 
	 * surfaces Norm. 
	 *
	 * @param norm - the surface norm 
	 * @return vector - reflected vector
	 */
	public Vector3 reflect(Vector3 norm) {
		//r = d - 2(d.n)n
		Vector3 v = new Vector3(this.x, this.y, this.getZ());
		v.normalize();
		float calc1 = 2 * v.dot(norm);
		Vector3 calc2 = norm.scaler(calc1);
		return v.subtractTwoVectors(calc2);
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
		return (float)Math.sqrt((x*x) + (y*y) + (getZ()*getZ()));
	}

	/**
	 * Prints the coordinates.
	 */
	public void printCoordinates() {
		System.out.println(this.getClass().getSimpleName() + " Vector:" + x + "," + y + "," + getZ());
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
	 * Gets the variable "Z".
	 * @return float - Z
	 */
	public float getZ() {
		return z;
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

	/**
	 * Method that sets the Z variable
	 * @param z - the z coordinate 
	 */
	public void setZ(float z) {
		this.z = z;
	}
	
	@Override 
	/** public method used to print object information*/
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

	
}