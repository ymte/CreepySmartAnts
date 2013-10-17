package MallVisualiser.AntSimulator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import MallVisualiser.Editor.World;

/*
 * Tom Peeters - 4176510
 * Rutger van den Berg - 4060156
 * Ymte Jan Broekhuizen - 4246586
 * Arne Hutter - 4108566
 */
public class Main {

	static int maxIterations=100;
	static int ants=10;
	static double pheremoneDropped=1;
	static double evaporationRate=0.9;
	static double a=1;
	static double b=1;
	static ArrayList<Node> nodes=new ArrayList<>();
	
	
	public static void main2(World world) {
		createNodes(world);
		System.out.println(nodes.size());
		Node startNode = world.start;
		Node endNode = world.end;
		linkNodes(world,startNode);
		if (!endNode.getNodes().isEmpty()){
			ArrayList<Node> bestPath = null;
			int bestLength=Integer.MAX_VALUE;
			for (int i=0;i<maxIterations;i++){
				ArrayList<Node> path=iteration(startNode,endNode);
				int length=0;
				for (int k=0;k<path.size()-1;k++){
					length+=path.get(k).getLength(path.get(k+1));
				}
				if (length<bestLength){
					bestLength=length;
					bestPath=path;
				}
			}
			System.out.println("("+bestPath.get(bestPath.size()-1).getColumn()+","+bestPath.get(bestPath.size()-1).getRow()+")");
			//savePath("C:\\Users\\Tom\\Dropbox\\Intelligence Practicum\\Practicum 3\\GradingMaze EASY v2 solution.txt",bestPath);
		} else {
			System.out.println("No path to end point");
		}
	}
	
	private static ArrayList<Node> iteration(Node start, Node end){
		ArrayList<ArrayList<Node>> paths=new ArrayList<>();
		for (int i=0;i<ants;i++){
			Ant ant=new Ant(start,end,a,b);
			paths.add(ant.getPath());
		}
		for (int i=0;i<nodes.size();i++){
			nodes.get(i).evaporatePheremone(evaporationRate);
		}
		for (int i=0;i<paths.size();i++){
			for (int k=0;k<paths.get(i).size()-1;k++){
				paths.get(i).get(k).dropPheremone(pheremoneDropped,paths.get(i).get(k+1));
				paths.get(i).get(k+1).dropPheremone(pheremoneDropped,paths.get(i).get(k));
			}
		}
		return paths.get(0);
	}
	
	@SuppressWarnings("unused")
	private static void savePath(String filename, ArrayList<Node> path){
		try {
			File file=new File(filename);
			file.createNewFile();
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for (int i=0;i<path.size();i++){
				output.write(";");
			}
			output.close();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private static void createNodes(World world){
		for(int column=0;column<world.getNumElementsX();column++){
			for(int row=0;row<world.getNumElementsY();row++){
				if (world.isAccessible(column,row)){
					int connections=
							(world.isAccessible(column,row+1)?1:0) +
							(world.isAccessible(column+1,row)?1:0) +
							(world.isAccessible(column,row-1)?1:0) +
							(world.isAccessible(column-1,row)?1:0) ;
					if (connections!=0 && connections!=2){
						nodes.add(new Node(column,row));
						System.out.println("("+column+","+row+")");
					}
				}
			}
		}
	}
	
	private static void linkNodes(World world, Node node){
		int column=node.getColumn();
		int row=node.getRow();
		if (world.isAccessible(column,row+1)){
			followPath(world,column,row,column,row+1,node,1);
		}
		if (world.isAccessible(column+1,row)){
			followPath(world,column,row,column+1,row,node,1);
		}
		if (world.isAccessible(column,row-1)){
			followPath(world,column,row,column,row-1,node,1);
		}
		if (world.isAccessible(column-1,row)){
			followPath(world,column,row,column-1,row,node,1);
		}
	}
	
	private static void followPath(World world, int column1, int row1, int column2, int row2, Node origin, int distance){
		Node node;
		if ((node=getNode(column2,row2))!=null){
			origin.addNode(node,distance);
			if (node.addNode(origin, distance)){
				linkNodes(world, node);
			}
		} else{
			if (world.isAccessible(column2,row2+1) && (column2!=column1 || row2+1!=row1)){
				followPath(world,column2,row2,column2,row2+1, origin, distance+1);
			} else if (world.isAccessible(column2+1,row2) && (column2+1!=column1 || row2!=row1)){
				followPath(world,column2,row2,column2+1,row2, origin, distance+1);
			} else if (world.isAccessible(column2,row2-1) && (column2!=column1 || row2-1!=row1)){
				followPath(world,column2,row2,column2,row2-1, origin, distance+1);
			} else {
				followPath(world,column2,row2,column2-1,row2, origin, distance+1);
			}
		}
	}
	
	private static Node getNode(int x, int y){
		for (int i=0;i<nodes.size();i++){
			if (nodes.get(i).getColumn()==x && nodes.get(i).getRow()==y){
				return nodes.get(i);
			}
		}
		return null;
	}
	
	public static void pheremoneDecay(){
		
	}
	
	public static double getPheremone(int column, int row){
		return 0;
	}
}
