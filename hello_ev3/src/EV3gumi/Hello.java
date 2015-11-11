package EV3gumi;



import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;



public class Hello{

	private static RegulatedMotor motor,motor2;
	private static int angle = 3600; 
	
	public static void main(String [] args){

		// Recupère l'adresse du cerveau
		EV3 ev3 = (EV3) BrickFinder.getLocal();

		motor = new EV3LargeRegulatedMotor(MotorPort.A);
		motor2 = new EV3LargeRegulatedMotor(MotorPort.D);
		//pour le deuxième moteur brancher sur le port D du cerveau
		TextLCD lcd = ev3.getTextLCD();

		// On met le texte sur l'écran LCD
		lcd.drawString("Hello World!", 4, 4);
		//Sound.beep();
		Delay.msDelay(1000);

		// Rotation
		/* Une manière simple de déplacer le robot 
		(faire les deux moteur en parralele) */
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				motor.rotate(angle);
			}
		}).start();
		
		motor2.rotate(angle);
		

		
		Delay.msDelay(2000);

	}

}
