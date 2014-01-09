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
public final class Zero implements Heuristic {

	/**
	 * Defines the heuristic value to be nearly 0 (actually {@link Double#MIN_VALUE}).
	 */
	@Override
	public double execute(Point here, Point there) {
		return Double.MIN_VALUE;
	}

}
