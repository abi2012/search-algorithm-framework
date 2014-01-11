package model.nodes;

import model.Direction;

/**
 * The status of a node. A node may only be one of the following:
 * <ul>
 * <li>START: the starting node of the search
 * <li>FINISH: the ending node of the search
 * <li>OBSTACLE: a node which may no be included in the solution
 * <li>WAYPOINT_<i>dir</i>: a node (not START or FINISH) which is included in the solution, where
 * <code><i>dir</i></code> is one of the directions from a node: N, NE, E, SE, S, SW, W, NW.
 * <li>EMPTY: none of the other statuses
 * </ul>
 * 
 * @author Ken Norman
 * 
 */
public enum NodeStatus {
	/**
	 * Indicates the node from which the search begins. It is represented by the character "S".
	 */
	START("S"),
	/**
	 * Indicates the node which is the goal of the search. It is represented by the character "F".
	 */
	FINISH("F"),
	/**
	 * Indicates a node which can never be traversed. Obstacles are, therefore, never included in
	 * the solution path. It is represented by the character █.
	 */
	OBSTACLE("\u2588"), // \u2573=╳, \u2588=█
	/**
	 * Indicates a node on the solution path from which the next node in the solution is found in
	 * the NORTH direction. It is represented by the character ↑.
	 */
	WAYPOINT_N("\u2191"), // ↑
	/**
	 * Indicates a node on the solution path from which the next node in the solution is found in
	 * the NORTHEAST direction. It is represented by the character ↗.
	 */
	WAYPOINT_NE("\u2197"), // ↗
	/**
	 * Indicates a node on the solution path from which the next node in the solution is found in
	 * the EAST direction. It is represented by the character →.
	 */
	WAYPOINT_E("\u2192"), // →
	/**
	 * Indicates a node on the solution path from which the next node in the solution is found in
	 * the SOUTHEAST direction. It is represented by the character ↘.
	 */
	WAYPOINT_SE("\u2198"), // ↘
	/**
	 * Indicates a node on the solution path from which the next node in the solution is found in
	 * the SOUTH direction. It is represented by the character ↓.
	 */
	WAYPOINT_S("\u2193"), // ↓
	/**
	 * Indicates a node on the solution path from which the next node in the solution is found in
	 * the SOUTHWEST direction. It is represented by the character ↙.
	 */
	WAYPOINT_SW("\u2199"), // ↙
	/**
	 * Indicates a node on the solution path from which the next node in the solution is found in
	 * the WEST direction. It is represented by the character ←.
	 */
	WAYPOINT_W("\u2190"), // ←
	/**
	 * Indicates a node on the solution path from which the next node in the solution is found in
	 * the NORTHWEST direction. It is represented by the character ↖.
	 */
	WAYPOINT_NW("\u2196"), // ↖
	/**
	 * Indicates a node on the solution path which provides no indication of the direction to the
	 * next node on the solution path. It is represented by the character •.
	 */
	WAYPOINT("\u2022"), // •
	/**
	 * Indicates a node which is not the START node, not the FINISH node, not an OBSTACLE node, and
	 * not a WAYPOINT (neither directional or generic) node. It is represented by a single blank
	 * space (i.e., "&nbsp;").
	 */
	EMPTY(" ");

	/** The one-character symbol which represents the status. */
	private final String symbol;

	/**
	 * Instantiates a new node status.
	 * 
	 * @param symbol
	 *            The one-character symbol which represents the status.
	 */
	private NodeStatus(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the one-character String used to represent the status of the node. Namely,
	 * <p>
	 * <ul>
	 * <li>START: S
	 * <li>FINISH: F
	 * <li>OBSTACLE: █
	 * <li>WAYPOINT_N: ↑
	 * <li>WAYPOINT_NE: ↗
	 * <li>WAYPOINT_E: →
	 * <li>WAYPOINT_SE: ↘
	 * <li>WAYPOINT_S: ↓
	 * <li>WAYPOINT_SW: ↙
	 * <li>WAYPOINT_W: ←
	 * <li>WAYPOINT_NW: ↖
	 * <li>WAYPOINT: •
	 * <li>EMPTY: "&nbsp;" (a single non-printing space)
	 * </ul>
	 * 
	 * @return The symbol
	 */
	public final String getSymbol() {
		return symbol;
	}

	/**
	 * Gets the directed waypoint NodeStatus associated with a particular direction.
	 * 
	 * @param d
	 *            the d
	 * @return the directed waypoint
	 */
	public static final NodeStatus getDirectedWaypoint(Direction d) {
		return NodeStatus.valueOf("WAYPOINT_" + d.getAbbreviation());
	}
}
