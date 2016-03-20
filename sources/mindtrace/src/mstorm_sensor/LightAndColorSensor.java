package mstorm_sensor;


import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import mstorm_colorcheck.ColorChecker;

public class LightAndColorSensor extends EV3ColorSensor {

	public final static String sensor_port = "S1";

	private SampleProvider color_provider;
	private SampleProvider light_provider;

	public LightAndColorSensor(Port port) {
		super(port);

		color_provider = this.getRGBMode();
		light_provider = this.getAmbientMode();
	}


	// Get a value (RGB or Light) from the sensor
	public float[] fetch(SensorType type) {

		float[] values;

		if (type == SensorType.COLOR_SENSOR) {
			values = fetchColorSample();
		} else {
			values = fetchLightSample();
		}

		return values;
	}

	// Get a RGB value from the sensor
	private float[] fetchColorSample() {

		float [][] stock_array = new float[11][];
		float[] sample = new float[color_provider.sampleSize()];

		for (int i = 0; i < 11; i++) {
			color_provider.fetchSample(sample, 0);
			stock_array[i] = sample;

		}
		
		float [][] sorted_array = ColorChecker.insertionSort(stock_array);
		
		return sorted_array[sorted_array.length/2];
	}


	// Get an ambient light value from the sensor
	private float[] fetchLightSample() {

		float[] sample = new float[light_provider.sampleSize()];
		light_provider.fetchSample(sample, 0);

		return sample;
	}

}
