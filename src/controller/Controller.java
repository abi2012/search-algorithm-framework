package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Direction;
import model.nodes.Node;
import model.nodes.NodeFactory;
import model.nodes.NodeStatus;
import controller.algorithm.Algorithm;
import controller.algorithm.Algorithm.AlgorithmType;

/**
 * Class used to generate and execute various search algorithms on a 2-dimensional rectangular
 * search area.
 * 
 * @author Ken Norman
 * 
 */
public final class Controller {

	/**
	 * The singleton instance of this controller. Initially null until lazy initialized.
	 */
	private static Controller instance = null;

	/**
	 * The search algorithm to apply.
	 */
	private Algorithm algorithm = null;

	/**
	 * The area to be searched.
	 */
	private SearchArea searchArea;

	private boolean cuttingCornersAllowed = false;

	/**
	 * Create a singleton Controller.
	 */
	private Controller() {
		// Exists only to defeat instantiation.
	}

	/**
	 * Set up the components needed to perform a search:
	 * <ul>
	 * <li>A search area with given <code>width</code> and <code>height</code> in which to search
	 * <li>The algorithm that will conduct the search
	 * </ul>
	 * <p>
	 * This method checks for positive values of <code>height</code> and <code>width</code>, and
	 * checks that <code>a</code> is not <code>null</code>.
	 * 
	 * @param width
	 *            The width of the search area.
	 * @param height
	 *            The height of the search area.
	 * @param a
	 *            An algorithm used to search.
	 * @return <code><b>true</b></code> if the setup succeeded;<br>
	 *         <code><b>false</b></code> if <code>(width < 1 || height < 1 || a == null)</code>.
	 */
	public boolean setUp(int width, int height, Algorithm a) {

		if (width < 1 || height < 1 || a == null) {
			// TODO need to throw some sort of exception here
			return false;
		}

		algorithm = a;
		searchArea = new SearchArea(width, height, a.getType());

		return true;
	}

	/**
	 * Set the location of the start node. This method (as well as {@link #setFinish(int, int)} is
	 * required prior to executing the search algorithm.
	 * <p>
	 * NOTE: If there is an obstacle already set at the <code>(x, y)</code> coordinate, the obstacle
	 * will be replaced. However, if the finish location is already set at the <code>(x, y)</code>
	 * coordinate, the start location will <i>not</i> be set.
	 * 
	 * @param x
	 *            The <code>x</code> coordinate of the start node.
	 * @param y
	 *            The <code>y</code> coordinate of the start node.
	 * @return <code><b>true</b></code> if set<br>
	 *         <code><b>false</b></code> otherwise
	 */
	public boolean setStart(int x, int y) {
		if (searchArea == null)
			return false;

		return searchArea.setStart(x, y);
	}

	/**
	 * Set the location of the finish node. This method (as well as {@link #setStart(int, int)} is
	 * required prior to executing the search algorithm.
	 * 
	 * @param x
	 *            The <code>x</code> coordinate of the start node.
	 * @param y
	 *            The <code>y</code> coordinate of the start node.
	 * @return <code><b>true</b></code> if set<br>
	 *         <code><b>false</b></code> otherwise
	 */
	public boolean setFinish(int x, int y) {
		if (searchArea == null)
			return false;

		return searchArea.setFinish(x, y);
	}

	/**
	 * Adds an obstacle to the search area. An obstacle is a line from a starting point
	 * <code>(x0, y0)</code> to an ending point <code>(x1, y1)</code>, inclusive.
	 * 
	 * @param x0
	 *            The <code>x</code> coordinate of the starting point.
	 * @param y0
	 *            The <code>y</code> coordinate of the starting point.
	 * @param x1
	 *            The <code>x</code> coordinate of the ending point.
	 * @param y1
	 *            The <code>y</code> coordinate of the ending point.
	 * @return <code><b>true</b></code> if the obstacle was added<br>
	 *         <code><b>false</b></code> in either or both of these cases:
	 *         <ul>
	 *         <li>any part of the line is outside the search area
	 *         <li>the line overlaps the start node or the finish node (or both)
	 * @see SearchArea#addObstacle(int, int, int, int)
	 */
	public boolean addObstacle(int x0, int y0, int x1, int y1) {
		if (searchArea == null)
			return false;

		return searchArea.addObstacle(x0, y0, x1, y1);
	}

	/**
	 * Designate a single node as an obstacle.
	 * 
	 * @param x
	 *            The <code>x</code> coordinate of the node.
	 * @param y
	 *            The <code>y</code> coordinate of the node.
	 * @return <code><b>true</b></code> if the obstacle was successfully added<br>
	 *         <code><b>false</b></code> otherwise
	 */
	public boolean addObstacle(int x, int y) {
		return searchArea.addObstacle(x, y, x, y);
	}

	/**
	 * Execute the search algorithm.
	 * 
	 * @return <code><b>true</b></code> if the algorithm successfully executed<br>
	 *         <code><b>false</b></code> otherwise
	 */
	public boolean executeSearch(boolean cuttingCornersAllowed) {
		if (algorithm == null || searchArea == null) {
			System.err.println("Something is wrong.");
			// TODO need to throw some sort of exception here
			return false;
		}

		if (searchArea.hasStart() && searchArea.hasFinish()) {
			this.cuttingCornersAllowed = cuttingCornersAllowed;
			return algorithm.execute() != null;
		}

		return false;
	}

	/**
	 * Prints the search area as a grid, and displays the start node, finish node, all obstacle
	 * nodes, and the solution path.
	 * 
	 * @see NodeStatus#getSymbol()
	 */
	public void print() {
		if (searchArea == null || algorithm == null) {
			System.err.println("Something is wrong.");
			// TODO need to throw some sort of exception here
		} else {
			System.out.println(searchArea.toString());
			System.out.println(this.algorithm.getName());
			searchArea.print();
		}
	}

	/**
	 * Gets the single instance of Controller.
	 * 
	 * @return The Controller.
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	/**
	 * Gets the algorithm.
	 * 
	 * @return The algorithm, or <code>null</code> if never initialized.
	 */
	public Algorithm getAlgorithm() {
		return algorithm;
	}

	/**
	 * Gets the search area. The {@link #setUp(int, int, Algorithm)} method must be invoked first
	 * since it is the only method which can access SearchArea's private constructor. Otherwise,
	 * this method will return <code>null</code>.
	 * 
	 * @return The search area, or <code>null</code> if no search area object exists.
	 */
	public SearchArea getSearchArea() {
		return searchArea;
	}

	public boolean isCuttingCornersAllowed() {
		return this.cuttingCornersAllowed;
	}

	/**
	 * An area to be searched by some search algorithm.
	 * <p>
	 * A search area is a 2-dimensional grid of nodes of a given width and height. One node is
	 * designated as a start node, and one node is designated as a finish node.
	 * <p>
	 * A search are is constructed according to the search algorithm to be used. This is because the
	 * nodes require different state (fields) and behavior (methods) based on which algorithm is
	 * used.
	 * 
	 * @author Ken Norman
	 * 
	 */
	public final class SearchArea {
		/**
		 * The underlying data construct that represents the search area. It is a two-dimensional
		 * array.
		 */
		private ArrayList<ArrayList<Node>> searchAreaArray;

		/**
		 * The width of the search area.
		 */
		private int width;

		/**
		 * The height of the search area.
		 */
		private int height;

		/**
		 * The start location.
		 */
		private Node start = null;

		/**
		 * The finish location.
		 */
		private Node finish = null;

		/**
		 * The collection of obstacle nodes. <code>obstacleNodes</code> is initially empty, and is
		 * only populated by the {@link #addObstacle(int, int, int, int)} method.
		 */
		private Set<Node> obstacleNodes;

		/**
		 * Create a new 2-dimensional search area of given width and height. <i>All</i> nodes are
		 * initially 'empty' (i.e., not any of the following: start location, finish location, or
		 * obstacle.
		 * 
		 * <p>
		 * The constructor is <code>private</code> to ensure a SearchArea can only be instantiated
		 * from within Controller.
		 * 
		 * @param width
		 *            The number of nodes in the 'x' dimension.
		 * @param height
		 *            The number of nodes in the 'y' dimension.
		 * @param type
		 *            The type of search algorithm being used to search (e.g., A*, DFS, BFS, etc.).
		 */
		private SearchArea(int width, int height, AlgorithmType type) {
			this.width = width;
			this.height = height;
			this.obstacleNodes = new HashSet<>();

			// Generate the search area
			searchAreaArray = new ArrayList<ArrayList<Node>>(width);
			Node aNode;

			for (int x = 0; x < width; x++) {
				searchAreaArray.add(new ArrayList<Node>());
				for (int y = 0; y < height; y++) {
					aNode = NodeFactory.buildNode(x, y, type);
					searchAreaArray.get(x).add(aNode);
				}
			}
		}

		/**
		 * Adds an obstacle to the search area. An obstacle is a line from a starting point
		 * <code>(x0, y0)</code> to an ending point <code>(x1, y1)</code>, inclusive.
		 * <p>
		 * This method employs the Bresenham's line algorithm to determine which nodes are to be
		 * designated as obstacle nodes.
		 * 
		 * @param x0
		 *            The <code>x</code> coordinate of the starting point.
		 * @param y0
		 *            The <code>y</code> coordinate of the starting point.
		 * @param x1
		 *            The <code>x</code> coordinate of the ending point.
		 * @param y1
		 *            The <code>y</code> coordinate of the ending point.
		 * @return <code><b>true</b></code> if the obstacle was added<br>
		 *         <code><b>false</b></code> in either or both of these cases:
		 *         <ul>
		 *         <li>any part of the line is outside the search area <li>the line overlaps the
		 *         start node or the finish node (or both)
		 *         </ul>
		 * @see Wikipedia.org: <a href=
		 *      "http://en.wikipedia.org/wiki/Bresenham&#39s_line_algorithm">
		 *      Bresenham&#39s_line_algorithm</a>
		 */
		private boolean addObstacle(int x0, int y0, int x1, int y1) {
			/*
			 * 1. Make sure obstacle will be entirely contained by the search area.
			 */
			if (!coordInSearchArea(x0, y0) || !coordInSearchArea(x1, y1)) {
				return false;
			} else {
				/*
				 * 2. Get the prospective set of nodes to make obstacle (using the Bresenham's line
				 * algorithm).
				 */
				Set<Point> obPoints = new HashSet<>();

				int dx = Math.abs(x1 - x0);
				int dy = Math.abs(y1 - y0);
				int sx = (x0 < x1 ? 1 : -1);
				int sy = (y0 < y1 ? 1 : -1);

				int err = dx - dy;
				int e2;
				do {
					obPoints.add(new Point(x0, y0));
					if (x0 == x1 && y0 == y1)
						break;
					e2 = 2 * err;
					if (e2 > -dy) {
						err = err - dy;
						x0 = x0 + sx;
					}
					if (x0 == x1 && y0 == y1) {
						obPoints.add(new Point(x0, y0));
						break;
					}
					if (e2 < dx) {
						err = err + dx;
						y0 = y0 + sy;
					}
				} while (true);
				/*
				 * 3. Do not add this obstacle if it overlaps the start or finish node(s).
				 */
				for (Point p : obPoints) {
					if (start != null && p == start.getPoint())
						return false;
					if (finish != null && p == finish.getPoint())
						return false;
				}
				/*
				 * 4. Change the nodes' statuses to 'obstacle' and add them to the collection of
				 * obstacle nodes.
				 */
				Node obNode;
				for (Point p : obPoints) {
					obNode = getNode(p);
					obNode.setStatus(NodeStatus.OBSTACLE);
					obstacleNodes.add(obNode);
				}
				return true;
			}
		}

		/**
		 * Gets the node at the <code>(x, y)</code> coordinate.
		 * 
		 * @param x
		 *            The <code>x</code> coordinate.
		 * @param y
		 *            The <code>y</code> coordinate.
		 * @return The {@link Node} object at the <code>(x, y)</code> coordinate if it is contained
		 *         in the search area; <code><b>null</b></code> otherwise.
		 */
		public Node getNode(int x, int y) {
			if (coordInSearchArea(x, y)) {
				return searchAreaArray.get(x).get(y);
			}

			return null;
		}

		/**
		 * Gets the node using a Point, p, to represent its <code>(x, y)</code> coordinate. This
		 * method calls {@link #getNode(int, int)} using <code>(p.x, p.y)</code> as input
		 * parameters.
		 * 
		 * @param p
		 *            A Point object representing the <code>(x, y)</code> coordinate of a Node
		 * @return <code><b>true</b></code> if the point is contained in the search area<br>
		 *         <code><b>false</b></code> otherwise
		 */
		public Node getNode(Point p) {
			return getNode(p.x, p.y);
		}

		/**
		 * Helper method to check whether or not a coordinate is in the search area.
		 * 
		 * @param x
		 *            The <code>x</code> coordinate.
		 * @param y
		 *            The <code>y</code> coordinate.
		 * @return <code><b>true</b></code> if the <code>(x, y)</code> coordinate is contained in
		 *         the search area<br>
		 *         <code><b>false</b></code> otherwise
		 */
		private boolean coordInSearchArea(int x, int y) {
			if (x < 0 || x >= width || y < 0 || y >= height) {
				return false;
			}

			return true;
		}

		/**
		 * Helper method to check whether or not a point is in the search area.
		 * 
		 * @param p
		 *            A Point representing an <code>(x, y)</code> coordinate
		 * @return <code><b>true</b></code> if the <code>(x, y)</code> coordinate is contained in
		 *         the search area<br>
		 *         <code><b>false</b></code> otherwise
		 */
		private boolean coordInSearchArea(Point p) {
			return coordInSearchArea(p.x, p.y);
		}

		/**
		 * Gets the underlying data construct that represents the search area. The search area is
		 * represented as a two-dimensional array.
		 * 
		 * @return The search area array.
		 */
		public ArrayList<ArrayList<Node>> getSearchAreaArray() {
			return searchAreaArray;
		}

		/**
		 * Gets the width of the search area.
		 * 
		 * @return The width.
		 */
		public int getWidth() {
			return width;
		}

		/**
		 * Gets the height of the search area.
		 * 
		 * @return The height.
		 */
		public int getHeight() {
			return height;
		}

		/**
		 * Gets the start node.
		 * 
		 * @return The start node.
		 */
		public Node getStart() {
			return start;
		}

		/**
		 * Set a new start node.
		 * 
		 * @param newStart
		 *            The new start node.
		 * @return <code><b>true</b></code> if the node was successfully identified as the new
		 *         start;<br>
		 *         <code><b>false</b></code> otherwise
		 */
		private boolean setStart(Node newStart) {
			if (newStart == null) {
				return false;
			}

			if (start != null) {
				// Never overwrite the finish node with a start node.
				if (newStart.getPoint() == finish.getPoint()) {
					return false;
				}
				start.setStatus(NodeStatus.EMPTY);
			}

			start = newStart;
			start.setStatus(NodeStatus.START);

			return true;
		}

		/**
		 * Set a new start node.
		 * 
		 * @param x
		 *            The <code>x</code> coordinate of the new start node.
		 * @param y
		 *            The <code>y</code> coordinate of the new start node.
		 * @return <code><b>true</b></code> if the node was successfully identified as the new
		 *         start;<br>
		 *         <code><b>false</b></code> otherwise
		 */
		private boolean setStart(int x, int y) {
			if (coordInSearchArea(x, y)) {
				Node newStart = searchAreaArray.get(x).get(y);
				return setStart(newStart);
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("The coordinate (" + x + ", " + y + ")");
				sb.append("is not contained in search area range: (0, 0) to (");
				sb.append((width - 1) + ", " + (height - 1) + ")\n");
				throw new IndexOutOfBoundsException(sb.toString());
			}
		}

		/**
		 * Checks for whether or not a start node has been designated.
		 * 
		 * @return <code><b>true</b></code> if the start node has been set<br>
		 *         <code><b>false</b></code> otherwise
		 */
		public boolean hasStart() {
			return (start != null);
		}

		/**
		 * Gets the finish node.
		 * 
		 * @return The finish node.
		 */
		public Node getFinish() {
			return finish;
		}

		/**
		 * Set a new finish node.
		 * 
		 * @param newFinish
		 *            The new finish node.
		 * @return <code><b>true</b></code> if the node was successfully identified as the new
		 *         finish;<br>
		 *         <code><b>false</b></code> otherwise
		 */
		private boolean setFinish(Node newFinish) {
			if (newFinish == null) {
				return false;
			}

			if (finish != null) {
				// don't overwrite start node with a finish node
				if (newFinish.getPoint() == start.getPoint()) {
					return false;
				}
				finish.setStatus(NodeStatus.EMPTY);
			}

			finish = newFinish;
			finish.setStatus(NodeStatus.FINISH);

			return true;
		}

		/**
		 * Set a new finish node.
		 * 
		 * @param x
		 *            The <code>x</code> coordinate of the new finish node.
		 * @param y
		 *            The <code>y</code> coordinate of the new finish node.
		 * @return <code><b>true</b></code> if the node was successfully identified as the new
		 *         finish;<br>
		 *         <code><b>false</b></code> otherwise
		 */
		private boolean setFinish(int x, int y) {
			if (coordInSearchArea(x, y)) {
				Node newFinish = searchAreaArray.get(x).get(y);
				return setFinish(newFinish);
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("The coordinate (" + x + ", " + y + ")");
				sb.append("is not contained in search area range: (0, 0) to (");
				sb.append((width - 1) + ", " + (height - 1) + ")\n");
				throw new IndexOutOfBoundsException(sb.toString());
			}
		}

		/**
		 * Checks for whether or not a finish node has been designated.
		 * 
		 * @return <code><b>true</b></code> if the finish node has been set<br>
		 *         <code><b>false</b></code> otherwise
		 */
		public boolean hasFinish() {
			return (finish != null);
		}

		/**
		 * Gets the type of algorithm upon which the generation of this search area was based.
		 * 
		 * @return The type of the algorithm.
		 */
		public AlgorithmType getType() {
			return algorithm.getType();
		}

		/**
		 * Gets the neighbors of a node, <i>excluding</i> obstacle nodes.
		 * 
		 * @param aNode
		 *            The node for which a collection of neighbor nodes is requested.
		 * @return A Map containing the <i>non-obstacle</i> neighbors of a particular node.
		 *         <ul>
		 *         <li><b>Key</b>: the direction from the current node ( <code>aNode</code>) to the
		 *         neighbor node.
		 *         <li><b>Value</b>: the neighbor node.
		 *         </ul>
		 */
		public Map<Direction, Node> getNeighbors(Node aNode) {
			int x = aNode.getX();
			int y = aNode.getY();

			Map<Direction, Node> neighbors = new HashMap<>();

			if (coordInSearchArea(x, y)) {
				Point adjacent;
				for (Direction dir : Direction.values()) {
					adjacent = dir.getAdjacentPoint(x, y);
					Node neighbor = getNode(adjacent);

					if (coordInSearchArea(adjacent) && !neighbor.isObstacle()) {
						neighbors.put(dir, neighbor);
					}
				}
			}

			return neighbors;
		}

		/**
		 * Finds the set of walkable neighbors around a given node.
		 * 
		 * <p>
		 * In the diagrams below, <code><b>[N]</b></code> is the node being inspected for whether
		 * its neighbors are traversable, <code><b>X</b></code> is an obstacle node,
		 * <code><b>?</b></code> is a node which may or may not be traversable directly from
		 * <code><b>[N]</b></code>, and <code><b>!</b></code> is a node which is <i>never</i>
		 * traversable.
		 * 
		 * <pre>
		 *    Case 1          Case 2
		 * ├â”€â”€â”€â”¬â”€â”€â”€â”¬â”€â”€â”€â”�   ├â”€â”€â”€â”¬â”€â”€â”€â”¬â”€â”€â”€â”�
		 * â”‚   â”‚   â”‚ ? â”‚   â”‚   â”‚ X â”‚ ! â”‚
		 * ├â”€â”€â”€â”¼â”€â”€â”€â”¼â”€â”€â”€â”¤   ├â”€â”€â”€â”¼â”€â”€â”€â”¼â”€â”€â”€â”¤
		 * â”‚   â”‚[N]â”‚ X â”‚   â”‚   â”‚[N]â”‚ X â”‚
		 * ├â”€â”€â”€â”¼â”€â”€â”€â”¼â”€â”€â”€â”¤   ├â”€â”€â”€â”¼â”€â”€â”€â”¼â”€â”€â”€â”¤
		 * â”‚   â”‚   â”‚ ? â”‚   â”‚   â”‚   â”‚   â”‚
		 * â””â”€â”€â”€â”´â”€â”€â”€â”´â”€â”€â”€â”˜   â””â”€â”€â”€â”´â”€â”€â”€â”´â”€â”€â”€â”˜
		 * </pre>
		 * 
		 * <p>
		 * Explanation of the two cases:
		 * <ol>
		 * <li>If the <code>cuttingCornersAllowed</code> flag is <code><b>true</b></code>, then the
		 * two <code>?</code> nodes <i>are</i> traversable from node <code>N</code>; if
		 * <code><b>false</b></code>, they are <i>not</i> traversable.
		 * <li>The <code>!</code> node is <i>never</i> traversable from node <code>N</code> (this is
		 * equivalent to walking through a wall).
		 * 
		 * @param aNode
		 *            The node around which to find neighbor nodes.
		 * @param cuttingCornersAllowed
		 *            A flag to indicate whether or not the solution path may traverse diagonally
		 *            past an obstacle node.
		 * @return The Map containing the traversable nodes around <code>aNode </code>.
		 */
		public Map<Direction, Node> getWalkableNeighbors(Node aNode, boolean cuttingCornersAllowed) {
			Map<Direction, Node> walkableNeighbors = getNeighbors(aNode);

			trimDiagonal(walkableNeighbors, Direction.NORTHEAST, cuttingCornersAllowed);
			trimDiagonal(walkableNeighbors, Direction.SOUTHEAST, cuttingCornersAllowed);
			trimDiagonal(walkableNeighbors, Direction.SOUTHWEST, cuttingCornersAllowed);
			trimDiagonal(walkableNeighbors, Direction.NORTHWEST, cuttingCornersAllowed);

			return walkableNeighbors;
		}

		private final void trimDiagonal(Map<Direction, Node> walkableNeighbors, Direction d,
				boolean cuttingCornersAllowed) {

			/* Both conditions prevent walking diagonally through walls */
			if (cuttingCornersAllowed) {
				if (walkableNeighbors.containsKey(d)) {
					if (!walkableNeighbors.containsKey(d.adjacentDirectionCCW()) &&
							!walkableNeighbors.containsKey(d.adjacentDirectionCW()))
						walkableNeighbors.remove(d);
				}
			} else {
				if (walkableNeighbors.containsKey(d)) {
					if (!walkableNeighbors.containsKey(d.adjacentDirectionCCW()) ||
							!walkableNeighbors.containsKey(d.adjacentDirectionCW()))
						walkableNeighbors.remove(d);
				}
			}
			return;
		}

		/**
		 * Prints the search area as a grid, and populates it with the start node, finish node, all
		 * obstacle nodes, and the solution path.
		 */
		private void print() {

			StringBuilder sb = new StringBuilder();

			// ----- top line ---------
			for (int x = 0; x < width; x++) {
				if (x == 0)
					sb.append(TOP_LEFT_CORNER + HORIZONTAL_LINE);
				else
					sb.append(TOP_T + HORIZONTAL_LINE);
			}
			sb.append(TOP_RIGHT_CORNER + "\n");
			// ----- end top line -----

			Node node;
			for (int y = height - 1; y >= 0; y--) {

				// ----- data line ---------
				for (int x = 0; x < width; x++) {
					node = searchAreaArray.get(x).get(y);
					sb.append(VERTICAL_LINE + SPACE + node.getSymbol() + SPACE);
				}
				sb.append(VERTICAL_LINE + "\n");
				// ----- end data line -----

				if (y == 0) {
					// ----- bottom line ---------
					for (int x = 0; x < width; x++) {
						if (x == 0)
							sb.append(BOTTOM_LEFT_CORNER + HORIZONTAL_LINE);
						else
							sb.append(BOTTOM_T + HORIZONTAL_LINE);
					}
					sb.append(BOTTOM_RIGHT_CORNER + "\n\n");
					// ----- end bottom line -----
				} else {
					// ----- middle line ---------
					for (int x = 0; x < width; x++) {
						if (x == 0)
							sb.append(LEFT_T + HORIZONTAL_LINE);
						else
							sb.append(INTERSECTION + HORIZONTAL_LINE);
					}
					sb.append(RIGHT_T + "\n");
					// ----- end middle line -----
				}
			}
			node = null;

			System.out.println(sb.toString());
		}

		/**
		 * Output a string representation of the search area.
		 * 
		 * @return A string of the form:
		 * 
		 *         <pre>
		 * W: &lt;width&gt;, H: &lt;height&gt;, Start: (&lt;x<sub>S</sub>&gt;, &lt;y<sub>S</sub>&gt;), Finish: (&lt;x<sub>F</sub>&gt;, &lt;y<sub>F</sub>&gt;)
		 * </pre>
		 * 
		 *         Example:<br>
		 * 
		 *         <pre>
		 * W: 9, H: 23, Start: (1, 12), Finish: (5, 1)
		 * </pre>
		 */
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("W: " + width + ", H: " + height);

			if (start != null) {
				sb.append(", Start: (" + start.getX() + ", " + start.getY() + ")");
			} else {
				sb.append(", (no start set)");
			}

			if (finish != null) {
				sb.append(", Finish: (" + finish.getX() + ", " + finish.getY() + ")");
			} else {
				sb.append(", (no finish set)");
			}

			return sb.toString();
		}

		/*
		 * Some values for use in the print() method:
		 */

		// ┌
		private static final String TOP_LEFT_CORNER = "\u250C";

		// ┐
		private static final String TOP_RIGHT_CORNER = "\u2510";

		// └
		private static final String BOTTOM_LEFT_CORNER = "\u2514";

		// ┘
		private static final String BOTTOM_RIGHT_CORNER = "\u2518";

		// ┬
		private static final String TOP_T = "\u252C";

		// ┤
		private static final String RIGHT_T = "\u2524";

		// ┴
		private static final String BOTTOM_T = "\u2534";

		// ├
		private static final String LEFT_T = "\u251C";

		// ┼
		private static final String INTERSECTION = "\u253C";

		// │
		private static final String VERTICAL_LINE = "\u2502";

		// ─── (i.e., 3 lines: "─" + "─" + "─")
		private static final String HORIZONTAL_LINE = "\u2500\u2500\u2500";

		private static final String SPACE = " ";

	}

}
