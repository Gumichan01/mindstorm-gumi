package dev.trace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.mstorm_moving.Engine;


public class RobotStatImpl implements RobotStat, Observer {

	public static volatile boolean stop = false;
	private static float ANGLE_2PI = 360.0f;
	private static float DISTANCE_2PI = 0.17165f;	// meter (calculated)
	
	private ArrayList<Integer[]> speeds;
	private int count = 0;
	private long current_time;
	private float min_speed = 0.0f;
	private float max_speed = 0.0f;
	private float avg_speed = 0.0f;
	private float distance = 0.0f;

	public RobotStatImpl(){
		
		speeds = new ArrayList<>();
		current_time = System.currentTimeMillis();
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

	@Override
	public void update(Observable o, Object arg){
		
		Engine en = (Engine) o;
		addSpeeds(en);
		
		if(arg != null){
			
			saveData();
		}
			
	}
	
	protected void saveData(){
		
		PrintWriter w = null;
		
		try{

			w = new PrintWriter(new File("statO.gumi"));
			
			for(Integer [] t : speeds){
				
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
	
	private void addSpeeds(Engine engine){

		long t;
		float vl,vr;
		float velocity;
		Integer [] sp = engine.getSpeedObj();

		t = System.currentTimeMillis() - current_time;
		//speeds.add(sp);

		vl = (sp[0] * DISTANCE_2PI) / ANGLE_2PI;
		vr = (sp[1] * DISTANCE_2PI) / ANGLE_2PI;

		velocity = (vl + vr) / 2.0f;

		if(velocity > max_speed)
			max_speed = velocity;

		if(velocity < min_speed)
			min_speed = velocity;

		count++;
		avg_speed += velocity/count;
		distance += velocity * t;
		
	}
}
