package mstorm_sensor;


import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;


public class LightAndColorSensor extends EV3ColorSensor{

	public final static String sensor_port = "S1";
	private final static float light_value = 0.42f;
	private final static float dark_value = 0.02f;
	
	private SampleProvider color_provider;
	private SampleProvider light_provider;
	private float scale;
	
	public LightAndColorSensor(Port port) {
		super(port);

		color_provider = this.getRGBMode();
		light_provider = this.getAmbientMode();
		
		setScale();
	}

	/**
	 * 	Set the minimal scale
	 */
	private void setScale(){
		
		scale = 1 / (light_value - dark_value);
	}
	
	
	public float [] fetch(SensorType type){
		
		float [] values;
		
		if(type == SensorType.COLOR_SENSOR){
			values = fetchColorSample();
		}
		else{
			values = fetchLightSample();
	    }

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
