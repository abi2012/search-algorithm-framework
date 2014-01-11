package controller.algorithm.strategies;

import java.util.List;
import java.util.Map;

import model.Direction;
import model.nodes.DFSNode;
import model.nodes.Node;
import controller.algorithm.Algorithm;
import controller.algorithm.AlgorithmType;

/**
 * This class is an implementation of the depth-first search algorithm. It operates on a 2-D grid
 * (search area).
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
	 * 
	 * @param aNode
	 *            The next node in the recursion from which to initiate a DFS.
	 */
	private void execute(DFSNode aNode) {
		aNode.setVisited(true);

		if (aNode.isFinish()) {
			return; // stop recursing
		} else {

			Map<Direction, Node> walkableNeighbors =
					getSearchArea().getWalkableNeighbors(aNode,
							controller.isCuttingCornersAllowed());

			// no crossing diagonally over the path that has already been traversed.
			trimDiagonal(walkableNeighbors, Direction.NORTHEAST, controller.isCuttingCornersAllowed());
			trimDiagonal(walkableNeighbors, Direction.SOUTHEAST, controller.isCuttingCornersAllowed());
			trimDiagonal(walkableNeighbors, Direction.SOUTHWEST, controller.isCuttingCornersAllowed());
			trimDiagonal(walkableNeighbors, Direction.NORTHWEST, controller.isCuttingCornersAllowed());
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

	/**
	 * Removes from consideration a diagonally adjacent node if the current node and the adjacent
	 * node are both adjacent to the same two previously-visited nodes. This has the effect of
	 * preventing the solution path from crossing over itself.
	 * 
	 * @param walkableNeighbors
	 *            The walkable neighbors around a node.
	 * @param d
	 *            The direction to the node which is to be inspected.
	 * @param cuttingCornersAllowed
	 *            The flag which indicates whether or not cutting corners is allowed.
	 */
	private final void trimDiagonal(Map<Direction, Node> walkableNeighbors, Direction d,
			boolean cuttingCornersAllowed) {

		if (walkableNeighbors.containsKey(d)) {
			DFSNode ccwNode = (DFSNode) walkableNeighbors.get(d.adjacentDirectionCCW());
			DFSNode cwNode = (DFSNode) walkableNeighbors.get(d.adjacentDirectionCW());

			if (ccwNode != null && cwNode != null && ccwNode.isVisited() && cwNode.isVisited())
				walkableNeighbors.remove(d);
		}
		return;
	}
}
