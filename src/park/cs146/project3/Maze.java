package park.cs146.project3;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {
	Vertex[][] graph; // Holds matrix for maze.
	int size; // Size of graph, row = size, column = size.
	Random random; // To generate random integer
	Vertex start; // Top left cell
	Vertex end; // Bottom right cell.
	int traversed; // Keeps track for traversals (BFS/DFS).
	
	public Maze(int size) {
		this.size = size;
		random = new Random();
		graph = new Vertex[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				graph[i][j] = new Vertex(i+j);
			}
		}
		start = graph[0][0];
		end = graph[size-1][size-1];
		traversed = 1;
		MazeInit();
	}
	
	/*
	 * Inits Maze and sets corresponding neighbors in graph/matrix.
	 */
	public void MazeInit() {
		// Set all neighbors
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Vertex cell = graph[i][j];
				if (i == 0) { // If top row
					cell.setTopNeighbor(null);
				}
				else { // Not top row
					cell.setTopNeighbor(graph[i-1][j]);
				}
				if (j == 0) { // Left most row
					cell.setLeftNeighbor(null);
				}
				else { // Not left most
					cell.setLeftNeighbor(graph[i][j-1]);
				}
				if (i == (size-1)) { // Bottom row
					cell.setDownNeighbor(null);
				}
				else {
					cell.setDownNeighbor(graph[i+1][j]);
				}
				if (j == (size-1)) { // Right row
					cell.setRightNeighbor(null);
				}
				else {
					cell.setRightNeighbor(graph[i][j+1]);
				}
			}
		}
	}
	
	/*
	 * Sets all cells in maze to not visited.
	*/
	public void ResetMaze() {
		for (int i = 0; i < size; i++) { 
			for (int j = 0; j < size; j++) {
				graph[i][j].visited = false;
			}
		}
	}
	
	/*
	 * Generates maze with given algorithm.
	*/
	public void MazeGeneration() {
		ResetMaze();
		MazeInit();
		Stack<Vertex> CellStack = new Stack<>();
		int totalCells = size * size;
		Vertex currentCell = start; // Always start at top left corner.
		currentCell.visited = true;
		int visitedCells = 1;
		
		while (visitedCells < totalCells) {
			ArrayList<Vertex> walledNeighbors = new ArrayList<>();
			// find all neighbors of currentCell with all walls intact
			for (int i = 0; i < currentCell.neighbors.length; i++) {
				if (currentCell.neighbors[i] != null) {
					if (currentCell.neighbors[i].hasAllWalls()) {
						walledNeighbors.add(currentCell.neighbors[i]);
					}
				}
			}
			
			if (walledNeighbors.size() > 0) {
				int rand = random.nextInt(walledNeighbors.size()); // from 0 to size-1
				// knock down wall between it and current cell
				if (currentCell.getTopNeighbor() == walledNeighbors.get(rand)) { // If currentCells top neighbor == selected walledNeighbor
					currentCell.breakTopWall();
					walledNeighbors.get(rand).breakDownWall();
				} else if (currentCell.getRightNeighbor() == walledNeighbors.get(rand)) { // If right
					currentCell.breakRightWall();
					walledNeighbors.get(rand).breakLeftWall();
				} else if (currentCell.getDownNeighbor() == walledNeighbors.get(rand)) { // If down
					currentCell.breakDownWall();
					walledNeighbors.get(rand).breakTopWall();
				} else if (currentCell.getLeftNeighbor() == walledNeighbors.get(rand)) { // If left.
					currentCell.breakLeftWall();
					walledNeighbors.get(rand).breakRightWall();
				}
				// push currentCell location on CellStack
				CellStack.push(currentCell);
				// make the new cell currentCell
				currentCell = walledNeighbors.get(rand);
				currentCell.visited = true;
				// add 1 to visitedcells
				visitedCells++;
			}
			else {
				if (!CellStack.isEmpty()) {
					currentCell = CellStack.pop();
				}
			}
		}
	}
	
	/*
	 * Depth First Search to traverse maze
	 */
	public void DFS() {
		Stack<Vertex> stack = new Stack<>();
		stack.push(start);
		
		while (!stack.isEmpty() && stack.peek() != end) {
			Vertex v = stack.pop();
			
			// Iterate through neighbors and keep going until cannot go any further.
			for (int i = 0; i < v.neighbors.length; i++) {
				Vertex neighbor = v.neighbors[i];
				
			}
		}
	}
	
	/*
	 * Breadth First Search to traverse maze
	 */
	public void BFS() {
		
	}
	
	/*
	 * Prints Maze
	 */
	public void printMaze() {
		System.out.println("MAZE " + size + "x" + size);
		String mazeOut = "";

		int rows = 2; // How many rows there are.
		for (int i = 0; i < size; i++) {
			if (i == size - 1) { // if i is at last row
				rows = 3; // then set to bottom layer.
			}
			for (int j = 1; j <= rows; j++) {
				// 1 = top walls, 2 = left/right, 3 = down
				if (j == 1) {
					mazeOut += "+"; // For top left
				}
				if (j == 2) {
					mazeOut += "|"; // For left
				}
				if (j == 3) {
					mazeOut += "+"; // For bottom left.
				}

				for (int k = 0; k < size; k++) { // Iterate through columns
					Vertex v = graph[i][k];
					
					if (j == 1) {
						if ((v.walls[0] != false) && (v != start) && (v != end)) { // if top walls are not broken down/ not start or end
							mazeOut += "-";
						}
						else {
							mazeOut += " ";
						}
						mazeOut += "+";
					}
					else if (j == 2) {
						mazeOut += " ";
						if (v.walls[1] != false) { // if right wall is not broken down
							mazeOut += "|";
						}
						else {
							mazeOut += " ";
						}
					}
					else if (j == 3) { // If bottom layer
						if ((v.walls[2] != false) && (v != start) && (v != end)) { // if down wall is not broken down/ not start or end
							mazeOut += "-";
						}
						else {
							mazeOut += " ";
						}
						mazeOut += "+";
					}
				}
				mazeOut += "\n"; // Add a new line
			}
		}
		System.out.println(mazeOut); // Print out to system.
	}
}
