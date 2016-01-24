package EV3gumi;



import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;


// Avancer, demi--tour, avancer, demi-tour (c'est un peu approximatif)
public class Hello{

	private static RegulatedMotor motor,motor2;
	private static int angle = 1800;
	private static int angleRotation = 465;
	
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
		
		motor2.rotate(angle+16);

		Delay.msDelay(100);
		
		// Demi-tour
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				motor.rotate(-angleRotation);
			}
		}).start();
		
		motor2.rotate(angleRotation);
		
		Delay.msDelay(100);
		
		// Avancer
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				motor.rotate(angle);
			}
		}).start();
		
		motor2.rotate(angle);

		Delay.msDelay(100);
		
		// Demi-tour (repositionnement)
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				motor.rotate(angleRotation);
			}
		}).start();
		
		motor2.rotate(-angleRotation);
		
		Delay.msDelay(2000);

	}

}
