package model;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * An enumeration of the eight directions of travel from a given node on a 2-D grid.
 * 
 * @author Ken Norman
 * 
 */
public enum Direction {

	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m-1, n-1)</code>.
	 */
	SOUTHWEST(-1, -1, 15),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m, n+1)</code>.
	 */
	NORTH(0, 1, 10),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m+1, n-1)</code>.
	 */
	SOUTHEAST(1, -1, 15),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m-1, n)</code>.
	 */
	WEST(-1, 0, 10),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m+1, n+1)</code>.
	 */
	NORTHEAST(1, 1, 15),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m, n-1)</code>.
	 */
	SOUTH(0, -1, 10),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m-1, n+1)</code>.
	 */
	NORTHWEST(-1, 1, 15),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m+1, n)</code>.
	 */
	EAST(1, 0, 10);

	/**
	 * The change in the value of the <code>y</code> coordinate for a neighbor node located in a
	 * particular direction.
	 */
	private final int deltaX;

	/**
	 * The change in the value of the <code>y</code> coordinate for a neighbor node located in a
	 * particular direction.
	 */
	private final int deltaY;

	/**
	 * The cost to move from the current node to the neighbor node in a particular direction.
	 */
	private final double costToNeighbor;

	/**
	 * Instantiates a new direction.
	 * 
	 * @param x
	 *            the x-offset to the neighbor node
	 * @param y
	 *            the y-offset to the neighbor node
	 * @param costToNeighbor
	 *            the cost to reach the neighbor node
	 */
	private Direction(int x, int y, double costToNeighbor) {
		this.deltaX = x;
		this.deltaY = y;
		this.costToNeighbor = costToNeighbor;
	}

	/*
	 * These three private static final fields are used by randomDirection() to get a random
	 * Direction
	 */
	private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays
			.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	/**
	 * Get a random Direction.
	 * 
	 * @return A Direction chosen at random.
	 */
	public final static Direction randomDirection() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

	/**
	 * Gets the adjacent point based on an <code>(x, y)</code> coordinate.
	 * 
	 * @param x
	 *            The <code>x</code> coordinate of a given node.
	 * @param y
	 *            The <code>y</code> coordinate of a given node.
	 * @return A Point object representing the <code>(x, y)</code> coordinates of an adjacent node
	 *         in a particular direction.
	 */
	public final Point getAdjacentPoint(int x, int y) {
		return new Point(x + deltaX, y + deltaY);
	}

	/**
	 * Gets the adjacent point based on a <code>(p.x, p.y)</code> coordinate.
	 * 
	 * @param p
	 *            A Point object representing the <code>(x, y)</code> coordinates of a given node
	 * @return A Point object representing the <code>(x, y)</code> coordinates of a node adjacent to
	 *         <code>p</code> in a particular direction.
	 */
	public final Point getAdjacentPoint(Point p) {
		return getAdjacentPoint(p.x, p.y);
	}

	/**
	 * Get the change in the value of the <code>x</code> coordinate for a neighbor node located in a
	 * particular direction.
	 * 
	 * @return The x coordinate multiplier. The value will only be one of 1, 0, or -1.
	 */
	public final int getDeltaX() {
		return deltaX;
	}

	/**
	 * Get the change in the value of the <code>y</code> coordinate for a neighbor node located in a
	 * particular direction.
	 * 
	 * @return The y coordinate multiplier. The value will only be one of 1, 0, or -1.
	 */
	public final int getDeltaY() {
		return deltaY;
	}

	/**
	 * Get the cost to move from the current node to the neighbor node in a particular direction.
	 * 
	 * @return The cost to move in this direction to the neighbor node.
	 */
	public final double getCostToNeighbor() {
		return costToNeighbor;
	}
}
