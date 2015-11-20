package capteurcouleur;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class CouleurGestion extends Thread {

	private float[][] colorSample;
	private String color;
	private SampleProvider sp;
	private Detection detection;
	private final int FREQUENCE = 2, NB_MESURES = 10;
	private CouleurInterpreter eval;
	private int numero;


	public CouleurGestion(EV3ColorSensor rgb, Detection d, int num) {
		sp = rgb.getRGBMode();
		detection = d;
		numero = num;
		colorSample = new float[NB_MESURES][3];
		eval = new CouleurInterpreter();
		color = "Inconnu";
	}

	public void run() {
		while(true) {
			for(int i = 0; i < NB_MESURES; i++)
				sp.fetchSample(colorSample[i], 0);
			color = eval.getColor(colorSample);
			System.out.println(color);
			Delay.msDelay(1000/FREQUENCE);
		}
	}


}
