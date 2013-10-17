package MallVisualiser.Visualiser;

import MallVisualiser.Editor.World;
import framework.Camera;
import framework.Graphics;
import framework.Pixmap;

public class Route {

	private final World world;

	private final Pixmap pix0;
	private final Pixmap pix1;
	private final Pixmap pix2;
	private final Pixmap pix3;

	private int[] route;
	private int startX;
	private int startY;
	private final float pathThickness = 5;

	public Route(Pixmap pix0, Pixmap pix1, Pixmap pix2, Pixmap pix3, World world){
		this.pix0 = pix0;
		this.pix1 = pix1;
		this.pix2 = pix2;
		this.pix3 = pix3;
		this.world = world;
		startX = 0;
		startY = 0;
	}

	public void paint(Graphics g, Camera cam){
		if(route==null)
			return;

		float x = world.indexToCoordX(startX)-pathThickness/2; //minus pathThickness/2 to cause a minor overlap (gives good look&feel in corners)
		float y = world.indexToCoordY(startY)-pathThickness/2; //we will draw extra pathThickness length
		for (int element : route) {
			switch(element){
			case 0: //to East
				//if(i%2==0)
				g.drawPixmap(pix0, (x), (y), (world.width+pathThickness), pathThickness, cam);
				x += world.width;
				break;
			case 1: //to North
				//if(i%2==0)
				g.drawPixmap(pix1, (x), (y-world.height), pathThickness, (world.height+pathThickness), cam);
				y -= world.height;
				break;
			case 2: //to West
				//if(i%2==0)
				g.drawPixmap(pix2, (x-world.width), (y), (world.width+pathThickness), pathThickness, cam);
				x -= world.width;
				break;
			default: //to South
				//if(i%2==0)
				g.drawPixmap(pix3, (x), (y), pathThickness, (world.height+pathThickness), cam);
				y += world.height;
			}
		}

	}

	public void renewRoute(int[] route, int startX, int startY){
		//Sanity-check:
		if(route==null){
			this.route = null;
			return;
		}
		for(int i = 0; i<route.length; i++){
			if(!(route[i] == 0 || route[i] == 1 || route[i] == 2 || route[i] == 3)){
				System.out.println("route was not valid: " + route[i]);
				return;
			}
		}

		this.route = route;
		this.startX = startX;
		this.startY = startY;
	}

}
