package controller.algorithm.strategies;

import java.util.List;
import java.util.Map;

import model.Direction;
import model.nodes.DFSNode;
import model.nodes.Node;
import controller.algorithm.Algorithm;
import controller.algorithm.AlgorithmType;

/**
 * This class is an implementation of the depth-first search algorithm. It
 * operates on a 2-D grid (search area).
 * 
 * @author Ken Norman
 * 
 */
public final class DFSAlgorithm extends Algorithm {

	/**
	 * Create a depth-first search algorithm.
	 */
	public DFSAlgorithm() {
		super(AlgorithmType.DFS);
	}

/**
 * Executes the depth-first search algorithm.
 */
	@Override
	public List<Node> execute() {
		if (getSearchArea() == null) {
			return null;
		}

		DFSNode startNode = (DFSNode) getSearchArea().getStart();

		execute(startNode);

		return buildSolutionPath();
	}

	/**
	 * Recursive implementation of depth-first search.
	 * @param aNode
	 */
	private void execute(DFSNode aNode) {
		aNode.setVisited(true);

		if (aNode.isFinish()) {
			return; // stop recursing
		} else {

			Map<Direction, Node> walkableNeighbors =
					getSearchArea().getWalkableNeighbors(aNode, false);

			// no crossing diagonally over the path that has already been traversed.
			if (walkableNeighbors.containsKey(Direction.NORTHEAST)) {
				if (((DFSNode) walkableNeighbors.get(Direction.NORTH)).isVisited()
						&& ((DFSNode) walkableNeighbors.get(Direction.EAST)).isVisited())
					walkableNeighbors.remove(Direction.NORTHEAST);
			}
			if (walkableNeighbors.containsKey(Direction.SOUTHEAST)) {
				if (((DFSNode) walkableNeighbors.get(Direction.SOUTH)).isVisited()
						&& ((DFSNode) walkableNeighbors.get(Direction.EAST)).isVisited())
					walkableNeighbors.remove(Direction.SOUTHEAST);
			}
			if (walkableNeighbors.containsKey(Direction.SOUTHWEST)) {
				if (((DFSNode) walkableNeighbors.get(Direction.SOUTH)).isVisited()
						&& ((DFSNode) walkableNeighbors.get(Direction.WEST)).isVisited())
					walkableNeighbors.remove(Direction.SOUTHWEST);
			}
			if (walkableNeighbors.containsKey(Direction.NORTHWEST)) {
				if (((DFSNode) walkableNeighbors.get(Direction.NORTH)).isVisited()
						&& ((DFSNode) walkableNeighbors.get(Direction.WEST)).isVisited())
					walkableNeighbors.remove(Direction.NORTHWEST);
			}
			// end "no crossing..."

			DFSNode neighbor;
			for (Direction dir : walkableNeighbors.keySet()) {

				neighbor = (DFSNode) walkableNeighbors.get(dir);

				if (!neighbor.isVisited()) {
					neighbor.setParent(aNode);
					execute(neighbor); // recursive call to this method
				}
			}
			return; // finish never found
		}
	}
}
