package model.nodes;

import controller.algorithm.AlgorithmType;

/**
 * A factory for creating nodes according to the type of algorithm being used.
 * 
 * @author Ken Norman
 * 
 */
public final class NodeFactory {

	/**
	 * Build a new node based on the algorithm being applied.
	 * 
	 * @param x
	 *            The <code>x</code> coordinate of the node.
	 * @param y
	 *            The <code>y</code> coordinate of the node.
	 * @param type
	 *            The type of algorithm for which the node will be used.
	 * @return The node
	 * @throws EnumConstantNotPresentException
	 */
	public static Node buildNode(int x, int y, AlgorithmType type) {
		Node node = null;

		switch (type) {
		case A_STAR:
			node = new AStarNode(x, y);
			break;
		case DFS:
			node = new DFSNode(x, y);
			break;
		case BFS:
			node = new BFSNode(x, y);
			break;

		default:
			throw new EnumConstantNotPresentException(AlgorithmType.class, type.toString());
		}
		return node;
	}
}
