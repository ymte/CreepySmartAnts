package ci.assignment3;

import java.util.ArrayList;

/*
 * Tom Peeters - 4176510
 * Rutger van den Berg - 4060156
 * Ymte Jan Broekhuizen - 4246586
 * Arne Hutter - 4108566
 */
public class Node {
	private int column;
	private int row;
	private double pheremone=0;
	
	ArrayList<Node> nodes;
	ArrayList<Integer> distance;
	
	public Node(int column, int row){
		this.column=column;
		this.row=row;
		nodes=new ArrayList<>();
		distance=new ArrayList<>();
	}
	
	public boolean addNode(Node node, int length){
		for (int i=0;i<nodes.size();i++){
			if (nodes.get(i)==node){
				return false;
			}
		}
		nodes.add(node);
		distance.add(length);
		return true;
	}

	public void dropPheremone(double dropAmount) {
		pheremone+=dropAmount;
	}

	public void evaporatePheremone(double evaporationRate) {
		pheremone*=evaporationRate;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public ArrayList<Integer> getDistance() {
		return distance;
	}
}
