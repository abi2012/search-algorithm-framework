package model.nodes;

/**
 * A concrete node for use by a depth-first search algorithm.
 * 
 * @author Ken Norman
 * 
 */
public final class DFSNode extends Node {

	private boolean visited = false;

	/**
	 * 
	 * Creates a new node for use by the depth-first search algorithm. This class extends the
	 * abstract class {@link Node}.
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @see Node#Node(int, int)
	 */
	public DFSNode(int x, int y) {
		super(x, y);
	}

	/**
	 * Checks if the node has been visited during the execution of the depth-first search algorithm.
	 * 
	 * @return <code><b>true</b></code> if the node has been visited;<br>
	 *         <code><b>false</b></code> otherwise
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Checks if the node has <i>not</i> been visited during the execution of the depth-first search
	 * algorithm.
	 * 
	 * @return <code><b>true</b></code> if the node has <i>not</i> been visited;<br>
	 *         <code><b>false</b></code> otherwise
	 */
	public boolean isNotVisited() {
		return !visited;
	}

	/**
	 * Sets the <code>visited</code> flag.
	 * 
	 * @param visited
	 *            <ul>
	 *            <code><b>true</b></code> indicates the node has been visited;<br>
	 *            <code><b>false</b></code> indicates the node has <i>not</i> been visited
	 *            </ul>
	 * 
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			DFSNode other = (DFSNode) obj;
			return (this.visited == other.visited);
		}
		return false;
	}
}
