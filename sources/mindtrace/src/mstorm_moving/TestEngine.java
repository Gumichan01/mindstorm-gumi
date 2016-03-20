package mstorm_moving;

import java.io.IOException;

public class TestEngine extends Engine{

	public TestEngine() throws IOException {
		super();

		System.out.println("Default Speed : (" + left_motor.getSpeed() + "," 
							+ right_motor.getSpeed() + ")");
	}
	
}
