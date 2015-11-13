package maincalibration;

import java.io.File;
import java.io.FileNotFoundException;
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
			//System.out.println("Color : " + Arrays.toString(sample[i]));
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
		
		System.out.println("AVG : " + r_avg + "\n" + g_avg + "\n" + b_avg);
		Delay.msDelay(5000);
		
		System.out.println("File saving");
		Delay.msDelay(1000);
		
		try{
			PrintWriter w = new PrintWriter(new File("avg.gumi"));
			w.printf("%.9g\n%.9g\n%.9g\n",r_avg,g_avg,b_avg);
			w.flush();
			w.close();
			
			System.out.println("Done");
			Delay.msDelay(1000);
			
		}catch(FileNotFoundException fe){
			
			//System.out.print("Cannot open the file:\n "+ fe.getMessage());
			fe.printStackTrace();
			throw fe;
			//Delay.msDelay(5000);
			
		}catch(IllegalFormatException ife){
		
			//System.out.print("Cannot write data:\n "+ ife.getMessage());
			ife.printStackTrace();
			throw ife;
			//Delay.msDelay(5000);
		}
		finally{
			
			sensor.close();
		}
		
	}
	
}
