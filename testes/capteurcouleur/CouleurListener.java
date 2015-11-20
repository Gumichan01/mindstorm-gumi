package capteurcouleur;

import java.util.EventListener;

/*
 * Interface que les classes qui ont besoin des capteurs doivent implementer
 * pour recuperer les resultats des capteurs
 */


public interface CouleurListener extends EventListener {
	public void colorChanged1(String newColor);
	public void colorChanged2(String newColor);
	public void aligned(String color);
}
