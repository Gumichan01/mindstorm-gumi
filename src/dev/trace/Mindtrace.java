package dev.trace;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.mstorm_moving.Engine;

public class Mindtrace {
	
	
	public static void main(String[] args) {
		
		Engine engine = null;

		try{
			engine = new TestEngine();

			engine.setSpeed(360,360);
			engine.move();
			
			// TODO Display the distance and save data in a file
			
		}catch(IOException ie){
			
			Logger.getAnonymousLogger().log(Level.INFO, "Input exception - " + 
											ie.getMessage());
			ie.printStackTrace();
		}
		catch(Exception e){
			
			Logger.getAnonymousLogger().log(Level.INFO, "Unknown exception - " + 
											e.getMessage());
		}
		finally{
			
			engine.close();
		}
		
	}

}
