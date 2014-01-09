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
public final class Infinity implements Heuristic {

	/**
	 * Defines the heuristic value to be infinity (actually {@link Double#MAX_VALUE}).
	 */
	@Override
	public double execute(Point here, Point there) {
		return Double.MAX_VALUE;
	}

}
