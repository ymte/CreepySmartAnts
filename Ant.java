package ci.assignment3;

import java.util.ArrayList;

/*
 * Tom Peeters - 4176510
 * Rutger van den Berg - 4060156
 * Ymte Jan Broekhuizen - 4246586
 * Arne Hutter - 4108566
 */
public class Ant {
	
	ArrayList<Node> path;
	
	public Ant(Node start, Node end){
		path=new ArrayList<>();
		Node loc=start;
		while (loc!=end){
			ArrayList<Node> locNodes=loc.getNodes();
			for (int i=0;i<locNodes.size();i++){
				
			}
		}
	}
	
	public ArrayList<Node> getPath(){
		return path;
	}
}
