package controller.algorithm.strategies;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import model.Direction;
import model.nodes.AStarNode;
import model.nodes.Node;
import controller.algorithm.Algorithm;
import controller.algorithm.AlgorithmType;
import controller.heuristic.Heuristic;

/**
 * This class is an implementation of the A* (A-Star) algorithm. It operates on a 2-D grid (search
 * area).
 * 
 * @author Ken Norman
 * 
 */
public final class AStarAlgorithm extends Algorithm {
	/**
	 * The heuristic to use to calculate <code>h(n)</code> as used in the A* algorithm.
	 */
	private Heuristic heuristic;

	/**
	 * The collection of nodes to be searched. Uses the Collection PriorityQueue to ensure a natural
	 * ordering (based upon f(n) value).
	 */
	private Queue<Node> openList = new PriorityQueue<>();

	/**
	 * The collection of nodes already searched. Order is not important.
	 */
	private Set<Node> closedList = new HashSet<>();

	/**
	 * Create an A* (A-star) algorithm with h(n) heuristic.
	 * 
	 * @param h
	 *            The h(n) cost heuristic for use by the A* algorithm.
	 */
	public AStarAlgorithm(Heuristic h) {
		super(AlgorithmType.A_STAR);
		heuristic = h;
	}

	/**
	 * Executes the A* algorithm.
	 * 
	 * @return A list of nodes ordered from start to finish, which represent the lowest cost path.
	 * @see controller.algorithm.Algorithm#execute()
	 */
	public List<Node> execute() {
		if (getSearchArea() == null || heuristic == null) {
			return null;
		}

		openList.clear();
		closedList.clear();

		// Inital conditions
		AStarNode startNode = (AStarNode) getSearchArea().getStart();
		AStarNode finishNode = (AStarNode) getSearchArea().getFinish();
		addToOpenList(startNode);
		startNode.setCostFromStart(0);
		setHeuristicCostToFinish(startNode);

		AStarNode currentNode;
		while (!(finishNode.isClosed() || openList.isEmpty())) {

			// Get node with lowest cost from start
			currentNode = (AStarNode) openList.peek();

			// Move current node to the closed (already searched) list
			moveToClosedList(currentNode);

			Map<Direction, Node> walkableNeighbors =
					getSearchArea().getWalkableNeighbors(currentNode, false);

			AStarNode neighbor;
			double tempCostFromStart;
			for (Direction dir : walkableNeighbors.keySet()) {

				neighbor = (AStarNode) walkableNeighbors.get(dir);
				tempCostFromStart = currentNode.getCostFromStart() + dir.getCostToNeighbor();

				// ignore neighbors in the closed list
				if (neighbor.isClosed()) {
					continue;
				}

				// neighbor is not in the open list
				if (!neighbor.isOpen()) {
					addToOpenList(neighbor);
					neighbor.setParent(currentNode);
					neighbor.setCostFromStart(tempCostFromStart);
					setHeuristicCostToFinish(neighbor);
				}

				// neighbor is already in the open list
				if (neighbor.isOpen()) {
					if (tempCostFromStart < neighbor.getCostFromStart()) {
						neighbor.setParent(currentNode);
						neighbor.setCostFromStart(tempCostFromStart);
					}
				}
			}
		}
		return buildSolutionPath();
	}

	/**
	 * Use Heuristic to calculate the cost to traverse from <code>aNode</code> to the finish node.
	 * 
	 * @param aNode
	 *            The node from which the cost should be calculated.
	 */
	private void setHeuristicCostToFinish(AStarNode aNode) {
		if (!aNode.isObstacle()) {
			Point here = aNode.getPoint();
			Point finish = getSearchArea().getFinish().getPoint();
			double cost = heuristic.execute(here, finish);
			aNode.setHeuristicCostToFinish(cost);
		}
	}

	/**
	 * Add a node into the A* open list.
	 * 
	 * @param aNode
	 *            The node to add.
	 */
	private void addToOpenList(AStarNode aNode) {
		aNode.setOpen();
		openList.add(aNode);
	}

	/**
	 * Moves a node from the A* open list to the A* closed list.
	 * 
	 * @param aNode
	 *            The node to move.
	 */
	private void moveToClosedList(AStarNode aNode) {
		aNode.setClosed();
		closedList.add(aNode);
		openList.remove(aNode);
	}

}
