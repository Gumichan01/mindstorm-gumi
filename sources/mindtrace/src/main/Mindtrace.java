package main;


import mstorm_moving.Engine;
import mstorm_moving.TestEngine;

public class Mindtrace {
	
	public static void main(String[] args) {
		
		try{
			Engine engine = new TestEngine();
			long begin_time = 0L, stop_time = 0;
			
			engine.setSpeed(0,360);
			engine.go();
			begin_time = System.currentTimeMillis();

			while(System.currentTimeMillis() - begin_time < 1000L);
			
			engine.stop();
			stop_time = System.currentTimeMillis();
			System.out.println("top : " + (stop_time - begin_time));
			
			try{
				Thread.sleep(4000);
			
			}finally{
				///  blabla
				engine.close();
			}
			
			
		}catch(Exception ie){
			
			ie.printStackTrace();
		}
		
	}

}
