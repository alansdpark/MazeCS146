package park.cs146.project3;

import static org.junit.Assert.*;

import org.junit.Test;

public class MazeTest {

	@Test
	public void test4x4() {
		Maze maze = new Maze(4);
		maze.MazeGeneration();
		maze.printMaze();
	}

}
