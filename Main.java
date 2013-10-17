package ci.assignment3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Tom Peeters - 4176510
 * Rutger van den Berg - 4060156
 * Ymte Jan Broekhuizen - 4246586
 * Arne Hutter - 4108566
 */
public class Main {
	
	static int[][] maze;
	static int startColumn;
	static int startRow;
	static int endColumn;
	static int endRow;
	static int maxIterations=100;
	static int ants=10;
	static double pheremoneDropped=1;
	static double evaporationRate=0.9;
	static double a=1;
	static double b=1;
	static ArrayList<Node> nodes=new ArrayList<>();
	
	
	public static void main(String[] args) {
		loadMap("C:\\Users\\Tom\\Dropbox\\Intelligence Practicum\\Practicum 3\\GradingMaze EASY v2");
		loadCoordinates("C:\\Users\\Tom\\Dropbox\\Intelligence Practicum\\Practicum 3\\GradingMaze EASY v2 coordinates.txt");
		createNodes();
			System.out.println(nodes.size());
		Node startNode=getNode(startColumn,startRow);
		if (startNode==null){
			startNode=new Node(startColumn,startRow);
			nodes.add(startNode);
		}
		Node endNode=getNode(endColumn,endRow);
		if (endNode==null){
			endNode=new Node(endColumn,endRow);
			nodes.add(endNode);
		}
		linkNodes(startNode);
		if (!endNode.getNodes().isEmpty()){
			ArrayList<Node> bestPath;
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
	
	private static void loadMap(String filename){
		try {
			Scanner input = new Scanner(new FileReader(new File(filename)));
			maze=new int[input.nextInt()][input.nextInt()];
			for (int y=0;y<getColumns();y++){
				for (int x=0;x<getRows();x++){
					maze[x][y]=input.nextInt();
				}
			}
			input.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private static void loadCoordinates(String filename){
		try {
			Scanner input = new Scanner(new FileReader(new File(filename)));
			String line=input.nextLine();
			line=line.substring(0,line.length()-1);
			startColumn=Integer.parseInt(line.split(", ")[0]);
			startRow=Integer.parseInt(line.split(", ")[1]);
			line=input.nextLine();
			line=line.substring(0,line.length()-1);
			endColumn=Integer.parseInt(line.split(", ")[0]);
			endRow=Integer.parseInt(line.split(", ")[1]);
			input.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
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
	
	private static void createNodes(){
		for(int column=0;column<getColumns();column++){
			for(int row=0;row<getRows();row++){
				if (isBlockValid(column,row)==1){
					int connections=
							isBlockValid(column,row+1)+
							isBlockValid(column+1,row)+
							isBlockValid(column,row-1)+
							isBlockValid(column-1,row);
					if (connections!=0 && connections!=2){
						nodes.add(new Node(column,row));
						System.out.println("("+column+","+row+")");
					}
				}
			}
		}
	}
	
	private static void linkNodes(Node node){
		int column=node.getColumn();
		int row=node.getRow();
		if (isBlockValid(column,row+1)==1){
			followPath(column,row,column,row+1,node,1);
		}
		if (isBlockValid(column+1,row)==1){
			followPath(column,row,column+1,row,node,1);
		}
		if (isBlockValid(column,row-1)==1){
			followPath(column,row,column,row-1,node,1);
		}
		if (isBlockValid(column-1,row)==1){
			followPath(column,row,column-1,row,node,1);
		}
	}
	
	private static void followPath(int column1, int row1, int column2, int row2, Node origin, int distance){
		Node node;
		if ((node=getNode(column2,row2))!=null){
			origin.addNode(node,distance);
			if (node.addNode(origin, distance)){
				linkNodes(node);
			}
		} else{
			if (isBlockValid(column2,row2+1)==1 && (column2!=column1 || row2+1!=row1)){
				followPath(column2,row2,column2,row2+1, origin, distance+1);
			} else if (isBlockValid(column2+1,row2)==1 && (column2+1!=column1 || row2!=row1)){
				followPath(column2,row2,column2+1,row2, origin, distance+1);
			} else if (isBlockValid(column2,row2-1)==1 && (column2!=column1 || row2-1!=row1)){
				followPath(column2,row2,column2,row2-1, origin, distance+1);
			} else {
				followPath(column2,row2,column2-1,row2, origin, distance+1);
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
	
	public static int isBlockValid(int column, int row){
		try {
			return maze[column][row];
		} catch (ArrayIndexOutOfBoundsException | NullPointerException ex) {
			return 0;
		}
	}
	
	public static double getPheremone(int column, int row){
		return 0;
	}
	
	public static int getRows(){
		return maze.length;
	}
	
	public static int getColumns(){
		return maze[0].length;
	}
	
	public static int getStartColumn(){
		return startColumn;
	}
	
	public static int getStartRow(){
		return startRow;
	}
	
	public static int getEndColumn(){
		return endColumn;
	}
	
	public static int getEndRow(){
		return endRow;
	}
}
