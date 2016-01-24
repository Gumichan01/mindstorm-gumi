package mstorm_main;

import java.util.Arrays;

import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;
import mstorm_sensor.LightAndColorSensor;
import mstorm_sensor.SensorType;

public class Main {

	public static void main(String[] args) {
		// TODO Calibration

		float [] sample = null;
		LightAndColorSensor sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));

		sample = sensor.fetch(SensorType.LIGHT_SENSOR);
		System.out.println("Light : " + Arrays.toString(sample));
		Delay.msDelay(1000);
		sample = sensor.fetch(SensorType.LIGHT_SENSOR);
		System.out.println("Light : " + Arrays.toString(sample));
		Delay.msDelay(2000);
		
		sample = sensor.fetch(SensorType.COLOR_SENSOR);
		System.out.println("Color : " + Arrays.toString(sample));
		Delay.msDelay(8000);
		sample = sensor.fetch(SensorType.COLOR_SENSOR);
		System.out.println("Color : " + Arrays.toString(sample));
		Delay.msDelay(8000);
		sample = sensor.fetch(SensorType.COLOR_SENSOR);
		System.out.println("Color : " + Arrays.toString(sample));
		Delay.msDelay(8000);
		
		sensor.close();
	}

}
