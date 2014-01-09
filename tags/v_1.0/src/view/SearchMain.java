package view;

import controller.Controller;
import controller.StopWatch;
import controller.algorithm.Algorithm;
import controller.algorithm.strategies.AStarAlgorithm;
import controller.algorithm.strategies.BFSAlgorithm;
import controller.algorithm.strategies.DFSAlgorithm;
import controller.heuristic.strategies.Infinity;
import controller.heuristic.strategies.Manhattan;

/**
 * Class containing the {@link #main(String[])} method for searching.
 * 
 * @author Ken Norman
 * 
 */
@SuppressWarnings("unused")
public final class SearchMain {

	public static void main(String[] args) {
		
		Controller c = Controller.getInstance();

		// 1. Set up a search
		Algorithm aStarOne = new AStarAlgorithm(new Manhattan());
		Algorithm aStarTwo = new AStarAlgorithm(new Infinity());
		Algorithm dfs = new DFSAlgorithm();
		Algorithm bfs = new BFSAlgorithm();

		c.setUp(30, 15, aStarOne);
//		c.setUp(30, 15, aStarTwo);
//		c.setUp(30, 15, bfs);
//		c.setUp(30, 15, dfs);

		// 2. Set start and finish locations
		c.setStart(8, 5);
		c.setFinish(21, 7);

		// 3. Add some obstacles
		c.addObstacle(1, 1, 11, 5);
		c.addObstacle(5, 10, 13, 10);
		c.addObstacle(13, 10, 13, 2);
		c.addObstacle(25, 10, 17, 10);
		c.addObstacle(17, 10, 17, 4);
		c.addObstacle(17, 4, 25, 4);
		c.addObstacle(14, 14);
		c.addObstacle(15, 11);
		c.addObstacle(22, 6);

		// 4. Execute the search
		StopWatch sw = new StopWatch();
		sw.start();
		c.executeSearch();
		sw.stop();

		// 5. Display the result
		c.print();
		System.out.println("Search time: " + sw.getElapsedTime() + " milliseconds");
	}
}
