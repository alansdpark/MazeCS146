package park.cs146.project3;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * Main/Test File
 * Will generate random 4x4, 6x6, 8x8, 16x16 mazes and solve them through DFS and BFS algorithms.
 * Will print out the original maze, DFS traversal, BFS traversal, and final paths given by both algorithms.
 */
public class MazeTest {

	@Test
	public void test4x4() {
		System.out.println("MAZE 4x4");
		
		// Original Maze
		Maze maze = new Maze(4);
		maze.MazeGeneration();
		System.out.println(maze.printMaze(1));
		
		// DFS
		System.out.println("DFS");
		maze.DFS();
		System.out.println(maze.printMaze(2));
		String DFSPath = maze.printMaze(3);
		
		// BFS
		System.out.println("BFS");
		maze.ResetMaze();
		maze.BFS();
		System.out.println(maze.printMaze(2));
		String BFSPath = maze.printMaze(3);
		
		// Print DFS Final Path
		System.out.println("DFS Final Path");
		System.out.println(DFSPath);
		// Print BFS Final Path
		System.out.println("BFS Final Path");
		System.out.println(BFSPath);
		// Assert that both final paths from DFS and BFS are equal
		assertEquals(BFSPath, DFSPath);
	}
	
	@Test
	public void test6x6() {
System.out.println("MAZE 6x6");
		
		// Original Maze
		Maze maze = new Maze(6);
		maze.MazeGeneration();
		System.out.println(maze.printMaze(1));
		
		// DFS
		System.out.println("DFS");
		maze.DFS();
		System.out.println(maze.printMaze(2));
		String DFSPath = maze.printMaze(3);
		
		// BFS
		System.out.println("BFS");
		maze.ResetMaze();
		maze.BFS();
		System.out.println(maze.printMaze(2));
		String BFSPath = maze.printMaze(3);
		
		// Print DFS Final Path
		System.out.println("DFS Final Path");
		System.out.println(DFSPath);
		// Print BFS Final Path
		System.out.println("BFS Final Path");
		System.out.println(BFSPath);
		// Assert that both final paths from DFS and BFS are equal
		assertEquals(BFSPath, DFSPath);
	}
	
	@Test
	public void test8x8() {
System.out.println("MAZE 8x8");
		
		// Original Maze
		Maze maze = new Maze(8);
		maze.MazeGeneration();
		System.out.println(maze.printMaze(1));
		
		// DFS
		System.out.println("DFS");
		maze.DFS();
		System.out.println(maze.printMaze(2));
		String DFSPath = maze.printMaze(3);
		
		// BFS
		System.out.println("BFS");
		maze.ResetMaze();
		maze.BFS();
		System.out.println(maze.printMaze(2));
		String BFSPath = maze.printMaze(3);
		
		// Print DFS Final Path
		System.out.println("DFS Final Path");
		System.out.println(DFSPath);
		// Print BFS Final Path
		System.out.println("BFS Final Path");
		System.out.println(BFSPath);
		// Assert that both final paths from DFS and BFS are equal
		assertEquals(BFSPath, DFSPath);
	}
	
	@Test
	public void test16x16() {
System.out.println("MAZE 16x16");
		
		// Original Maze
		Maze maze = new Maze(16);
		maze.MazeGeneration();
		System.out.println(maze.printMaze(1));
		
		// DFS
		System.out.println("DFS");
		maze.DFS();
		System.out.println(maze.printMaze(2));
		String DFSPath = maze.printMaze(3);
		
		// BFS
		System.out.println("BFS");
		maze.ResetMaze();
		maze.BFS();
		System.out.println(maze.printMaze(2));
		String BFSPath = maze.printMaze(3);
		
		// Print DFS Final Path
		System.out.println("DFS Final Path");
		System.out.println(DFSPath);
		// Print BFS Final Path
		System.out.println("BFS Final Path");
		System.out.println(BFSPath);
		// Assert that both final paths from DFS and BFS are equal
		assertEquals(BFSPath, DFSPath);
	}
}
