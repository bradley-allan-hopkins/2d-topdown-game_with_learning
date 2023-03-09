/**
 * Title: Random.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 26, 2022
 */
package utils;

/**
 * The Class Random. This class holds different methods for returning random numbers 
 */
public class Random {

	/**
	 * Gets the variable "RndInteger".
	 *
	 * @param min the min
	 * @param max the max
	 * @return int - RndInteger
	 */
	public static int getRndInteger(int min, int max) {
		  return (int)Math.floor(Math.random() * (max - min + 1) ) + min;
	}
	
	/**
	 * returns a random binomial. Math.random() returns a number between 0 and 1; thus this 
	 * method returns a number between -1 and 1.
	 * @return float
	 */
	public static float randomBinomial() {
		return (float) (Math.random() - Math.random());
	}
	
}
