package EV3gumi;


import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.Delay;



public class Hello{
	
	public static void main(String [] args){
		
		// Recupère l'adresse du cerveau
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		TextLCD lcd = ev3.getTextLCD();		// Objet représentant l'écran LCD
		
		// On met le texte sur l'écran LCD
		lcd.drawString("Hello World!", 4, 4);
		Sound.beep();
		// Attendre 2 secondes
		Delay.msDelay(2000);

	}
	
}