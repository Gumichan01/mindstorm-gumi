package main;

import lejos.hardware.ev3.LocalEV3;
import lejos.internal.ev3.EV3DeviceManager;
import lejos.utility.Delay;
import mstorm_colorcheck.ColorChecker;
import mstorm_sensor.LightAndColorSensor;
import mstorm_sensor.SensorType;

public class LineFollower {

	public static void main(String[] args) throws Exception {
		// TODO Vérifier si la couleur capté est correct
		// TODO Créer la classe chargé du déplacement
		// TODO se déplacer si (couleur OK && etat = arret)
		// TODO Corriger trajectoire si (couleur KO && etat = avance)
		
		LightAndColorSensor sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));
		ColorChecker checker = new ColorChecker();
		
		sensor.fetch(SensorType.COLOR_SENSOR);
		
		float [] s = sensor.fetch(SensorType.COLOR_SENSOR);
		
		if(checker.isGoodcColor(s))
			System.out.println("Good color");
		else
			System.out.println("Bad color");
		
		Delay.msDelay(4000);
		
		sensor.close();
	}

}
