package model.nodes;

import java.awt.Point;

/**
 * A node in a search area.
 * 
 * @author Ken Norman
 * 
 */
public abstract class Node {
	/**
	 * A Point representation of the <code>(x, y)</code> coordinate of this node.
	 */
	private final Point point;

	/**
	 * The (initially EMPTY) status of the node.
	 */
	private NodeStatus status;

	/**
	 * The preceding node from which this node was reached during the course of the search.
	 */
	protected Node parent;

	/**
	 * <p>
	 * Creates a new empty Node at location (x, y). This abstract class must be extended by a class
	 * which is specific to the search algorithm being applied.
	 * <p>
	 * An <code>EMPTY</code> node is one that is not the start or finish node, nor is it an
	 * obstacle. Further, at node creation, it is not (yet) known whether the node will be on the
	 * solution path. If the search algorithm identifies the node as being on the solution path, the
	 * status of the node will be changed to <code>WAYPOINT</code>.
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 */
	protected Node(int x, int y) {
		this.point = new Point(x, y);
		this.status = NodeStatus.EMPTY;
		this.parent = null;

	}

	/**
	 * Gets a Point object representing the <code>(x, y)</code> coordinate of the node.
	 * 
	 * @return The Point object
	 */
	public final Point getPoint() {
		return point;
	}

	/**
	 * Gets the <code>x</code> coordinate.
	 * 
	 * @return The <code>x</code> coordinate.
	 */
	public final int getX() {
		return point.x;
	}

	/**
	 * Gets the <code>y</code> coordinate.
	 * 
	 * @return The <code>y</code> coordinate.
	 */
	public final int getY() {
		return point.y;
	}

	/**
	 * Gets the status of the node.
	 * 
	 * @return The status of the node.
	 * @see NodeStatus
	 */
	public final NodeStatus getStatus() {
		return status;
	}

	/**
	 * <p>
	 * Sets the status of a node.
	 * 
	 * <p>
	 * NOTE: The initial status of a newly created node is <code>EMPTY</code>.
	 * 
	 * @param s
	 *            The new status of the node.
	 */
	public void setStatus(NodeStatus s) {
		this.status = s;
	}

	/**
	 * Checks if the node is the start node.
	 * 
	 * @return <code><b>true</b></code> if the status of the node is <code>START</code><br>
	 *         <code><b>false</b></code> otherwise.
	 */
	public final boolean isStart() {
		return status == NodeStatus.START;
	}

	/**
	 * Checks if the node is the finish node.
	 * 
	 * @return <code><b>true</b></code> if the status of the node is <code>FINISH</code><br>
	 *         <code><b>false</b></code> otherwise.
	 */
	public final boolean isFinish() {
		return status == NodeStatus.FINISH;
	}

	/**
	 * Checks if the node is an obstacle.
	 * 
	 * @return <code><b>true</b></code> if the status of the node is <code>OBSTACLE</code><br>
	 *         <code><b>false</b></code> otherwise.
	 */
	public final boolean isObstacle() {
		return status == NodeStatus.OBSTACLE;
	}

	/**
	 * Checks if the node is a waypoint.
	 * 
	 * @return <code><b>true</b></code> if the status of the node is <code>WAYPOINT</code><br>
	 *         <code><b>false</b></code> otherwise.
	 */
	public final boolean isWaypoint() {
		return status.toString().startsWith("WAYPOINT");
	}

	/**
	 * Checks if the node is empty (i.e., not the start node, the finish node, an obstacle, or a
	 * waypoint).
	 * 
	 * @return <code><b>true</b></code> if the status of the node is <code>EMPTY</code><br>
	 *         <code><b>false</b></code> otherwise.
	 */
	public final boolean isEmpty() {
		return status == NodeStatus.EMPTY;
	}

	/**
	 * Gets the predecessor (parent) node for a given node on the solution path. If the node is not
	 * part of the solution path, then the parent will be <code>null</code>.
	 * 
	 * @return The parent node
	 */
	public final Node getParent() {
		return parent;
	}

	/**
	 * Sets the predecessor (parent) node of the current node. Applies only if the current node is
	 * on the solution path.
	 * 
	 * @param parent
	 *            The predecessor (parent) node of the current node.
	 */
	public final void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * Checks if the current node has a parent node.
	 * 
	 * @return <ul>
	 *         <code><b>true</b></code> if the current node is on the solution path and has a
	 *         predecessor (parent) identified<br>
	 *         <code><b>false</b></code> otherwise
	 *         </ul>
	 */
	public final boolean hasParent() {
		return parent != null;
	}

	/**
	 * Gets the one-character String to represent the status of the node.
	 * 
	 * @return A one-character unicode String symbol:
	 *         <ul>
	 *         <li>START: S
	 *         <li>FINISH: F
	 *         <li>OBSTACLE: █
	 *         <li>WAYPOINT_N: ↑
	 *         <li>WAYPOINT_NE: ↗
	 *         <li>WAYPOINT_E: →
	 *         <li>WAYPOINT_SE: ↘
	 *         <li>WAYPOINT_S: ↓
	 *         <li>WAYPOINT_SW: ↙
	 *         <li>WAYPOINT_W: ←
	 *         <li>WAYPOINT_NW: ↖
	 *         <li>EMPTY: "&nbsp;" (a single non-printing space)
	 *         </ul>
	 */
	public final String getSymbol() {
		return status.getSymbol();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass())
			return false;

		if (this == obj)
			return true;

		Node other = (Node) obj;

		return (this.point.x == other.point.x && this.point.y == other.point.y &&
				this.status == other.status && this.parent == other.parent);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	/**
	 * Displays a String representation of this Node as:<br>
	 * <code>[x=&lt;<i>x</i>&gt;, y=&lt;<i>y</i>&gt;, Status=&lt;<i>status</i>&gt;]</code>
	 */
	@Override
	public String toString() {
		return "[x=" + point.x + ", y=" + point.y + ", Status=" + status + "]";
	}

}
