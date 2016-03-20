package main;

import mstorm_moving.Engine;
import mstorm_moving.TestEngine;

public class Mindtrace {
	
	public static void main(String[] args) {
		
		try{
			Engine engine = new TestEngine();
			
			engine.setSpeed(0,360);
			//engine.move();
			engine.go();
			try{
			
				//Thread.sleep(1000);
				//System.out.println(Math.PI);
				/*System.out.println("Vitesse courante : "+ engine.getSpeed()[0] 
									+ " " + engine.getSpeed()[1]);*/
				Thread.sleep(1000);
				engine.stop();
				Thread.sleep(4000);
				engine.close();
			
			}catch(Exception e){
				///  blabla
			}
			
			
		}catch(Exception ie){
			
			ie.printStackTrace();
		}
		
	}

}
