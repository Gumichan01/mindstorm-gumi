package mstorm_sensor_calibration;


import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;


public class LightAndColorSensor extends EV3ColorSensor{

	public final static String sensor_port = "S1";
	
	private SampleProvider color_provider;
	private SampleProvider light_provider;
	
	public LightAndColorSensor(Port port) {
		super(port);

		color_provider = this.getRGBMode();
		light_provider = this.getAmbientMode();
		
	}
	
	
	public float [] fetch(SensorType type){
		
		float [] values;
		
		if(type == SensorType.COLOR_SENSOR){
			values = fetchColorSample();
		}
		else{
			values = fetchLightSample();
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
