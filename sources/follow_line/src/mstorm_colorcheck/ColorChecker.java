package mstorm_colorcheck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ColorChecker {

	private static final int NB_COLORS = 3;
	private static final float MARGIN = 2.0f;
	private static float [] line_sample;	// Couleur de la ligne
	private float [] bg_sample;				// Couleur du fond
	private float [] ht_sample;				// Couleur du fond
	private float epsilon;					// Marge d'erreur pour le calcul du bord
	
	public ColorChecker() throws IOException {

		try{
			line_sample = new float[NB_COLORS];
			bg_sample = new float[NB_COLORS];
			ht_sample = new float[NB_COLORS];
			BufferedReader reader = new BufferedReader(new FileReader("line.gumi")); 
			BufferedReader rd = new BufferedReader(new FileReader("bg.gumi"));
			BufferedReader rdht = new BufferedReader(new FileReader("ht.gumi"));
			
			for(int i = 0 ; i < NB_COLORS; i++){
				
				String s = reader.readLine();
				String ss = rd.readLine();
				String sss = rdht.readLine();
				line_sample[i] = Float.parseFloat(s);
				bg_sample[i] = Float.parseFloat(ss);
				ht_sample[i] = Float.parseFloat(sss);
			}
			
			reader.close();
			rd.close();
			rdht.close();
			
			epsilon = Math.abs(euclide_distance(line_sample, bg_sample))/MARGIN;
			
		}catch(IOException e){
			throw e;	
		}catch(NumberFormatException ne){
			throw ne;
		}
	}

	/// Verifie si la couleur est bien celle de la ligne
	public boolean isLinecColor(float [] sample_to_check) throws Exception{
		
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_bg, dist_line, dist_ht;
		dist_line = euclide_distance(sample_to_check, line_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		dist_ht = euclide_distance(sample_to_check, ht_sample);
		
		return (dist_line <= dist_bg) && (dist_line <= dist_ht);
	}

	public boolean isBgcColor(float [] sample_to_check) throws Exception{
		
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_bg, dist_line, dist_ht;
		dist_line = euclide_distance(sample_to_check, line_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		dist_ht = euclide_distance(sample_to_check, ht_sample);
		
		return (dist_line >= dist_bg) &&  (dist_ht >= dist_bg);
	}

	
	public boolean isHalfTurncColor(float [] sample_to_check) throws Exception{
		
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_bg, dist_line, dist_ht;
		dist_line = euclide_distance(sample_to_check, line_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		dist_ht = euclide_distance(sample_to_check, ht_sample);
		
		return (dist_ht <= dist_line) && (dist_ht <= dist_bg);
	}
	
	public boolean isBorder(float [] sample_to_check) throws Exception{
		
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_bg, dist_line;
		dist_line = euclide_distance(sample_to_check, line_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		
		return Math.abs(dist_line - dist_bg) <= epsilon;
	}
	
	
	private static float euclide_distance(float [] color1,float [] color2){
		
		return (float) Math.sqrt(square_euclide_distance(color1, color2));
	}
	
	
	private static float square_euclide_distance(float [] color1,float [] color2){
		
		float dr = color2[0] - color1[0];
		float dg = color2[1] - color1[1];
		float db = color2[2] - color1[2];
				
		return  (dr*dr) + (dg*dg) + (db*db);
	}
	
	/// TODO Test this sort
	public static float[][] insertionSort(float[][] array) {
		
		int cpt;
		float[] element;

		for (int i = 1; i < array.length; i++) {

			element = array[i];
			cpt = i - 1;

			while (cpt >= 0 
					&& (euclide_distance(array[cpt], line_sample) 
						> euclide_distance(element, line_sample))) {

				array[cpt + 1] = array[cpt];
				cpt--;
			}

			array[cpt + 1] = element;
		}
		
		return array;
	}
}
