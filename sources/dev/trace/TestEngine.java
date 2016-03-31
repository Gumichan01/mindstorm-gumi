package dev.trace;

import java.io.IOException;

import main.mstorm_moving.Engine;

public class TestEngine extends Engine{

	public TestEngine() throws IOException {
		super();

		System.out.println("Default Speed : (" + left_motor.getSpeed() + "," 
							+ right_motor.getSpeed() + ")");
	}
	
	
	
	
}
