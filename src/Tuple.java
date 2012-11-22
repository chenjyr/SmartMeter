/*
 * copied from http://stackoverflow.com/a/2671052
 */

/**
 * 
 * @author Jake
 *
 * @param <X> the first coordinate in the tuple
 * @param <Y> the second coordinate in the tuple
 */
public class Tuple<X, Y> { 
  public final X x; 
  public final Y y; 
  public Tuple(X x, Y y) { 
    this.x = x; 
    this.y = y; 
  }
  public String toString()
  {
	  return "(" + x.toString() + "," + y.toString() + ")"; 
  }
} 