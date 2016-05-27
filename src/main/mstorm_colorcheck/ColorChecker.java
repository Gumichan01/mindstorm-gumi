package main.mstorm_colorcheck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ColorChecker {

	private static final int NB_COLORS = 3;
	private static final float MARGIN = 2.0f;
	private static float [] line_sample;	// Line color
	private float [] bg_sample;				// Background color
	private float [] black_sample;			// Black color
	private float [] blue_sample;			// Blue color
	private float [] ch_sample;				// Choice color
	private float [] st_sample;				// Stop color
	private float epsilon;					// ε for the error margin
	
	public ColorChecker() throws IOException {

		try{
			line_sample = new float[NB_COLORS];
			bg_sample = new float[NB_COLORS];
			black_sample = new float[NB_COLORS];
			blue_sample = new float[NB_COLORS];
			st_sample = new float[NB_COLORS];
			ch_sample = new float[NB_COLORS];

			BufferedReader reader = new BufferedReader(new FileReader("line.gumi")); 
			BufferedReader rd = new BufferedReader(new FileReader("bg.gumi"));
			BufferedReader rdht = new BufferedReader(new FileReader("ht.gumi"));
			BufferedReader rdht2 = new BufferedReader(new FileReader("ht2.gumi"));
			BufferedReader rdst = new BufferedReader(new FileReader("stop.gumi"));
			BufferedReader rdch = new BufferedReader(new FileReader("choice.gumi"));
			
			for(int i = 0 ; i < NB_COLORS; i++){

				line_sample[i] = Float.parseFloat(reader.readLine());
				bg_sample[i] = Float.parseFloat(rd.readLine());
				black_sample[i] = Float.parseFloat(rdht.readLine());
				blue_sample[i] = Float.parseFloat(rdht2.readLine());
				st_sample[i] = Float.parseFloat(rdst.readLine());
				ch_sample[i] = Float.parseFloat(rdch.readLine());
			}
			
			reader.close();
			rd.close();
			rdht.close();
			rdst.close();
			rdch.close();
			
			epsilon = Math.abs(euclide_distance(line_sample, bg_sample))/MARGIN;
			
		}catch(IOException e){
			throw e;	
		}catch(NumberFormatException ne){
			throw ne;
		}
	}

	/// Check if the color is the line color
	public boolean isLinecColor(float [] sample_to_check) throws Exception{
		
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_bg, dist_line, dist_st;
		dist_line = euclide_distance(sample_to_check, line_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		dist_st = euclide_distance(sample_to_check, st_sample);
		
		return (dist_line <= dist_bg) && (dist_line <= dist_st);
	}

	/// Check if the color is the background color
	public boolean isBgcColor(float [] sample_to_check) throws Exception{
		
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_bg, dist_line, dist_st;
		dist_line = euclide_distance(sample_to_check, line_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		dist_st = euclide_distance(sample_to_check, st_sample);
		
		return (dist_line >= dist_bg) && (dist_st >= dist_bg);
	}

	/// Check if the color is the choice color
	public boolean isChoiceColor(float [] sample_to_check) throws Exception{
		
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_bg, dist_line, dist_color, dist_st;
		dist_line = euclide_distance(sample_to_check, line_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		dist_st = euclide_distance(sample_to_check, st_sample);
		dist_color = euclide_distance(sample_to_check, ch_sample);
		
		return (dist_color <= dist_line) && (dist_color <= dist_bg) && (dist_color <= dist_st);
	}
	
	/// Check if the color is the stop color
	public boolean isStopcColor(float [] sample_to_check) throws Exception{
		
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_bg, dist_line, dist_st;
		dist_line = euclide_distance(sample_to_check, line_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		dist_st = euclide_distance(sample_to_check, st_sample);
		
		return (dist_st <= dist_line) && (dist_st <= dist_bg);
	}
	
	/// Check if this is the border
	public boolean isBorder(float [] sample_to_check) throws Exception{
		
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_bg, dist_line;
		dist_line = euclide_distance(sample_to_check, line_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		
		return Math.abs(dist_line - dist_bg) <= epsilon;
	}
	
	public boolean isBlackColor(float [] sample_to_check) throws Exception{
		
		if(sample_to_check.length != NB_COLORS)
			return false;

		float dist_black, dist_bg;;
		dist_black = euclide_distance(sample_to_check, black_sample);
		dist_bg = euclide_distance(sample_to_check, bg_sample);
		
		return (dist_black <= dist_bg);
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
