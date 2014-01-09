package controller.algorithm;

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
	final String getName() {
		return name;
	}

}
