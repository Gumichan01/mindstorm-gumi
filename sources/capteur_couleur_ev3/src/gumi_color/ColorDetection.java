package gumi_color;

import java.io.IOException;

import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;
import mstorm_colorcheck.ColorChecker;
import mstorm_moving.Engine;
import mstorm_sensor.LightAndColorSensor;
import mstorm_sensor.SensorType;

public class ColorDetection {

	public static final float WHITE_MIN_VALUE = (float) 0.75;
	
	public static void main(String[] args) throws IOException {

		/*Port port = LocalEV3.get().getPort("S1");
		TextLCD lcd = ((EV3) BrickFinder.getLocal()).getTextLCD();

		// Créer le capteur
		EV3ColorSensor sensor = new EV3ColorSensor(port);
		SampleProvider color_sample_provider = sensor.getRGBMode();
		SampleProvider light_sample_provider = sensor.getAmbientMode();

		int n = 32;
		int i = 0;
		
		// Detection de couleur
		float [] sample = new float[color_sample_provider.sampleSize()];

		while(i < n)
		{
			Delay.msDelay(500);
			color_sample_provider.fetchSample(sample, 0);
			System.out.println(Arrays.toString(sample));
			
			DisplayIfBlack(sample);
			DisplayIfWhite(sample);
			
			i++;
		}
		
		Delay.msDelay(2000);
		
		i = 0;	// Reset the counter
		
		// Detection de lumiere
		sample = new float[light_sample_provider.sampleSize()];

		while(i < n)
		{
			light_sample_provider.fetchSample(sample, 0);
			Delay.msDelay(500);

			System.out.println("Light : " + Arrays.toString(sample));
			DisplayIfNoLight(sample);	
			i++;
		}		
		
		Delay.msDelay(4000);
		sensor.close();*/
		
		Engine engine = new Engine();
		LightAndColorSensor sensor = new LightAndColorSensor(LocalEV3.get().
				getPort(LightAndColorSensor.sensor_port));
		
		// Poubelle
		sensor.fetch(SensorType.COLOR_SENSOR);
		
		int i = 0;
		float [] sample;
		float red, green, blue;
		
		engine.run();
		
		while(i < 100)
		{
			sample = sensor.fetch(SensorType.COLOR_SENSOR); 
			red = sample[0];
			green = sample[1];
			blue = sample[2];
			i++;
			Delay.msDelay(16);
		}
		sensor.close();
	}

	
	// If the value of all channels is 0, then the color is black
	static public void DisplayIfBlack(float [] color_sample){
		
		for(float f : color_sample)
		{
			// Not black, get out of the function!
			if(f != 0.0)
				return;
		}
		
		String s = "Black color ";
		System.out.println(s);
	}
	
	
	// IF the value of all channels is above a certain value
	// we consider this color as white
	static public void DisplayIfWhite(float [] color_sample){
		
		for(float f : color_sample)
		{
			// Not black, get out of the function!
			if(f < WHITE_MIN_VALUE)
				return;
		}
		
		String s = "White color ";
		System.out.println(s);
	}
	
	// Display a sentence if there is no light 
	static public void DisplayIfNoLight(float [] light_sample){
		
		for(float f : light_sample)
		{
			if(f != 0.0)
				return;
		}
		
		String s = "No light";
		System.out.println(s);
	}
	
}
