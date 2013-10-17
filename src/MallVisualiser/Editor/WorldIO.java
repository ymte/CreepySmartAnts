package MallVisualiser.Editor;

import java.io.File;
import java.io.FileInputStream;
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
			FileInputStream fis = new FileInputStream(new File(""));

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
