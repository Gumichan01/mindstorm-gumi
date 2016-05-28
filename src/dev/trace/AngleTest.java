package dev.trace;

import java.util.ArrayList;

import main.trace.AngleCalculation;
import main.trace.RobotDatum;

public class AngleTest {

	public static void main(String[] args) {

		AngleCalculation c = new AngleCalculation();
		ArrayList<RobotDatum> rl = new ArrayList<>();

		rl.add(new RobotDatum(180, 360, 0));
		rl.add(new RobotDatum(360, 360, 103));
		rl.add(new RobotDatum(360, 180, 267));
		rl.add(new RobotDatum(360, 360, 273));
		rl.add(new RobotDatum(180, 360, 3079));
		rl.add(new RobotDatum(180, 360, 3181));
		rl.add(new RobotDatum(360, 360, 3202));
		rl.add(new RobotDatum(180, 360, 3225));
		rl.add(new RobotDatum(360, 360, 3346));
		rl.add(new RobotDatum(0, 0, 8254));

		c.generateData(rl);
		System.out.println(c.toString());
	}
}
