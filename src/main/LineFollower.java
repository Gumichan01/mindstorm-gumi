package main;

import dev.trace.RobotStatImpl;
import main.mstorm_moving.Engine;

public class LineFollower {
	
	public static void main(String[] args) throws Exception {
		
		Engine engine = null;
		
		try{
			engine = new Engine(new RobotStatImpl());
			engine.move();
			
		}catch(Exception ie){
			
			ie.printStackTrace();
			throw ie;
		
		} finally{
			
			if(engine != null)
				engine.close();
		}
		
	}

}
