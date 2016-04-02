package dev.trace;

import java.io.IOException;

import main.mstorm_moving.Engine;

public class TestEngine extends Engine{

	private static final long LINE_DELAY = 2000L;
	private static final long RTURN_DELAY = 1500L;
	private static final long LTURN_DELAY = 3000L;
	private static final int DEFAULT_SPEED = 360;
	private static final int HT_SPEED = 255;

	public TestEngine() throws IOException {
		super();

		System.out.println("Default Speed : (" + left_motor.getSpeed() + ","
							+ right_motor.getSpeed() + ")");
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

		setSpeed(HT_SPEED,HT_SPEED);
		halfTurn();
		stop();

	}
}
