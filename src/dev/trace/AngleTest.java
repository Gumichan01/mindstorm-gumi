package dev.trace;

import java.util.ArrayList;

public class AngleTest {

	public static void main(String [] args){
	
		AngleCalculation c = new AngleCalculation();
		ArrayList<RobotDatum> rl = new ArrayList<>();
		
		rl.add(new RobotDatum(0, 0, 0));
		rl.add(new RobotDatum(360, 360, 30));
		rl.add(new RobotDatum(360, 240, 2035));
		rl.add(new RobotDatum(180, 360, 3537));
		rl.add(new RobotDatum(360, 360, 6535));
		rl.add(new RobotDatum(-360, 360, 8539));
		rl.add(new RobotDatum(0, 0, 10449));
	
		c.generateData(rl);
		System.out.println(c.toString());
	}
}
