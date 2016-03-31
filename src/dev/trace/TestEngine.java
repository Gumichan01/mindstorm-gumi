package dev.trace;

import java.io.IOException;

import main.mstorm_moving.Engine;

public class TestEngine extends Engine{

	private static final long LINE_DELAY = 2000L;
	private static final long RTURN_DELAY = 1000L;
	private static final long LTURN_DELAY = 4000L;
	private static final int DEFAULT_SPEED = 360;
	
	public TestEngine() throws IOException {
		super();

		System.out.println("Default Speed : (" + left_motor.getSpeed() + "," 
							+ right_motor.getSpeed() + ")");
	}
	
	@Override
	public void move(){
		
		long begin_time;
		
		begin_time = System.currentTimeMillis();
		
		go();
		while(System.currentTimeMillis() - begin_time < LINE_DELAY);
		setSpeed(getSpeed()[0] / 2, getSpeed()[1]);
		while(System.currentTimeMillis() - begin_time < RTURN_DELAY);
		setSpeed(getSpeed()[0], getSpeed()[1] / 4);
		while(System.currentTimeMillis() - begin_time < LTURN_DELAY);
		setSpeed(DEFAULT_SPEED,DEFAULT_SPEED);
		while(System.currentTimeMillis() - begin_time < LINE_DELAY);
		halfTurn();
		stop();
		//end_time = System.currentTimeMillis();
	}
	
}
