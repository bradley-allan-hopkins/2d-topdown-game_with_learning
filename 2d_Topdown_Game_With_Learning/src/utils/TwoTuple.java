/**
 * Title: TwoTuple.java 
 * Description: TME4 Program 1
 * Id# 3380484 
 * @author Bradley Hopkins
 * @version 1.0
 * @since Oct 18,2021 
 * 
 */

package utils;
import java.io.Serializable;

/**
 * public class that stores two Generic objects. 
 *
 * @param <A> - referred to as "key"
 * @param <B> - referred to as "value"
 */
public class TwoTuple<A,B> implements Serializable{
	
	/** a holds any object*/
	public A a;
	/** b holds any object */
	public B b;
	/**
	 * Constructor 
	 * @param a - UNKNOWN - 
	 * @param b - UNKNOWN -
	 */
	public TwoTuple(A a, B b) {
		this.a = a;
		this.b = b;
	}

	@Override 
	/** public method used to print object information*/
	public String toString() {
		return "<" + a + "," + b + ">";
	}
}

