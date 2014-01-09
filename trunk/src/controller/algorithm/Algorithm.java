package controller.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		Node current = getSearchArea().getFinish();

		if (!current.hasParent()) { // i.e., the finish node was never found
			return null;
		}

		Node parent;

		List<Node> path = new ArrayList<>();
		Map<Direction, Node> neighbors;
		while (!current.isStart()) {
			path.add(0, current);
			parent = current.getParent();

			neighbors = getSearchArea().getNeighbors(current);

			/*
			 * This loop determines which symbol to display for a particular node on the solution
			 * path
			 */
			for (Direction dir : neighbors.keySet()) {
				if (parent == neighbors.get(dir) && !parent.isStart()) {

					switch (dir) {
					case NORTH:
						parent.setStatus(NodeStatus.WAYPOINT_S);
						break;
					case NORTHEAST:
						parent.setStatus(NodeStatus.WAYPOINT_SW);
						break;
					case EAST:
						parent.setStatus(NodeStatus.WAYPOINT_W);
						break;
					case SOUTHEAST:
						parent.setStatus(NodeStatus.WAYPOINT_NW);
						break;
					case SOUTH:
						parent.setStatus(NodeStatus.WAYPOINT_N);
						break;
					case SOUTHWEST:
						parent.setStatus(NodeStatus.WAYPOINT_NE);
						break;
					case WEST:
						parent.setStatus(NodeStatus.WAYPOINT_E);
						break;
					case NORTHWEST:
						parent.setStatus(NodeStatus.WAYPOINT_SE);
						break;
					}
				}
			}
			current = parent;
		}

		return path;
	}

}
