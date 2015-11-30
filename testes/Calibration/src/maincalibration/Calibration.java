package maincalibration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.IllegalFormatException;

import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;
import mstorm_sensor.LightAndColorSensor;
import mstorm_sensor.SensorType;

public class Calibration {

	public final static int NUMCHANS = 10;
	
	public static void main(String [] args) throws FileNotFoundException{

		float r_avg, g_avg, b_avg;
		float [][] sample;

		LightAndColorSensor sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));

		int sz = sensor.getRGBMode().sampleSize();
		sample = new float[NUMCHANS][sz];

		// 3 color Detections
		for(int i = 0; i <NUMCHANS; i++){
	
			sample[i] = sensor.fetch(SensorType.COLOR_SENSOR);
			Delay.msDelay(500);
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

		try{
			PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter("agv.test", false)));
			w.write("FUCK YOU LEJOS 30/11/2015 12:40 PIPOPIPO\n");
			w.close();
			
		}catch(FileNotFoundException fe){
			
			fe.printStackTrace();
			throw fe;
			
		}catch(IllegalFormatException ife){
		
			ife.printStackTrace();
			throw ife;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
			sensor.close();
		}
	}
}
