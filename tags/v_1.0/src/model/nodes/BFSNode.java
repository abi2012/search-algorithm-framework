package model.nodes;

/**
 * A concrete node for use by a breadth-first search algorithm.
 * 
 * @author Ken Norman
 * 
 */
public final class BFSNode extends Node {

	/**
	 * 
	 * Creates a new node for use by the breadth-first search algorithm. This class
	 * extends the abstract class {@link Node}.
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @see Node#Node(int, int)
	 */
	public BFSNode(int x, int y) {
		super(x, y);
	}
}
