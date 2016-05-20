package dev.trace;

import java.io.IOException;
import java.util.Observer;

import main.mstorm_moving.Engine;

public class TestEngine extends Engine{

	private static final long LINE_DELAY = 2000L;
	private static final long RTURN_DELAY = 1500L;
	private static final long LTURN_DELAY = 3000L;
	private static final int DEFAULT_SPEED = 360;
	
	public TestEngine() throws IOException {
		super();

	}
	
	public TestEngine(Observer o) throws IOException {
		super(o);

	}
	
	@Override
	public void move(){
		
		long ref_time;
		
		ref_time = System.currentTimeMillis();
		
		go();
		while(System.currentTimeMillis() - ref_time < LINE_DELAY);
		ref_time = System.currentTimeMillis();
		
		setSpeed(DEFAULT_SPEED, DEFAULT_SPEED - (DEFAULT_SPEED / 3));
		while(System.currentTimeMillis() - ref_time < RTURN_DELAY);
		ref_time = System.currentTimeMillis();
		
		setSpeed(DEFAULT_SPEED - (DEFAULT_SPEED / 2), DEFAULT_SPEED);
		while(System.currentTimeMillis() - ref_time < LTURN_DELAY);
		ref_time = System.currentTimeMillis();
		
		setSpeed(DEFAULT_SPEED,DEFAULT_SPEED);
		while(System.currentTimeMillis() - ref_time < LINE_DELAY);
		
		halfTurn();
		stop();
	}
	
}
