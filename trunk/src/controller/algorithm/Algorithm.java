package controller.algorithm;

import java.util.ArrayList;
import java.util.List;

import model.Direction;
import model.nodes.Node;
import model.nodes.NodeStatus;
import controller.Controller;

/**
 * An algorithm to use for searching.
 * 
 * @author Ken Norman
 * 
 */
public abstract class Algorithm {
	/**
	 * The type of this algorithm (e.g., A*, DFS, BFS, etc.).
	 */
	protected final AlgorithmType type;

	/**
	 * The Controller operating on this Algorithm.
	 */
	protected final Controller controller = Controller.getInstance();

	/**
	 * Construct a new Algorithm of type <code>t</code>.
	 * 
	 * @param t
	 *            The type of the new algorithm (e.g., A*, DFS, BFS, etc.).
	 */
	public Algorithm(AlgorithmType t) {
		type = t;
	}

	/**
	 * Execute the search algorithm. This abstract method must be implemented in a concrete
	 * algorithm class which extends {@link Algorithm}.
	 * 
	 * @return A list of nodes ordered from start to finish, which represent the lowest cost path.
	 */
	public abstract List<Node> execute();

	/**
	 * Gets the type of algorithm (e.g., A*, DFS, BFS, etc.).
	 * 
	 * @return The type
	 */
	public final AlgorithmType getType() {
		return type;
	}

	/**
	 * Gets the search area on which this algorithm will operate.
	 * 
	 * @return The search area.
	 */
	public final Controller.SearchArea getSearchArea() {
		return controller.getSearchArea();
	}

	/**
	 * Gets the human-readable name of the algorithm.
	 * 
	 * @return The name.
	 */
	public final String getName() {
		return type.getName();
	}

	/**
	 * Generate the solution path.
	 * 
	 * @return A List of Nodes representing the solution path. The list is in order from start to
	 *         finish, where the first element is the start node, and the last element is the finish
	 *         node.
	 */
	protected final List<Node> buildSolutionPath() {
		Node currentNode = getSearchArea().getFinish();

		if (!currentNode.hasParent()) { // i.e., the finish node was never found
			return null;
		}

		Node parentNode;

		List<Node> path = new ArrayList<>();
		while (!currentNode.isStart()) {
			path.add(0, currentNode);
			parentNode = currentNode.getParent();

			int parentXOffset = parentNode.getX() - currentNode.getX();
			int parentYOffset = parentNode.getY() - currentNode.getY();
			Direction dirToCurrent = Direction.fromOffset(-parentXOffset, -parentYOffset);

			if (dirToCurrent != null && !parentNode.isStart()) {
				parentNode.setStatus(NodeStatus.getDirectedWaypoint(dirToCurrent));
			}

			currentNode = parentNode;
		}

		return path;
	}

	/**
	 * Enumeration of various search algorithms.
	 * 
	 * @author Ken Norman
	 * 
	 */
	public enum AlgorithmType {
		/**
		 * An A* (A-star) algorithm.
		 */
		A_STAR("A*"),

		/**
		 * A Breadth-First Search algorithm.
		 */
		BFS("Breadth-First Search"),

		/**
		 * A Depth-First Search algorithm.
		 */
		DFS("Depth-First Search");

		/**
		 * A human-readable name of the algorithm.
		 */
		private final String name;

		private AlgorithmType(String name) {
			this.name = name;
		}

		/**
		 * @return The human-readable name of the algorithm associated with a given type.
		 */
		private final String getName() {
			return name;
		}

	}
}
