package main.calibration.bg;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.IllegalFormatException;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import main.mstorm_sensor.LightAndColorSensor;
import main.mstorm_sensor.SensorType;

public class BgCalibre {

	public final static int NUMCHANS = 10;
	public final static String bg_gumi = "bg.gumi";

	public static void main(String[] args) throws IOException {

		float r_avg, g_avg, b_avg;
		float[][] sample;

		LightAndColorSensor sensor = new LightAndColorSensor(LocalEV3.get()
				.getPort(LightAndColorSensor.sensor_port));

		int sz = sensor.getRGBMode().sampleSize();
		sample = new float[NUMCHANS][sz];

		// Ignore that
		sensor.fetch(SensorType.COLOR_SENSOR);

		// Color detections
		for (int i = 0; i < NUMCHANS; i++) {

			System.out.println("Waiting for input");
			Button.DOWN.waitForPressAndRelease();
			sample[i] = sensor.fetch(SensorType.COLOR_SENSOR);
		}

		// Average
		r_avg = 0;
		g_avg = 0;
		b_avg = 0;

		for (int j = 0; j < NUMCHANS; j++) {
			r_avg += sample[j][0];
			g_avg += sample[j][1];
			b_avg += sample[j][2];
		}

		r_avg /= NUMCHANS;
		g_avg /= NUMCHANS;
		b_avg /= NUMCHANS;

		try {

			PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(
					bg_gumi, false)));
			w.printf("%.9g\n%.9g\n%.9g\n", r_avg, g_avg, b_avg);
			w.flush();
			w.close();
			sensor.close();

		} catch (FileNotFoundException fe) {

			fe.printStackTrace();
			sensor.close();
			throw fe;

		} catch (IllegalFormatException ife) {

			ife.printStackTrace();
			sensor.close();
			throw ife;
		} catch (IOException e) {

			e.printStackTrace();
			sensor.close();
			throw e;
		}

	}
}
