package mstorm_sensor;

import java.util.ArrayList;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import mstorm_colorcheck.ColorChecker;

public class LightAndColorSensor extends EV3ColorSensor {

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
	 * Set the minimal scale
	 */
	private void setScale() {

		scale = 1 / (light_value - dark_value);
		System.out.println("Scale: " + scale);
	}

	// Get a value (RGB or Light) from the sensor
	public float[] fetch(SensorType type) {

		float[] values;

		if (type == SensorType.COLOR_SENSOR) {
			values = fetchColorSample();
		} else {
			values = fetchLightSample();
		}

		// System.out.println("Values: "+Arrays.toString(values));
		for (int i = 0; i < values.length; i++) {
			values[i] *= scale;
		}

		return values;
	}

	// Get a RGB value from the sensor
	/// TODO Test the new implementation
	private float[] fetchColorSample() {

		ArrayList<float[]> list_sample = new ArrayList<float[]>();

		float[] sample = new float[color_provider.sampleSize()];

		for (int i = 0; i < 11; i++) {
			color_provider.fetchSample(sample, 0);
			list_sample.add(sample);
			Delay.msDelay(16);
		}

		float [][] stock_array = new float[list_sample.size()][];
		stock_array = (float[][]) list_sample.toArray();
		
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
