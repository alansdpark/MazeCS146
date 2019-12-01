package park.cs146.project3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/*
 * Alan Park
 * Alex Vuong
 * CS 146 Project 3
 */

public class Maze {
	Vertex[][] graph; // Holds matrix for maze.
	int size; // Size of graph, row = size, column = size.
	Random random; // To generate random integer
	Vertex start; // Top left cell
	Vertex end; // Bottom right cell.
	boolean[] traversedValue; // Keeps track for traversals (BFS/DFS).
	
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
		traversedValue = new boolean[size*size];
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
				graph[i][j].inPath = false;
			}
		}
		traversedValue = new boolean[size * size];
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
	 * Shows path created by DFS or BFS.
	 * Starts at end room, and backtracks through Vertex parents back to the first room.
	 */
	public void SetPath() {
		Vertex v = end;
		while (v != start) {
			v.inPath = true;
			v = v.parent;
		}
		v.inPath = true;
	}
	
	/*
	 * Depth First Search to traverse maze
	 * Uses Stack
	 * TODO: Fix Error
	 */
	public void DFS() {
		ResetMaze();
		Stack<Vertex> stack = new Stack<>();
		stack.push(start);
		int t = 0; // Keep track of order used in traversal.
		while ((!stack.isEmpty()) && (stack.peek() != end)) {
			Vertex v = stack.pop();
			// Iterate through neighbors and keep going until cannot go any further.
			for (int i = 0; i < v.neighbors.length; i++) {
				Vertex neighbor = v.neighbors[i];
				if ((v.walls[i] == false) && (neighbor != null) && (neighbor.visited == false)) {
					neighbor.visited = true;
					if (neighbor.traversed == 0) {
						if (traversedValue[t] == false) { // If t value has not already been used.
							neighbor.traversed = t+1;
							traversedValue[t] = true;
						} else {
							t++;
							neighbor.traversed = t + 1;
							traversedValue[t] = true;
						}
					}						

					neighbor.parent = v;
					stack.push(neighbor);
				}
			}
			v.DFSComplete = true; // Mark as black.
		}
		SetPath();
	}
	
	/*
	 * Breadth First Search to traverse maze
	 * Uses Queue
	 * TODO: Fix Error
	 */
	public void BFS() {
		ResetMaze();
		int t = 0;
		Queue<Vertex> queue = new LinkedList<>();
		queue.add(start);
		while ((!queue.isEmpty()) && (!queue.peek().equals(end))) {
			Vertex v = queue.remove();
			for (int i = 0; i < v.neighbors.length; i++) {
				Vertex neighbor = v.neighbors[i];
				if (v.walls[i] == false && neighbor != null && neighbor.visited == false) {
					neighbor.visited = true;
					if (neighbor.traversed == 0) {
						if (traversedValue[t] == false) {
							neighbor.traversed = t;
							traversedValue[t] = true;
						} else {
							t++;
							neighbor.traversed = t;
							traversedValue[t] = true;
						}
					}

					neighbor.parent = v;
					queue.add(neighbor);
				}
			}
			v.DFSComplete = true;
		}
		SetPath();
	}
	
	/*
	 * Prints Maze
	 */
	public String printMaze(int type) {
		String mazeOut = "";
		int rows = 2; // How many rows there are. Keeps track for whether to print final portion or not.
		if (type == 1) {
			for (int i = 0; i < size; i++) { // Iterating through each row.
				if (i == size - 1) { // if i is at last row
					rows = 3; // then set to bottom layer.
				}
				for (int j = 1; j <= rows; j++) { // Each cell row's individual corner/wall
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
							if ((v.walls[0] == true) && (v != start) && (v != end)) { // if top walls are not broken down/ not start or end
								mazeOut += "-";
							}
							else {
								mazeOut += " ";
							}
							mazeOut += "+";
						}
						else if (j == 2) {
							mazeOut += " ";
							if (v.walls[1] == true) { // if right wall is not broken down
								mazeOut += "|";
							}
							else {
								mazeOut += " ";
							}
						}
						else if (j == 3) { // If bottom layer
							if ((v.walls[2] == true) && (v != start) && (v != end)) { // if down wall is not broken down/ not start or end
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
		}
		if (type == 2) {
			for (int i = 0; i < size; i++) { // Iterating through each row.
				if (i == size - 1) { // if i is at last row
					rows = 3; // then set to bottom layer.
				}
				for (int j = 1; j <= rows; j++) { // Each cell row's individual corner/wall
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
							if ((v.walls[0] == true) && (v != start) && (v != end)) { // if top walls are not broken down/ not start or end
								mazeOut += "-";
							}
							else {
								mazeOut += " ";
							}
							mazeOut += "+";
						}
						else if (j == 2) {
							if (v == start) {
								mazeOut += 0;
							} else if (v.parent == null) {
								mazeOut += " ";
							} else {
								mazeOut += ((v.traversed)%10); // Print only single digit numbers.
							}

							
							if (v.walls[1] == true) { // if right wall is not broken down
								mazeOut += "|";
							}
							else {
								mazeOut += " ";
							}
						}
						else if (j == 3) { // If bottom layer
							if ((v.walls[2] == true) && (v != start) && (v != end)) { // if down wall is not broken down/ not start or end
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
		}
		if (type == 3) {
			for (int i = 0; i < size; i++) { // Iterating through each row.
				if (i == size - 1) { // if i is at last row
					rows = 3; // then set to bottom layer.
				}
				for (int j = 1; j <= rows; j++) { // Each cell row's individual corner/wall
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
							if ((v.walls[0] == true) && (v != start) && (v != end)) { // if top walls are not broken down/ not start or end
								mazeOut += "-";
							} 
							else {
								mazeOut += " ";
							}
							mazeOut += "+";
						}
						else if (j == 2) {
							if (v == null) {
								mazeOut += " ";
							}
							else if (v.inPath) {
								mazeOut += "#";
							}
							else {
								mazeOut += " ";
							}
							if (v.walls[1] == true) { // if right wall is not broken down
								mazeOut += "|";
							}
							else {
								mazeOut += " ";
							}
						}
						else if (j == 3) { // If bottom layer
							if ((v.walls[2] == true) && (v != start) && (v != end)) { // if down wall is not broken down/ not start or end
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
		}
		return mazeOut; // Print out to system.
	}
}
