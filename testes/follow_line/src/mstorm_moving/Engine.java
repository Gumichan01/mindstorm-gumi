package mstorm_moving;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class Engine {

	private final static int angle_run = 1800;
	private RegulatedMotor left_motor, right_motor;
	private boolean is_running;
	
	public Engine(){
		
		left_motor = new EV3LargeRegulatedMotor(MotorPort.A);
		right_motor = new EV3LargeRegulatedMotor(MotorPort.D);
		is_running = false;
	}

	// Fait tourner le moteur
	public void run()
	{
		if(!is_running){
			// Avec cette signature, cette fonction est non-bloquante
			left_motor.rotate(angle_run, true);
			right_motor.rotate(angle_run, true);
		}
	}
	
	// Marque l'arrêt du moteur
	public void stop()
	{
		if(is_running){
			// On arrête de bouger
			left_motor.stop(true);
			right_motor.stop(true);			
		}
	}
}
