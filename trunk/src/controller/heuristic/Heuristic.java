package controller.heuristic;

import java.awt.Point;

/**
 * A heuristic for estimating the cost to travel from one location to another in
 * a search area.
 * 
 * @author Ken Norman
 */
public interface Heuristic {

	/**
	 * Estimate the cost to traverse from <code>here</code> to
	 * <code>there</code> in a search area.
	 * 
	 * @param here
	 *            A Point representing the current location.
	 * @param there
	 *            A Point representing the destination location.
	 * @return The estimated cost.
	 */
	public double execute(Point here, Point there);
}
