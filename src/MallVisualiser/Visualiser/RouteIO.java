package MallVisualiser.Visualiser;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import framework.FileIO;

public class RouteIO {

	Route route;
	FileIO fileIO;
	
	public RouteIO(Route route, FileIO fileIO){
		this.route = route;
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
			
			String length= "";
			String startX = "";
			String startY = "";
			
			//Read route length:
			while((i=is.read())!=-1)
			{
	            c=(char)i;
	            
	            if((c==';') && length.length()>0){
	            	break;
	            }else{
	            	length += c;
	            }
			}
			if(i==-1){
				throw new IOException("Invalid file ending after 'numTimes' specification.");
			}
			
			//Read startX (index):
			while((i=is.read())!=-1)
			{
				c=(char)i;
	            
	            if((c==',') && startX.length()>0){
	            	break;
	            }else if(c!='\n' && c!='\r'){ //Ignore enters - always
	            	if(!(startX.length()==0 && c==' ')){ //Ignore spacebars only if nextVal has a zero length
		            	startX += c;
	            	}
	            }
			}
			if(i==-1){
				throw new IOException("Invalid file ending after startX specification.");
			}
			
			//Read startY (index):
			while((i=is.read())!=-1)
			{
				c=(char)i;
	            
	            if((c==';') && startY.length()>0){
	            	break;
	            }else if(c!='\n' && c!='\r'){ //Ignore enters - always
	            	if(!(startY.length()==0 && c==' ')){ //Ignore spacebars only if nextVal has a zero length
		            	startY += c;
	            	}
	            }
			}
			if(i==-1){
				throw new IOException("Invalid file ending after startX specification.");
			}			

			//Read rest-of-file: the routeMatrix:
			int[] routeArray = new int
					[Integer.parseInt(length)];
			
			int j = 0;
			String nextVal = "";
			int routeNumber;
			try{
				while((i=is.read())!=-1)
				{
		            c=(char)i;
		            if(c==';'){
		            	if(nextVal.length()>0){
		            		routeNumber = Integer.parseInt(nextVal);
		            		if(routeNumber==0 || routeNumber==1 || routeNumber==2 || routeNumber==3){
				            	routeArray[j] = routeNumber;
				            	j++;
		            		}else{
		            			throw new IndexOutOfBoundsException("Invalid action! Must be `0', `1', `2' or `3'!");
		            		}
		            	}
		            	nextVal = "";
		            }else if(c!='\n' && c!='\r'){ //Ignore enters - always
		            	if(!(nextVal.length()==0 && c==' ')){ //Ignore spacebars only if nextVal has a zero length
			            	nextVal += c;
		            	}
		            }	            	
				}
			}catch(IndexOutOfBoundsException e){
				b = false;
			}
			route.renewRoute(routeArray, Integer.parseInt(startX), Integer.parseInt(startY));
			routeArray = null;
			
	    }catch(IOException e){
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
	        		b = false;
	        	}
	    }
		
		return b;		
	}
	
	public void resetRoute(){
		route.renewRoute(null, 0, 0);
	}
	
}
