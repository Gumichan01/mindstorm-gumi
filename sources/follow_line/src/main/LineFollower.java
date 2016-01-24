package main;

import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;
import mstorm_colorcheck.ColorChecker;
import mstorm_moving.Engine;
import mstorm_sensor.LightAndColorSensor;
import mstorm_sensor.SensorType;

public class LineFollower {
	
	public static void main(String[] args) {
		// TODO Réflechir sur la correction de trajectoire
		
		/*LightAndColorSensor sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));
		ColorChecker checker = new ColorChecker();
		
		// Dump measure
		sensor.fetch(SensorType.COLOR_SENSOR);
		
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR);
		
		
		// Tant que l'on detecte la bonne couleur, on avance
		while(checker.isGoodcColor(s))
		{
			engine.run();
			Delay.msDelay(delay);	// On attend un peu avant de refaire la mesure
			s = sensor.fetch(SensorType.COLOR_SENSOR);
		}*/
		
		try{
			Engine engine = new Engine();
			engine.move();
			
		}catch(Exception ie){
			
			ie.printStackTrace();
		}
		
	}

}
