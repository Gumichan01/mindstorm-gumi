package mstorm_colorcheck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ColorChecker {

	private static final int NB_COLORS = 3; 
	float [] line_sample;						// Couleur de la ligne
	float [] bg_sample;							// Couleur du fond
	
	public ColorChecker() throws IOException {

		try{
			line_sample = new float[NB_COLORS];
			bg_sample = new float[NB_COLORS];
			BufferedReader reader = new BufferedReader(new FileReader("line.gumi")); 
			BufferedReader rd = new BufferedReader(new FileReader("bg.gumi"));
			
			for(int i = 0 ; i < NB_COLORS; i++)
			{
				String s = reader.readLine();
				line_sample[i] = Float.parseFloat(s);
			}
			
			reader.close();
						
			for(int i = 0 ; i < NB_COLORS; i++)
			{
				String ss = rd.readLine();	
				bg_sample[i] = Float.parseFloat(ss);
			}
			
			rd.close();
			
		}catch(IOException e){
			throw e;	
		}catch(NumberFormatException ne){
			throw ne;
		}
	}

	/// Verifie si la couleur est bien celle attendue
	public boolean isGoodcColor(float [] sample_to_check) throws Exception{
		
		/// TODO Les canaux sont les coordonnées de la couleur 
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_bg, dist_line;
		
		dist_line = euclide_distance(sample_to_check, line_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		
		return dist_line <= dist_bg;
			
		
		/*float sigma_red, sigma_green, sigma_blue;
		float red_variance, green_variance, blue_variance;
		
		float max_avg, min_avg, range;
		max_avg = line_sample[0];
		min_avg = line_sample[0];
		
		for(float v : line_sample){
			
			if(v < min_avg)
				min_avg = v;
			
			if(v > max_avg)
				max_avg = v;
		}

		// Zone entre max et min
		range = max_avg - min_avg;

		// Variances
		red_variance = (sample_to_check[0] - line_sample[0]) * (sample_to_check[0] - line_sample[0]);
		green_variance = (sample_to_check[1] - line_sample[1]) * (sample_to_check[1] - line_sample[1]);
		blue_variance = (sample_to_check[2] - line_sample[2]) * (sample_to_check[2] - line_sample[2]);

		// Ecart-types
		sigma_red = (float) Math.sqrt(red_variance);
		sigma_green = (float) Math.sqrt(green_variance);
		sigma_blue = (float) Math.sqrt(blue_variance);
		
		return(sigma_red <= range && sigma_green <= range && sigma_blue <= range);*/
	}
	
	public float euclide_distance(float [] color1,float [] color2)
	{
		// TODO euclide
		return 0.0f;
	}
}
