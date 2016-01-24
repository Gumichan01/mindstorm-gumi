package mstorm_moving;

import java.io.IOException;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import mstorm_colorcheck.ColorChecker;
import mstorm_sensor.LightAndColorSensor;
import mstorm_sensor.SensorType;

public class Engine {
	
	private final static long DPS = 60;			// Detection par seconde
	private final static long SECOND = 1000;	// 1 seconde
	private final static long TOTAL_DELAY = 15000;

	private final static int angle_run = 360;
	private final static int angle_rotate = 8;
	private final static int speed = 225;	// Vitesse du robot
	
	
	private RegulatedMotor left_motor, right_motor;
	private ColorChecker checker;
	private LightAndColorSensor sensor;
	private boolean is_running;
	
	public Engine() throws IOException{
		
		sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));
		checker = new ColorChecker();
		left_motor = new EV3LargeRegulatedMotor(MotorPort.D);
		right_motor = new EV3LargeRegulatedMotor(MotorPort.A);
		is_running = false;
		
		left_motor.setSpeed(speed);
		right_motor.setSpeed(speed);
	}

	// Faire tourner le moteur
	public void run()
	{
		// Avec cette signature, cette fonction est non-bloquante
		left_motor.rotate(angle_run, true);
		right_motor.rotate(angle_run, true);
	}
	
	// Marque l'arrêt du moteur
	public void stop()
	{
		// On arrête de bouger
		left_motor.stop(true);
		right_motor.stop(true);	
	}
	
	public void move() throws Exception{
		
		final long delay = SECOND/DPS;	// Durée entre deux detections
		long ref_time = System.currentTimeMillis();
		
		// Dump measure
		sensor.fetch(SensorType.COLOR_SENSOR);
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR);

		while((System.currentTimeMillis() - ref_time) < TOTAL_DELAY){
			
			if(checker.isGoodcColor(s))
			{
				run();
				Delay.msDelay(delay);
			}
			else{
				Delay.msDelay(delay);
				leftCorrection();
				stop();
				Delay.msDelay(delay);
			}
			
			s = sensor.fetch(SensorType.COLOR_SENSOR);
		}

		stop();
		sensor.close();
	}
	
	
	private void leftCorrection() throws Exception{
		
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR); 
		left_motor.stop();
		
		while(!checker.isGoodcColor(s)){
			
			right_motor.rotate(angle_rotate,true);
			//left_motor.rotate(-(angle_rotate/2),true);
			
			s = sensor.fetch(SensorType.COLOR_SENSOR);
		}
		right_motor.stop();
	}
	
	private void rightCorrection(){
		
		throw new UnsupportedOperationException("TODO rightcCorrection");
	}	
	
	
}
