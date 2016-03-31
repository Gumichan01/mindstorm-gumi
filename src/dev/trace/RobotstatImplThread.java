package dev.trace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.mstorm_moving.Engine;

public class RobotstatImplThread extends Thread implements RobotStat{

	public static volatile boolean stop = false;
	private static float ANGLE_2PI = 360.0f;
	private static float DISTANCE_2PI = 0.1728f;	// meter (calculated)
	private int DELAY = 33;
	
	ArrayList<Integer[]> speeds;
	private int count = 0;
	private float min_speed = 0.0f;
	private float max_speed = 0.0f;
	private float avg_speed = 0.0f;
	private float distance = 0.0f;
	Engine engine;
	
	public RobotstatImplThread(Engine en){
		
		engine = en;
		speeds = new ArrayList<>();
	}
	
	@Override
	public float getMaxSpeed() {

		return max_speed;
	}

	@Override
	public float getMinSpeed() {

		return min_speed;
	}

	@Override
	public float getAvgSpeed() {

		return avg_speed;
	}

	@Override
	public float getDistance() {

		return distance;
	}
	
	private void addSpeeds(){
		
		float velocity;
		float vg,vd;
		Integer [] sp = engine.getSpeedObj();
		speeds.add(sp);	// Atomic
		
		vg = (sp[0] * DISTANCE_2PI) / ANGLE_2PI;
		vd = (sp[1] * DISTANCE_2PI) / ANGLE_2PI;
		
		velocity = (vg + vd) / 2.0f;
		
		if(velocity > max_speed)
			max_speed = velocity;
		
		if(velocity < min_speed)
			min_speed = velocity;
		
		// TODO distance -> velocity * t (t is a moment)
		count++;
		avg_speed += velocity/count;
		
	}

	@Override
	public void run(){
		
		PrintWriter w = null;
		
		while(!stop){
			
			try{
				Thread.sleep(DELAY);
			} catch (InterruptedException in){
				
				Logger.getAnonymousLogger().log(Level.INFO, "Interrupted - " + 
						in.getMessage());
			}
			
			addSpeeds();
		}
		

		try{

			w = new PrintWriter(new File("statT.gumi"));
			
			for(Integer [] t : speeds)
			{
				w.printf("%d %d\n", t[0],t[1]);
				w.flush();
			}

		}catch(FileNotFoundException e){
			
			Logger.getAnonymousLogger().log(Level.INFO, "File not found - " + 
					e.getMessage());
		
		}finally{

			if(w != null)
				w.close();
		}
	}
}
