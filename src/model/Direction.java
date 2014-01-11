package model;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// TODO: Auto-generated Javadoc
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
	SOUTHWEST(-1, -1, "SW", 15),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m, n+1)</code>.
	 */
	NORTH(0, 1, "N", 10),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m+1, n-1)</code>.
	 */
	SOUTHEAST(1, -1, "SE", 15),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m-1, n)</code>.
	 */
	WEST(-1, 0, "W", 10),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m+1, n+1)</code>.
	 */
	NORTHEAST(1, 1, "NE", 15),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m, n-1)</code>.
	 */
	SOUTH(0, -1, "S", 10),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m-1, n+1)</code>.
	 */
	NORTHWEST(-1, 1, "NW", 15),
	/**
	 * For a node at <code>(m, n)</code>, this represents the direction of node
	 * <code>(m+1, n)</code>.
	 */
	EAST(1, 0, "E", 10);

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

	/** The abbreviation for the direction. */
	private final String abbreviation;

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
	 * @param abbreviation
	 *            the abbreviation
	 * @param costToNeighbor
	 *            the cost to reach the neighbor node
	 */
	private Direction(int x, int y, String abbreviation, double costToNeighbor) {
		this.deltaX = x;
		this.deltaY = y;
		this.abbreviation = abbreviation;
		this.costToNeighbor = costToNeighbor;
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
	 * Gets the abbreviation.
	 * 
	 * @return the abbreviation
	 */
	public final String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * Get the cost to move from the current node to the neighbor node in a particular direction.
	 * 
	 * @return The cost to move in this direction to the neighbor node.
	 */
	public final double getCostToNeighbor() {
		return costToNeighbor;
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
	 * Get the opposite Direction from this one.
	 * 
	 * @return The opposite direction.
	 */
	public final Direction oppositeDirectionCCW() {
		return Direction.fromOffset(-deltaX, -deltaY);
	}

	/**
	 * The adjacent direction moving clockwise around the compass.
	 * 
	 * @return The clockwise adjacent direction.
	 */
	public final Direction adjacentDirectionCW() {
		switch (this) {
		case NORTH:
			return NORTHEAST;
		case NORTHEAST:
			return EAST;
		case EAST:
			return SOUTHEAST;
		case SOUTHEAST:
			return SOUTH;
		case SOUTH:
			return SOUTHWEST;
		case SOUTHWEST:
			return WEST;
		case WEST:
			return NORTHWEST;
		case NORTHWEST:
			return NORTH;
		default:
			return null;
		}
	}

	/**
	 * The adjacent direction moving clockwise around the compass.
	 * 
	 * @return The counter-clockwise adjacent direction.
	 */
	public final Direction adjacentDirectionCCW() {
		switch (this) {
		case NORTH:
			return NORTHWEST;
		case NORTHWEST:
			return WEST;
		case WEST:
			return SOUTHWEST;
		case SOUTHWEST:
			return SOUTH;
		case SOUTH:
			return SOUTHEAST;
		case SOUTHEAST:
			return EAST;
		case EAST:
			return NORTHEAST;
		case NORTHEAST:
			return NORTH;
		default:
			return null;
		}
	}

	/**
	 * Get a Direction based on an abbreviation (N, NW, E, etc.).
	 * 
	 * @param abbreviation
	 *            The abbreviation.
	 * @return The Direction which corresponds to the abbreviation if such a match exists; returns
	 *         <code>null</code> otherwise.
	 */
	public static final Direction fromAbbreviation(String abbreviation) {
		if (abbreviation != null) {
			for (Direction d : Direction.values()) {
				if (abbreviation.trim().equalsIgnoreCase(d.abbreviation)) {
					return d;
				}
			}
		}
		return null;
	}

	/**
	 * Get a Direction based on an integer offset.
	 * 
	 * @param xOffset
	 *            The x offset. Must be 1, 0, or -1.
	 * @param yOffset
	 *            The y offset. Must be 1, 0, or -1.
	 * @return The direction which corresponds to the x- and y-offsets. Returns <code>null</code> if
	 *         both offsets are not in the integer range [-1, 1].
	 */
	public static final Direction fromOffset(int xOffset, int yOffset) {
		if (xOffset >= -1 && xOffset <= 1 && yOffset >= -1 && yOffset <= 1) {
			for (Direction d : Direction.values()) {
				if (d.deltaX == xOffset && d.deltaY == yOffset)
					return d;
			}
		}
		return null;
	}

	/*
	 * These three private static final fields are used by randomDirection() to get a random
	 * Direction
	 */
	/** The Constant VALUES. */
	private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays
			.asList(values()));

	/** The Constant RANDOM. */
	private static final Random RANDOM = new Random();

	/** The Constant SIZE. */
	private static final int SIZE = VALUES.size();

	/**
	 * Get a random Direction.
	 * 
	 * @return A Direction chosen at random.
	 */
	public final static Direction randomDirection() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
