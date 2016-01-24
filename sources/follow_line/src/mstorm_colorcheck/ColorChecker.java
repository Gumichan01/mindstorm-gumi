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
		
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_bg, dist_line;
		
		dist_line = euclide_distance(sample_to_check, line_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		
		return dist_line <= dist_bg;
	}
	
	
	private float euclide_distance(float [] color1,float [] color2)
	{
		return (float) Math.sqrt(square_euclide_distance(color1, color2));
	}
	
	private float square_euclide_distance(float [] color1,float [] color2)
	{
		float dr = color2[0] - color1[0];
		float dg = color2[1] - color1[1];
		float db = color2[2] - color1[2];
				
		return  (dr*dr) + (dg*dg) + (db*db);
	}
}
