package main.mstorm_moving;

import java.io.IOException;

import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;
import main.mstorm_colorcheck.ColorChecker;
import main.mstorm_sensor.LightAndColorSensor;
import main.mstorm_sensor.SensorType;

public class Engine {
	
	protected final static long DPS = 60;			// Detection par seconde
	protected final static long SECOND = 1000;		// 1 seconde
	protected final static long TOTAL_DELAY = 30000;

	protected final static int angle_rotate = 225;
	protected final static int speed = 360;			// Vitesse du robot
	
	protected RegulatedMotor left_motor, right_motor;
	protected ColorChecker checker;
	protected LightAndColorSensor sensor;
	
	private int id_strat;
	private boolean bg_right;			// Indique si le fond est à droite du robot
	private boolean half_turn_done;
	private boolean running;
	
	public Engine() throws IOException{
		
		sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));
		checker = new ColorChecker();
		left_motor = new EV3LargeRegulatedMotor(MotorPort.D);
		right_motor = new EV3LargeRegulatedMotor(MotorPort.A);
		id_strat = 0;
		bg_right = false;
		half_turn_done = false;
		running = false;
		
		//left_motor.setSpeed(speed);
		//right_motor.setSpeed(speed);
	}

	// Faire tourner le moteur
	public void go()
	{
		left_motor.forward();
		right_motor.forward();
	}
	

	public void halfTurn() {
		left_motor.backward();
		right_motor.forward();
		Delay.msDelay(1900);
		bg_right = false;
		half_turn_done = true;
	}
	
	// Marque l'arrêt du moteur
	public void stop()
	{
		// On arrête de bouger
		left_motor.stop(true);
		right_motor.stop(true);	
	}
	
	public void close(){
		
		stop();
		sensor.close();
	}
	
	public void move() throws Exception{
		
		final long delay = SECOND/DPS;	// Durée entre deux detections
		
		// Dump measure
		sensor.fetch(SensorType.COLOR_SENSOR);
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR);
		running = true;

		while(running){
			
			if(id_strat == 0){
				
				go();
				Delay.msDelay(1000);
				s = sensor.fetch(SensorType.COLOR_SENSOR);
				
				// On est perdu, donc on avance bêtement
				while(checker.isLinecColor(s)){
					
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
			
			if(checker.isLinecColor(s)){
				fromLineToBorder();

			} else if (checker.isBgcColor(s)) {
				fromBackgroundToBorder();

			} else if (checker.isHalfTurncColor(s)) {
				
				halfTurn();
				id_strat = 0;
				sensor.fetch(SensorType.COLOR_SENSOR);
				s = sensor.fetch(SensorType.COLOR_SENSOR);
				continue;
				
			} else if (checker.isStopcColor(s)) {
				
				if(half_turn_done){
					
					stop();
					Sound.setVolume(8);
					Sound.beep(); 
					Delay.msDelay(250); 
					Sound.beep();
					Delay.msDelay(250); 
					Sound.beep();
					Delay.msDelay(250); 
					Sound.beep();
					Sound.setVolume(0);
					running = false;
				}
			}
		}

		stop();
		sensor.close();
	}
	
	
	// Revenir vers la gauche
	private void leftCorrection() throws Exception{
		
		int sp = speed/2;
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR); 
		left_motor.setSpeed(sp);
		
		Stopwatch timer = new Stopwatch();
		
		while(!checker.isBorder(s)){
			
			if(timer.elapsed() > SECOND){
				sp--;
				left_motor.setSpeed(sp);
			}
			
			//right_motor.rotate(angle_rotate,true);
			//left_motor.rotate(angle_rotate,true);
			
			s = sensor.fetch(SensorType.COLOR_SENSOR);
		}
		
		left_motor.setSpeed(speed);
	}
	
	// Revenir vers la droite
	private void rightCorrection() throws Exception{
		
		int sp = speed/2;
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR); 
		right_motor.setSpeed(sp);
		
		Stopwatch timer = new Stopwatch();
		
		while(!checker.isBorder(s)){
			
			if(timer.elapsed() > SECOND){
				sp--;
				left_motor.setSpeed(sp);
			}
			
			//right_motor.rotate(angle_rotate,true);
			//left_motor.rotate(angle_rotate,true);
			
			s = sensor.fetch(SensorType.COLOR_SENSOR);
		}
		
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
	
	public void setSpeed(int sp_left, int sp_right){
		
		setSpeed(new int[]{sp_left,sp_right});
	}
	
	
	protected void setSpeed(int [] sp){
		
		if(sp.length == 2){
			
			left_motor.setSpeed(sp[0]);
			right_motor.setSpeed(sp[1]);
		}
		
	}
	
	public int [] getSpeed(){
		
		return new int[]{left_motor.getSpeed(),left_motor.getSpeed()};
	}
}
