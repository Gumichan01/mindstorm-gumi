package calibration.half_turn;

import java.io.IOException;



public class HTCalibre {

	
	public static void main(String [] args)throws Exception{

		System.out.println("Started");
		System.out.println("Black color");
		CalibrateColorPath.Calibrate(true);
		System.out.println("Blue color");
		CalibrateColorPath.Calibrate(false);

	}
}
