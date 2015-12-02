package mstorm_moving;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class Engine {

	private final static int angle_run = 1800;
	private RegulatedMotor left_motor, right_motor;
	
	public Engine(){
		
		left_motor = new EV3LargeRegulatedMotor(MotorPort.A);
		right_motor = new EV3LargeRegulatedMotor(MotorPort.D);
	}
	
	public void run()
	{
		left_motor.rotate(angle_run, true);
		right_motor.rotate(angle_run, true);
	}
	
}
