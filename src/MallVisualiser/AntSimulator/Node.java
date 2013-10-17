package MallVisualiser.AntSimulator;

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
	
	ArrayList<Node> nodes;
	ArrayList<Integer> distance;
	ArrayList<Double> pheremone;
	
	public Node(int column, int row){
		this.column=column;
		this.row=row;
		nodes=new ArrayList<>();
		distance=new ArrayList<>();
		pheremone=new ArrayList<>();
	}
	
	public boolean addNode(Node node, int length){
		for (int i=0;i<nodes.size();i++){
			if (nodes.get(i)==node){
				return false;
			}
		}
		nodes.add(node);
		distance.add(length);
		pheremone.add(0.0);
		return true;
	}

	public void dropPheremone(double dropAmount, Node node ) {
		for (int i=0;i<nodes.size();i++){
			if (nodes.get(i)==node){
				pheremone.set(i, pheremone.get(i)+(dropAmount/distance.get(i)));
			}
		}
	}

	public void evaporatePheremone(double evaporationRate) {
		for (int i=0;i<nodes.size();i++){
			pheremone.set(i, pheremone.get(i)*evaporationRate);
		}
	}
	
	public Double getPheremone(int i){
		return pheremone.get(i);
	}
	
	public Double getPheremone(Node node){
		for (int i=0;i<nodes.size();i++){
			if (nodes.get(i)==node){
				return pheremone.get(i);
			}
		}
		return 0.0;
	}

	public double getLength(int i) {
		return distance.get(i);
	}

	public double getLength(Node node) {
		for (int i=0;i<nodes.size();i++){
			if (nodes.get(i)==node){
				return distance.get(i);
			}
		}
		return Integer.MAX_VALUE;
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
