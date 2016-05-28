package dev.trace;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import lejos.utility.Delay;
import main.mstorm_moving.Engine;
import main.trace.RobotStatImpl;

public class Mindtrace {

	public static void main(String[] args) {

		Engine engine = null;

		try {
			RobotStatImpl stat = new RobotStatImpl();
			engine = new TestEngine(stat);

			engine.setSpeed(360, 360);
			engine.move();

			System.out.println("min : " + stat.getMinSpeed() + "\n");
			System.out.println("max : " + stat.getMaxSpeed() + "\n");
			System.out.println("avg : " + stat.getAvgSpeed() + "\n");
			System.out.println("distance : " + stat.getDistance() + "\n");
			Delay.msDelay(8000);

		} catch (IOException ie) {

			Logger.getAnonymousLogger().log(Level.INFO,
					"Input exception - " + ie.getMessage());
			ie.printStackTrace();
		} catch (Exception e) {

			Logger.getAnonymousLogger().log(Level.INFO,
					"Unknown exception - " + e.getMessage());
		} finally {

			if (engine != null)
				engine.close();
		}

	}
}
