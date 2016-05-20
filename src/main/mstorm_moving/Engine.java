package main.mstorm_moving;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import main.mstorm_colorcheck.ColorChecker;
import main.mstorm_sensor.LightAndColorSensor;
import main.mstorm_sensor.SensorType;

public class Engine extends Observable {
	
	public static boolean half_turning;
	
	protected final static long DPS = 60;			// Detection par seconde
	protected final static long SECOND = 1000;		// 1 seconde
	protected final static long TOTAL_DELAY = 30000;

	protected final static int angle_rotate = 225;
	protected final static int speed = 360;			// Vitesse du robot
	
	protected RegulatedMotor left_motor, right_motor;
	protected ColorChecker checker;
	protected LightAndColorSensor sensor;
	protected Observer observer;
	
	private int id_strat;
	private boolean bg_right;			// Indique si le fond est � droite du robot
	private boolean half_turn_done;
	private boolean running;
	private long start_run;
	
	
	public Engine() throws IOException{
		
		sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));
		checker = new ColorChecker();
		left_motor = new EV3LargeRegulatedMotor(MotorPort.D);
		right_motor = new EV3LargeRegulatedMotor(MotorPort.A);
		
		id_strat = 0;
		bg_right = false;
		half_turn_done = true;
		half_turning = false;
		running = false;
		observer = null;
	}

	public Engine(Observer obs) throws IOException{
		
		this();
		observer = obs;
	}
	

	protected void go()
	{
		left_motor.forward();
		right_motor.forward();
	}
	

	public void halfTurn() {
		
		half_turning = true;
		left_motor.backward();
		right_motor.forward();
		update();
		
		Delay.msDelay(1900);
		bg_right = false;
		half_turn_done = true;
		half_turning = false;
	}
	

	public void stop(){

		left_motor.stop(true);
		right_motor.stop(true);
		left_motor.setSpeed(0);
		right_motor.setSpeed(0);
		lastUpdate();
	}
	
	public void close(){
		
		sensor.close();
	}

	
	public void move() throws Exception{
		
		final long delay = SECOND/DPS;
		
		System.out.println("START");
		// Dump measure
		sensor.fetch(SensorType.COLOR_SENSOR);
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR);
		running = true;
		start_run = System.currentTimeMillis();

		while(running && (System.currentTimeMillis() - start_run) < 45000){
			
			if(id_strat == 0){
				System.out.println("Strat 0");
				//left_motor.setSpeed(225);
				//right_motor.setSpeed(225);
				go();
				//System.out.println(left_motor.getSpeed() + " " + right_motor.getSpeed());
				Delay.msDelay(1000);
				s = sensor.fetch(SensorType.COLOR_SENSOR);
				
				// On est perdu, donc on avance b�tement
				while(checker.isLinecColor(s)){
					
					go();
					s = sensor.fetch(SensorType.COLOR_SENSOR);
				}
				
				/* 	On retrouve notre chemin en enregistrant
				 *  l'endroit o� est situ� le fond */
				leftCorrection();
				//stop();
				Delay.msDelay(delay);
				bg_right = true;
				id_strat = 1;
			}

			
			System.out.println("Strat 1");
			s = sensor.fetch(SensorType.COLOR_SENSOR);
			
			while(checker.isBorder(s)){
				//System.out.println("Border");
				go();
				s = sensor.fetch(SensorType.COLOR_SENSOR);
			}
			
			if(checker.isLinecColor(s)){
				//System.out.println("Line -> Border");
				fromLineToBorder();

			} else if (checker.isBgcColor(s)) {
				//System.out.println("Bg -> Border");
				fromBackgroundToBorder();

			/*} else if (checker.isHalfTurncColor(s)) {
				
				//System.out.println("HT");
				/*halfTurn();
				id_strat = 0;
				sensor.fetch(SensorType.COLOR_SENSOR);
				s = sensor.fetch(SensorType.COLOR_SENSOR);
				continue;*/
				
			} else if (checker.isStopcColor(s) || checker.isHalfTurncColor(s)) {
				
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
	}
	
	
	// Revenir vers la gauche
	private void leftCorrection() throws Exception{
		
		int sp = speed/2;
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR); 
		
		left_motor.setSpeed(sp);
		update();
		
		//Stopwatch timer = new Stopwatch();
		
		System.out.println("TO left");
		
		while(!checker.isBorder(s)){
			
			/*if(timer.elapsed() > SECOND){
				sp--;
				left_motor.setSpeed(sp);
				update();
			}*/
			
			//right_motor.rotate(angle_rotate,true);
			//left_motor.rotate(angle_rotate,true);
			
			s = sensor.fetch(SensorType.COLOR_SENSOR);
		}
		
		left_motor.setSpeed(speed);
		update();
	}
	
	// Revenir vers la droite
	private void rightCorrection() throws Exception{
		
		int sp = speed/2;
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR); 
		
		System.out.println("TO right");
		
		right_motor.setSpeed(sp);
		update();
		
		//Stopwatch timer = new Stopwatch();
		
		while(!checker.isBorder(s)){
			
			/*if(timer.elapsed() > SECOND){
				sp--;
				left_motor.setSpeed(sp);
				update();
			}*/
			
			//right_motor.rotate(angle_rotate,true);
			//left_motor.rotate(angle_rotate,true);
			
			s = sensor.fetch(SensorType.COLOR_SENSOR);
		}
		
		right_motor.setSpeed(speed);
		update();
	}	
	
	
	// On est dans la ligne et on veut revenir au bord
	private void fromLineToBorder() throws Exception{
		
		if(bg_right){
			
			// Fond � droite, donc le bord est � droite
			rightCorrection();

		} else {

			// Sinon c'est que le bord est � gauche
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
	
	public synchronized void setSpeed(int sp_left, int sp_right){
		
		setSpeed(new int[]{sp_left,sp_right});
	}
	
	
	private void update(){
	
		if(observer != null)
			observer.update(this, null);
	}

	private void lastUpdate(){
		
		if(observer != null)
			observer.update(this, this);
	}
	
	protected void setSpeed(int [] sp){
		
		if(sp.length == 2){
			
			left_motor.setSpeed(sp[0]);
			right_motor.setSpeed(sp[1]);
			update();
		}
		
	}
	
	public synchronized int [] getSpeed(){
		
		return new int[]{left_motor.getSpeed(),right_motor.getSpeed()};
	}
	
	public synchronized Integer [] getSpeedObj(){
		
		return new Integer[]{left_motor.getSpeed(),right_motor.getSpeed()};
	}
}
