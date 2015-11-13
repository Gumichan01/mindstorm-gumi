package maincalibration;

import java.util.Arrays;

import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;
import mstorm_sensor.LightAndColorSensor;
import mstorm_sensor.SensorType;

public class Calibration {

	
	public static void main(String [] args){
		
		//int avg
		float [][] sample;
		
		
		LightAndColorSensor sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));
		
		int sz = sensor.getRGBMode().sampleSize();
		sample = new float[3][sz];
		
		for(int i = 0; i <3; i++){
			
			sample[i] = sensor.fetch(SensorType.COLOR_SENSOR);
			System.out.println("Color : " + Arrays.toString(sample[i]));
			Delay.msDelay(1000);
		}
		
		for(int j = 0; j < 3; j++)
			
		
		
		sensor.close();
	}
	
}
