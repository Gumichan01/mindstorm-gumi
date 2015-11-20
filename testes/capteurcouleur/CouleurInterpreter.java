package capteurcouleur;

public class CouleurInterpreter {

	// couleurs = {valMinRed, valMaxRed, valMinGreen, ...}
	// d'apres les infos trouvees sur le net
	private final double [] NOIR = {0.02, 0.025, 0.02, 0.025, 0.009, 0.013};
	private final double [] BLANC = {0.3, 0.35, 0.3, 0.32, 0.15, 0.17};
	private int[] tab;


	public CouleurInterpreter() {
		this.tab = new int[7];//la taille est suffisante
	}

	public String getColor(float[][] rgb) {
		String couleur;

		for(int i = 0; i < 7; i++)
			tab[i] = 0;

		for(int i = 0, t = rgb.length; i < t; i++) {
			couleur = couleur_captes(rgb[i]);

			switch(couleur) {
				case "Noir":
					if((tab[0]++) > 5)
						return "Noir";
					break;
				case "Blanc":
					if((tab[1]++) > 5)
						return "Blanc";
					break;
				case "Rouge":
					if((tab[2]++) > 5)
						return "Rouge";
					break;
				case "Jaune":
					if((tab[3]++) > 5)
						return "Jaune";
					break;
			case "Bleu":
				if((tab[4]++) > 5)
					return "Bleu";
				break;
			case "Vert":
				if((tab[5]++) > 5)
					return "Vert";
				break;
			default:
				if((tab[6]++) > 5)
					return "Inconnu";
				break;
		}
	}
	/* si la couleur n'�tait pas pr�sente � plus de 50%
	on renvoie inconnu */
	couleur=" Couleur Inconnu";
	return couleur;
}

/**
 * res_capteur -> resultat_capteur
 * fonction qui compare  les couleur du capteur et
 * retourne la couleur corrrespondate
 *
 **/

private String couleur_captes(float [] res_capteur) {
	/*drb -> delta red blue */
	float drb,drg;
	String inconu="Couleur Inconnue";

	drb = Math.abs(res_capteur[0]-res_capteur[2]);
	drg = Math.abs(res_capteur[0]-res_capteur[1]);

	if(test_couleur(res_capteur, NOIR))
		return "Noir";
	else if(test_couleur(res_capteur, BLANC))
		return "Blanc";
	else if((drg-drb<0.05) && res_capteur[0]>res_capteur[1]+res_capteur[2])
		return "Rouge";
	else if((drg<drb)&&(res_capteur[0]>res_capteur[1]+res_capteur[2]))
		return "Jaune";
	else if(res_capteur[2]>res_capteur[0]&&res_capteur[2]>=res_capteur[1])
		return "Bleu";
	else if(res_capteur[1]>(res_capteur[0]+res_capteur[2]))
		return "Vert";
	else
		return inconu;
}

private boolean test_couleur(float[] resultat_capteur, double[] game_couleur) {
	for(int i = 0; i < game_couleur.length; i++) {
		if(game_couleur[i]<resultat_capteur[i+i]||resultat_capteur[i]<game_couleur[i+i+1])
			return false;
	}
	return true;
}

}
