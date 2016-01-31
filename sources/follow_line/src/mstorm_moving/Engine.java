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
	private final static int angle_rotate = 225;
	private final static int speed = 225;	// Vitesse du robot
	
	
	private RegulatedMotor left_motor, right_motor;
	private ColorChecker checker;
	private LightAndColorSensor sensor;
	private int id_strat;
	//private boolean is_running;
	private boolean bg_right;			// Indique si le fond est à droite du robot
	
	public Engine() throws IOException{
		
		sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));
		checker = new ColorChecker();
		left_motor = new EV3LargeRegulatedMotor(MotorPort.D);
		right_motor = new EV3LargeRegulatedMotor(MotorPort.A);
		id_strat = 0;
		//is_running = false;
		bg_right = false;
		
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
			
			if(id_strat == 0){
				
				// On est perdu, donc on avance bêtement
				while(checker.isGoodcColor(s)){
					
					go();
					s = sensor.fetch(SensorType.COLOR_SENSOR);
				}
				
				/* 	On retrouve notre chemin en enregistrant
				 *  l'endroit où est situé le fond */
				leftCorrection();
				stop();
				Delay.msDelay(delay);
				bg_right = true;
				id_strat = 1;
			}
			
			s = sensor.fetch(SensorType.COLOR_SENSOR);
			
			while(checker.isBorder(s)){
				
				go();
				s = sensor.fetch(SensorType.COLOR_SENSOR);
			}
			
			if(checker.isGoodcColor(s)){
			
				fromLineToBorder();

			} else {

				fromBackgroundToBorder();
			}
		}

		stop();
		sensor.close();
	}
	
	
	private void go(){
		
		run();
		Delay.msDelay(SECOND/DPS);
	}
	
	// Revenir vers la gauche
	private void leftCorrection() throws Exception{
		
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR); 
		left_motor.stop();
		left_motor.setSpeed(speed/2);
		
		while(!checker.isBorder(s)){
			
			right_motor.rotate(angle_rotate,true);
			left_motor.rotate(angle_rotate,true);
			
			s = sensor.fetch(SensorType.COLOR_SENSOR);
		}
		
		stop();
		left_motor.setSpeed(speed);
	}
	
	// Revenir vers la droite
	private void rightCorrection() throws Exception{
		
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR); 
		right_motor.stop();
		right_motor.setSpeed(speed/2);
		
		while(!checker.isBorder(s)){
			
			right_motor.rotate(angle_rotate,true);
			left_motor.rotate(angle_rotate,true);
			
			s = sensor.fetch(SensorType.COLOR_SENSOR);
		}
		
		stop();
		right_motor.setSpeed(speed);
	}	
	
	
	// On est dans la ligne et on veut revenir au bord
	private void fromLineToBorder() throws Exception{
		
		if(bg_right){
			
			// Fond à droite, donc le bord est à droite
			rightCorrection();

		} else {

			// Sinon c'est que le bord est à gauche
			leftCorrection();
		}
	}
	
	
	// On est dans le fond et on veut revenir au bord
	private void fromBackgroundToBorder() throws Exception{
		
		if(bg_right){
			
			leftCorrection();

		} else {
			
			rightCorrection();
		}
	}
}
