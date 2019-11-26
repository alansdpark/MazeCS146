package park.cs146.project3;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {
	Vertex[][] graph; // Holds matrix for maze.
	int size; // Size of graph, row = size, column = size.
	Random random;
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
				if (i == (size-1)) {
					cell.setDownNeighbor(null);
				}
				else {
					cell.setDownNeighbor(graph[i+1][j]);
				}
				if (j == (size-1)) {
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
		Stack<Vertex> CellStack = new Stack<>();
		int totalCells = size * size;
		Vertex currentCell = start;
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
			
			if (!walledNeighbors.isEmpty()) {
				int rand = random.nextInt(walledNeighbors.size());
				// knock down wall between it and current cell
				if (currentCell.neighbors[0] == walledNeighbors.get(rand)) { // If up
					currentCell.breakTopWall();
					walledNeighbors.get(rand).breakDownWall();
				} else if (currentCell.neighbors[1] == walledNeighbors.get(rand)) { // If right
					currentCell.breakRightWall();
					walledNeighbors.get(rand).breakLeftWall();
				} else if (currentCell.neighbors[2] == walledNeighbors.get(rand)) { // If down
					currentCell.breakDownWall();
					walledNeighbors.get(rand).breakTopWall();
				} else if (currentCell.neighbors[3] == walledNeighbors.get(rand)) { // If left.
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
	 * Prints Maze
	 */
	public void printMaze() {
		System.out.println("");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (graph[i][j].walls[0] == true) {
					if (graph[i][j] == graph[0][0]) {
						System.out.print("+  ");
					}
					else {
						System.out.print("+---");
					}
				}
				else {
					System.out.print("+  ");
				}
			}
			
			System.out.println("|");
			for (int j = 0; j < size; j++) {
				if (j == size - 1) {
					System.out.print("+  ");
				} 
				else {
					System.out.print("+---");
				}
			}
			System.out.println("+");
		}
		
	}
}
