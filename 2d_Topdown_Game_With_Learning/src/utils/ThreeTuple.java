/**
 * Title: ThreeTuple.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 9, 2022
 */
package utils;

/**
 * The Class ThreeTuple.
 *
 * @param <A> the generic type
 * @param <B> the generic type
 * @param <C> the generic type
 */
public class ThreeTuple<A,B,C> extends TwoTuple<A,B> {
	
	/** The c. */
	public C c;

	/**
	 * Three tuple.
	 *
	 * @param a the a
	 * @param b the b
	 * @param c the c
	 */
	public ThreeTuple(A a, B b, C c) {
		super(a, b);
		this.c = c;
	}
	
	@Override 
	/** public method used to print object information*/
	public String toString() {
		return "<" + a + "," + b + "," + c + ">";
	}

}
