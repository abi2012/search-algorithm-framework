package controller.algorithm.strategies;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import model.Direction;
import model.nodes.BFSNode;
import model.nodes.Node;
import controller.algorithm.Algorithm;
import controller.algorithm.AlgorithmType;

/**
 * This class is an implementation of the breadth-first search algorithm. It
 * operates on a 2-D grid (search area).
 * 
 * @author Ken Norman
 * 
 */
public final class BFSAlgorithm extends Algorithm {

	/**
	 * Create a breadth-first search algorithm.
	 */
	public BFSAlgorithm() {
		super(AlgorithmType.BFS);
	}

	/**
	 * Executes the breadth-first search algorithm.
	 */
	@Override
	public List<Node> execute() {
		if (getSearchArea() == null) {
			return null;
		}

		ArrayDeque<Node> horizon = new ArrayDeque<>();
		HashSet<Node> discovered = new HashSet<>();

		BFSNode startNode = (BFSNode) getSearchArea().getStart();
		horizon.addLast(startNode);
		discovered.add(startNode);

		BFSNode currentNode;
		while (!horizon.isEmpty()) {
			currentNode = (BFSNode) horizon.pollFirst();

			if (currentNode.isFinish())
				return buildSolutionPath();

			Map<Direction, Node> walkableNeighbors =
					getSearchArea().getWalkableNeighbors(currentNode, false);

			BFSNode neighbor;
			for (Direction dir : walkableNeighbors.keySet()) {

				neighbor = (BFSNode) walkableNeighbors.get(dir);
				if (!discovered.contains(neighbor)) {
					neighbor.setParent(currentNode);
					discovered.add(neighbor);
					horizon.addLast(neighbor);
				}
			}
		}
		return null; // finish never found
	}

}
