
package capteurcouleur;

/*
 *  Classe qui Contient les resultats des capteurs et les appels aux
 * classes qui ont besoin de ces donnees
 */

import javax.swing.event.EventListenerList;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;

public class Detection {

	private final EventListenerList colorlisteners = new EventListenerList();
	private EV3ColorSensor rgb1, rgb2;
	private String color1, color2;

	public Detection(Port[] ports) {
		initialiser_capteurs(ports);
		color1 = "Inconnu";
		color2 = "Inconnu";
		if(rgb1 != null)
			(new CouleurGestion(rgb1, this, 1)).start();
		if(rgb2 != null)
			(new CouleurGestion(rgb2, this, 2)).start();
	}

	private void initialiser_capteurs(Port[] ports) {
		for(int i = 0; i < ports.length; i++) {
			try {
				if(rgb1 == null) {
					rgb1 = new EV3ColorSensor(ports[i]);
				} else if(rgb2 == null) {
					rgb2 =  new EV3ColorSensor(ports[i]);
				}
			} catch(Exception e1) {
						System.out.println("Port "+ports[i].getName()+" non utilis�\n");
						Delay.msDelay(2000);
						stopDetection();
						System.exit(2);
			}
		}
	}


    public String getColor1() {
    	return color1;
    }

    public String getColor2() {
    	return color2;
    }

	public void stopDetection() {
		rgb1.close();
		rgb2.close();
	}

	// Methodes des event listeners

    protected void fireColorEvent1(String color) {
    	if(color1 != color) {
	    	for(CouleurListener listener : getCouleurListeners()) {
	    		listener.colorChanged1(color1);
	    	}
	    	color1 = color;
	    	if(color1 == color2)
	    		fireAligned();
    	}
    }

    protected void fireColorEvent2(String color) {
    	if(color2 != color) {
	    	for(CouleurListener listener : getCouleurListeners()) {
	    		listener.colorChanged2(color2);
	    	}
	    	color2 = color;
	    	if(color1 == color2)
	    		fireAligned();
    	}
    }

    protected void fireAligned() {
    	for(CouleurListener listener : getCouleurListeners()) {
    		listener.aligned(color1);
    	}
    }

	// Methodes pour ajouter, supprimer ou avoir la liste des listeners

	public void addColorListener(CouleurListener listener) {
		colorlisteners.add(CouleurListener.class, listener);
	}

	public void removeColorListener(CouleurListener listener) {
		colorlisteners.remove(CouleurListener.class, listener);
	}

	public CouleurListener[] getCouleurListeners() {
		return colorlisteners.getListeners(CouleurListener.class);
	}
}
