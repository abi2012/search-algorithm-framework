package model.nodes;

/**
 * A concrete node for use by an A* (A-star) search algorithm.
 * 
 * @author Ken Norman
 * 
 */
public final class AStarNode extends Node implements Comparable<AStarNode> {

	/**
	 * The cost to reach this node from the start node. Often identified as <code>g(n)</code> or
	 * <code>G</code>.
	 */
	private double costFromStart;

	/**
	 * An estimate of the expected cost to reach the finish node. Often identified as
	 * <code>h(n)</code> or <code>H</code>.
	 */
	private double heuristicCostToFinish;

	/**
	 * A flag to indicate if this node is in the list of 'open' nodes (still possible candidates for
	 * inclusion in the solution). The full collection of open nodes represents the search horizon.
	 */
	private boolean open;

	/**
	 * A flag to indicate if this node is in the list of 'closed' nodes (already searched).
	 */
	private boolean closed;

	/**
	 * 
	 * Creates a new node for use by the A* (A-star) algorithm. This class extends the abstract
	 * class {@link Node}.
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @see Node#Node(int, int)
	 */
	public AStarNode(int x, int y) {
		super(x, y);
		initialize();
	}

	/**
	 * Resets all instance variables.
	 */
	private void initialize() {
		parent = null; // no parent unless assigned via setter
		costFromStart = Double.MAX_VALUE;
		heuristicCostToFinish = 0;
		open = false;
		closed = false;
	}

	@Override
	public void setStatus(NodeStatus status) {
		super.setStatus(status);

		if (status.toString().startsWith("WAYPOINT"))
			return; // do nothing more

		initialize();

		if (status == NodeStatus.START) {
			parent = this;
			costFromStart = 0;
		}

		if (status == NodeStatus.OBSTACLE)
			heuristicCostToFinish = Double.MAX_VALUE;

		return;
	}

	/**
	 * Gets the cost to reach this node from the start node. Often identified as <code>g(n)</code>
	 * or <code>G</code>.
	 * 
	 * @return The cost.
	 */
	public double getCostFromStart() {
		return costFromStart;
	}

	/**
	 * Sets the actual cost from start node to the current node along the current path.
	 * 
	 * @param g
	 *            The actual cost.
	 */
	public void setCostFromStart(double g) {
		this.costFromStart = g;
	}

	/**
	 * Gets an estimate of the expected cost to reach the finish node. Often identified as
	 * <code>h(n)</code> or <code>H</code>.
	 * 
	 * @return The heuristic cost to finish.
	 */
	public double getHeuristicCostToFinish() {
		return heuristicCostToFinish;
	}

	/**
	 * Sets the estimated cost to reach the finish node from this node. The value is determined by
	 * the applied heuristic.
	 * 
	 * @param h
	 *            An estimated cost based on a heuristic.
	 */
	public void setHeuristicCostToFinish(double h) {
		this.heuristicCostToFinish = h;
	}

	/**
	 * Gets the sum, <code>f(n)</code>, of the actual cost from the start node, <code>g(n)</code>,
	 * and the estimated cost to reach the finish node, <code>h(n)</code>.
	 * 
	 * @return <code>f(n)</code>, where <code>f(n) = g(n) + h(n)</code>.
	 * @see #getCostFromStart()
	 * @see #getHeuristicCostToFinish()
	 */
	public double getEstimatedTotalCost() {
		return costFromStart + heuristicCostToFinish;
	}

	/**
	 * A flag to indicate if this node is in the list of 'open' nodes (still possible candidates for
	 * inclusion in the solution). The full collection of open nodes represents the search horizon.
	 * 
	 * @return <code><b>true</b></code> if the open list contains this node<br>
	 *         <code><b>false</b></code> otherwise.
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * Identify the node as 'open' (i.e., in the open list).
	 */
	public void setOpen() {
		open = true;
		closed = false;
	}

	/**
	 * A flag to indicate if this node is in the list of 'closed' nodes (already searched).
	 * 
	 * @return <code><b>true</b></code> if the closed list contains this node<br>
	 *         <code><b>false</b></code> otherwise.
	 */
	public boolean isClosed() {
		return closed;
	}

	/**
	 * Identify the node as 'closed' (i.e., in the closed list).
	 */
	public void setClosed() {
		closed = true;
		open = false;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			AStarNode other = (AStarNode) obj;
			return (this.costFromStart == other.costFromStart &&
					this.heuristicCostToFinish == other.heuristicCostToFinish &&
					this.open == other.open && this.closed == other.closed);
		}
		return false;
	}

	/**
	 * Compares the return values of {@link #getEstimatedTotalCost()} for two nodes.
	 * 
	 * @return <code><b>1</b></code> if this node's total cost is greater than the other node's
	 *         total cost<br>
	 *         <code><b>0</b></code> if both nodes have the same total cost<br>
	 *         <code><b>-1</b></code> if this node's total cost is less than the other node's total
	 *         cost
	 * @param other
	 *            The node to compare
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AStarNode other) {
		if (this.getEstimatedTotalCost() < other.getEstimatedTotalCost()) {
			return -1;
		} else if (this.getEstimatedTotalCost() > other.getEstimatedTotalCost()) {
			return 1;
		} else {
			return 0;
		}
	}

}
