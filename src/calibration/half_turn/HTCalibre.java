package calibration.half_turn;

import java.io.IOException;



public class HTCalibre {

	
	public static void main(String [] args)throws Exception{

		CalibrateColorPath.Calibrate(true);
		CalibrateColorPath.Calibrate(false);

	}
}
