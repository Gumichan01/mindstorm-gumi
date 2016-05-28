package dev.trace;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.mstorm_moving.Engine;

public class MindtraceThread {

	public static void main(String[] args) {

		Engine engine = null;

		try {
			engine = new TestEngine();
			RobotstatImplThread stat = new RobotstatImplThread(engine);

			engine.setSpeed(360, 360);
			stat.start();
			engine.move();
			RobotstatImplThread.stop = true;

			System.out.println("min : " + stat.getMinSpeed() + "\n");
			System.out.println("max : " + stat.getMaxSpeed() + "\n");
			System.out.println("avg : " + stat.getAvgSpeed() + "\n");

			try {
				Thread.sleep(4000);
			} catch (InterruptedException i) {

				Logger.getAnonymousLogger().log(Level.INFO,
						"Interrupted main - " + i.getMessage());
			}

		} catch (IOException ie) {

			Logger.getAnonymousLogger().log(Level.INFO,
					"Input exception - " + ie.getMessage());
			ie.printStackTrace();
		} catch (Exception e) {

			Logger.getAnonymousLogger().log(Level.INFO,
					"Unknown exception - " + e.getMessage());
		} finally {

			engine.close();
		}

	}
}
