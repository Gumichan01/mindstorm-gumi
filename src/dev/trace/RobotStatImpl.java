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
	public static boolean first_stat = true;
	
	private ArrayList<RobotDatum> speed_data;
	private int count = 0;
	private long current_time;
	private float min_speed = 0.0f;
	private float max_speed = 0.0f;
	private float avg_speed = 0.0f;
	private float distance = 0.0f;

	public RobotStatImpl(){
		
		speed_data = new ArrayList<>();
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
		newData(en);
		
		if(arg != null){
			
			saveData();
		}
			
	}
	
	protected void saveData(){
		
		PrintWriter w = null;
		
		try{

			w = new PrintWriter(new File("statO.gumi"));
			
			for(RobotDatum rd : speed_data){
				
				w.print(rd.toString());
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
	
	private void newData(Engine engine){

		long t;
		float vl,vr;
		float velocity;
		Integer [] sp = engine.getSpeedObj();

		if(first_stat){
		
			t = 0;
			current_time = System.currentTimeMillis();
			first_stat = false;
		}
		else
			t = System.currentTimeMillis() - current_time;
		
		speed_data.add(new RobotDatum(sp[0], sp[1], t));

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
