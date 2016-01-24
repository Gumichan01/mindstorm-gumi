package mstorm_colorcheck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ColorChecker {

	private static final int NB_COLORS = 3; 
	float [] avg_sample;						// Tableau de couleurs captés
	
	public ColorChecker() throws IOException {

		try{
			avg_sample = new float[NB_COLORS];
			BufferedReader reader = new BufferedReader(new FileReader("avg.gumi")); 
			
			for(int i = 0 ; i < NB_COLORS; i++)
			{
				String s  = reader.readLine();
				avg_sample[i] = Float.parseFloat(s);
			}
			
			reader.close();
			
		}catch(IOException e){
			throw e;	
		}catch(NumberFormatException ne){
			throw ne;
		}
	}

	/// Verifie si la couleur est bien celle attendue
	public boolean isGoodcColor(float [] sample_to_check) throws Exception{
		/// TODO Calculer l'écart-type et verifier sa valeur, si OK -> TRUE, sinon FALSE 
		if(sample_to_check.length != NB_COLORS)
			return false;

		float sigma_red, sigma_green, sigma_blue;
		float red_variance, green_variance, blue_variance;
		
		float max_avg, min_avg, range;
		max_avg = avg_sample[0];
		min_avg = avg_sample[0];
		
		for(float v : avg_sample){
			
			if(v < min_avg)
				min_avg = v;
			
			if(v > max_avg)
				max_avg = v;
		}

		// Zone entre max et min
		range = max_avg - min_avg;

		// Variances
		red_variance = (sample_to_check[0] - avg_sample[0]) * (sample_to_check[0] - avg_sample[0]);
		green_variance = (sample_to_check[1] - avg_sample[1]) * (sample_to_check[1] - avg_sample[1]);
		blue_variance = (sample_to_check[2] - avg_sample[2]) * (sample_to_check[2] - avg_sample[2]);

		// Ecart-types
		sigma_red = (float) Math.sqrt(red_variance);
		sigma_green = (float) Math.sqrt(green_variance);
		sigma_blue = (float) Math.sqrt(blue_variance);
		
		return(sigma_red <= range && sigma_green <= range && sigma_blue <= range);
	}
}
