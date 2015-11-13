package maincalibration;

import java.util.Arrays;

import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;
import mstorm_sensor.LightAndColorSensor;
import mstorm_sensor.SensorType;

public class Calibration {

	public final static int NUMCHANS = 3;
	
	public static void main(String [] args){
		
		float r_avg, g_avg, b_avg;
		float [][] sample;
		
		LightAndColorSensor sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));
		
		int sz = sensor.getRGBMode().sampleSize();
		sample = new float[NUMCHANS][sz];
		
		// 3 color Detections
		for(int i = 0; i <NUMCHANS; i++){
			
			sample[i] = sensor.fetch(SensorType.COLOR_SENSOR);
			//System.out.println("Color : " + Arrays.toString(sample[i]));
			Delay.msDelay(1000);
		}
		
		// Average
		r_avg = 0;
		g_avg = 0;
		b_avg = 0;
		
		for(int j = 0; j < NUMCHANS; j++){
			r_avg += sample[j][0];
			g_avg += sample[j][1];
			b_avg += sample[j][2];
		}
			
		r_avg /= NUMCHANS;
		g_avg /= NUMCHANS;
		b_avg /= NUMCHANS;
		
		System.out.println("AVG : " + r_avg + "\n" + g_avg + "\n" + b_avg);
		Delay.msDelay(8000);
		
		sensor.close();
	}
	
}
