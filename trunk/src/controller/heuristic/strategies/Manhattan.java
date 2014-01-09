package controller.heuristic.strategies;

import java.awt.Point;

import controller.algorithm.strategies.AStarAlgorithm;
import controller.heuristic.Heuristic;

/**
 * A concrete heuristic class for use by an A* (A-star) algorithm.
 * 
 * @author Ken Norman
 * @see AStarAlgorithm
 */
public final class Manhattan implements Heuristic {

	/**
	 * Computes the heuristic value based on the Manhattan algorithm. For two nodes,
	 * <code>(x<sub>here</sub>, y<sub>here</sub>)</code> and
	 * <code>(x<sub>there</sub>, y<sub>there</sub>)</code>, this method returns the value<br>
	 * <ul>
	 * <code>|(x<sub>there</sub> - x<sub>here</sub>)| + |(y<sub>there</sub> - y<sub>here</sub>)|</code>
	 * </ul>
	 * 
	 */
	@Override
	public double execute(Point here, Point there) {
		return Math.abs(here.x - there.x) + Math.abs(here.y - there.y);
	}

}
