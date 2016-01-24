package main;

import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;
import mstorm_colorcheck.ColorChecker;
import mstorm_moving.Engine;
import mstorm_sensor.LightAndColorSensor;
import mstorm_sensor.SensorType;

public class LineFollower {

	private final static long DPS = 60;			// Detection par seconde
	private final static long SECOND = 1000;	// 1 seconde
	
	
	public static void main(String[] args) throws Exception {
		// TODO Réflechir sur la correction de trajectoire
		
		final long delay = SECOND/DPS;	// Durée entre deux detections
		
		Engine engine = new Engine();
		LightAndColorSensor sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));
		ColorChecker checker = new ColorChecker();
		
		// Dump measure
		sensor.fetch(SensorType.COLOR_SENSOR);
		
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR);
		
		// Bout de code basique. Si la couleur est bonne on avance, 
		// sinon on ne bouge pas
		/*if(!checker.isGoodcColor(s))
			System.out.println("Bad color");
		else{
			System.out.println("Good color");
			engine.run();
		}*/
		
		// Tant que l'on detecte la bonne couleur, on avance
		while(checker.isGoodcColor(s))
		{
			engine.run();
			Delay.msDelay(delay);	// On attend un peu avant de refaire la mesure
			s = sensor.fetch(SensorType.COLOR_SENSOR);
		}
		
		engine.stop();
		sensor.close();
	}

}
