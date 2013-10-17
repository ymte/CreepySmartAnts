package MallVisualiser.Editor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import framework.FileIO;

public class WorldIO {

	World world;
	FileIO fileIO;

	public WorldIO(World world, FileIO fileIO){
		this.world = world;
		this.fileIO = fileIO;
	}
	
	public void toggleSimulate() {
		world.toggleSimulate();
	}

	public boolean load(File file){
		boolean b = true;
		int i;
		char c;
		InputStream is = null;
		try{
			// new input stream created
			is = fileIO.readFile(file);

			String width = "";
			String height = "";
			String nextVal = "";
			int x = 0;
			int y = -1;

			//Read width (X):
			while((i=is.read())!=-1)
			{
				c=(char)i;

				if((c==' ' || c =='\n' || c=='\r') && width.length()>0){
					break;
				}else{
					width += c;
				}
			}
			if(i==-1){
				throw new IOException("Invalid file ending after width specification.");
			}

			//Read height (Y):
			while((i=is.read())!=-1)
			{
				c=(char)i;

				if((c==' ' || c =='\n' || c=='\r') && height.length()>0){
					break;
				}else{
					height += c;
				}
			}
			if(i==-1){
				throw new IOException("Invalid file ending after height specification.");
			}

			int[][] worldArray = new int
					[Integer.parseInt(width)]
							[Integer.parseInt(height)];

			//Read rest-of-file: the worldMatrix:
			try{
				while((i=is.read())!=-1)
				{
					c=(char)i;
					if(c=='\n'){
						if(nextVal.length()>0)
							worldArray[x][y] = Integer.parseInt(nextVal);
						x=0;
						y++;
						nextVal = "";
					}else if(c==' '){
						worldArray[x][y] = Integer.parseInt(nextVal);
						x++;
						nextVal = "";
					}else if(c=='\r'){

					}else{
						nextVal += c;
					}
				}
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
				b = false;
			}

			// read coords
			File file2 = new File(file.getAbsolutePath() + " coordinates.txt");
			FileReader fr = new FileReader(file2);
			String start1 = "", start2 = "";
			String end1 = "", end2 = "";
			char c2 = 0;
			while((c2 = (char) fr.read()) != ',') start1 += c2;
			fr.skip(1); // space
			while((c2 = (char) fr.read()) != ';') start2 += c2;
			fr.skip(2); // newline
			while((c2 = (char) fr.read()) != ',') end1 += c2;
			fr.skip(1); // space
			while((c2 = (char) fr.read()) != ';') end2 += c2;
			
			// parse coordinates
			world.startTile_x = Integer.parseInt(start1);
			world.startTile_y = Integer.parseInt(start2);
			world.endTile_x = Integer.parseInt(end1);
			world.endTile_y = Integer.parseInt(end2);
			
			// close
			fr.close();

			world.renewWorld(worldArray);
			worldArray = null;
		}catch(IOException e){
			e.printStackTrace();
			b = false;
		}catch(NumberFormatException e){
			e.printStackTrace();
			b = false;
		}finally{

			// releases system resources associated with this stream
			if(is!=null)
				try{
					is.close();
					is = null;
				}catch(IOException e){
					e.printStackTrace();
				}
		}
		return b;
	}

	public void renewWorld(int width, int height){
		world.renewWorld(width, height);
	}

	public void resizeWorld(int width, int height){
		world.resizeWorld(width, height);
	}
	public boolean save(File file){
		try {
			OutputStream os = fileIO.writeFile(file);

			String str = world.getNumElementsX() + " " + world.getNumElementsY() + System.lineSeparator();
			os.write(str.getBytes());
			str = null;

			os.write(world.toString().getBytes());

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
