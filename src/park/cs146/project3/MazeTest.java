package park.cs146.project3;

import static org.junit.Assert.*;

import org.junit.Test;

public class MazeTest {

	@Test
	public void test4x4() {
		Maze maze = new Maze(4);
		maze.MazeGeneration();
		maze.printMaze();
		
		// DFS
		
		// BFS
		
		// Assert that both paths from DFS and BFS are equal
	}
	
	@Test
	public void test6x6() {
		Maze maze = new Maze(6);
		maze.MazeGeneration();
		maze.printMaze();
	}
	
	@Test
	public void test8x8() {
		Maze maze = new Maze(8);
		maze.MazeGeneration();
		maze.printMaze();
	}
	
	@Test
	public void test16x16() {
		Maze maze = new Maze(16);
		maze.MazeGeneration();
		maze.printMaze();
	}

}
