package park.cs146.project3;

/*
 * Vertex represents a singular room/cell
 */
public class Vertex {
	public int label;
	Vertex[] neighbors;
	boolean[] walls; 
	boolean visited;  
	Vertex parent;
	boolean inPath;
	int traversed;
	boolean DFSComplete; // For third "color" for DFS.
	int distance;
	
	
	public Vertex(int label) {
		this.label = label;
		visited = false;
		neighbors = new Vertex[4]; // 0 = up, 1 = right, 2 = down, 3 = left.
		walls = new boolean[4]; // 0 = top wall, 1 = right wall, 2 = down wall, 3 = left wall
		for (int i = 0; i < walls.length; i++) {
			walls[i] = true; // All walls initalized to be up.
		}
		parent = null;
		inPath = false;
		traversed = 0;
		DFSComplete = false;
		distance = 0;
	}
	
	public boolean hasAllWalls() {
		for (int i = 0; i < walls.length; i++) {
			if (walls[i] == false) {
				return false;
			}
		}
		return true;
	}
	
	public void breakTopWall() {
		walls[0] = false;
	}
	
	public void breakRightWall() {
		walls[1] = false;
	}
	
	public void breakDownWall() {
		walls[2] = false;
	}
	
	public void breakLeftWall() {
		walls[3] = false;
	}
	
	public Vertex getTopNeighbor() {
		return neighbors[0];
	}
	
	public void setTopNeighbor(Vertex v) {
		neighbors[0] = v;
	}
	
	public Vertex getRightNeighbor() {
		return neighbors[1];
	}
	
	public void setRightNeighbor(Vertex v) {
		neighbors[1] = v;
	}
	
	public Vertex getDownNeighbor() {
		return neighbors[2];
	}
	
	public void setDownNeighbor(Vertex v) {
		neighbors[2] = v;
	}
	
	public Vertex getLeftNeighbor() {
		return neighbors[3];
	}
	
	public void setLeftNeighbor(Vertex v) {
		neighbors[3] = v;
	}
}
