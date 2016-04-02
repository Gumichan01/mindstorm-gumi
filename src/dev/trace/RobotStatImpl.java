package dev.trace;

import java.util.Observable;
import java.util.Observer;


public class RobotStatImpl implements RobotStat, Observer {

	private float min_speed = 0.0f;
	private float max_speed = 0.0f;
	private float avg_speed = 0.0f;
	private float distance = 0.0f;

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
			
	}

}
