package dev.trace;


import main.mstorm_moving.Engine;

public class Mindtrace {
	
	private static final long RUNTIME_DELAY = 4000L; 
	
	public static void main(String[] args) {
		
		try{
			Engine engine = new TestEngine();
			long begin_time = 0L, stop_time = 0;
			
			engine.setSpeed(0,360);
			engine.move();
			begin_time = System.currentTimeMillis();

			while(System.currentTimeMillis() - begin_time < RUNTIME_DELAY);
			
			engine.stop();
			stop_time = System.currentTimeMillis();
			System.out.println("top : " + (stop_time - begin_time));
			
			// Display the distance and save data in a file
			
			try{
				Thread.sleep(4000);
			}
			finally{
				engine.close();
			}
			
			
		}catch(Exception ie){
			
			ie.printStackTrace();
		}
		
	}

}
