package mstorm_sensor;

import java.util.Arrays;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class LightAndColorSensor extends EV3ColorSensor{

	public final static String sensor_port = "S1";
	private final static float light_value = 0.42f;
	private final static float dark_value = 0.02f;
	private final static int delay =2000;
	
	private SampleProvider color_provider;
	private SampleProvider light_provider;
	private float scale;
	
	public LightAndColorSensor(Port port) {
		super(port);

		color_provider = this.getRGBMode();
		light_provider = this.getAmbientMode();
		
		calibrate();
	}

	/**
	 * 
	 * 	Sensor calibration
	 * 
	 * */
	private void calibrate(){
		
		//float [] light = new float[light_provider.sampleSize()];
		
		System.out.println("Calibration");
	
		//light_provider.fetchSample(light, 0);
		Delay.msDelay(delay);
		//light_provider.fetchSample(light, 0);
	
		scale = 1 / (light_value - dark_value);
		//System.out.println("Ambient light: "+Arrays.toString(light));
		System.out.println("Scale: "+scale);
	}
	
	
	public float [] fetch(SensorType type){
		
		float [] values;
		
		if(type == SensorType.COLOR_SENSOR){
			values = fetchColorSample();
		}
		else{
			values = fetchLightSample();
	    }
		
		System.out.println("Values: "+Arrays.toString(values));
		
		Delay.msDelay(2000);
		
		for(int i = 0; i < values.length;i++){
			
			values[i] *= scale; 
		}
		
		return values;
	}
	
	
	
	private float [] fetchColorSample(){
		
		float [] sample = new float[color_provider.sampleSize()];
		
		color_provider.fetchSample(sample, 0);
		
		return sample;
	}
	
	private float [] fetchLightSample(){
		
		float [] sample = new float[light_provider.sampleSize()];
		
		light_provider.fetchSample(sample, 0);
		
		return sample;
	}
	
}
