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
public final class Diagonal implements Heuristic {

	/**
	 * Computes the heuristic value based on a straight-line distance ("as the crow flies") from the
	 * current node to the finish node.
	 */
	@Override
	public double execute(Point here, Point there) {
		double xDist = Math.abs(here.x - there.x);
		double yDist = Math.abs(here.y - there.y);

		if (xDist > yDist) {
			return (Math.sqrt(2) * yDist) + (xDist - yDist);
		} else {
			return (Math.sqrt(2) * xDist) + (yDist - xDist);
		}
	}

}
