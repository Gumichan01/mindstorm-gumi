package mstorm_colorcheck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class ColorChecker {

	private static final int NB_COLORS = 3; 
	float [] avg_sample;						// Tableau de couleurs captés
	
	public ColorChecker() throws IOException {
		// TODO Load data from "agv.gumi"
		try{
		
			avg_sample = new float[NB_COLORS];
			BufferedReader reader = new BufferedReader(new FileReader("avg.gumi")); 
			
			for(int i = 0 ; i < NB_COLORS; i++)
			{
				String s  = reader.readLine();
				avg_sample[i] = Float.parseFloat(s);
			}
			
			reader.close();
			//System.out.println("Got from file : \n"+ Arrays.toString(avg_sample));
			//Thread.sleep(4000);
			
		}catch(IOException e){
			
			System.out.println("IOException occurred : "+ e.getMessage());
			throw e;
			
		}catch(NumberFormatException ne){
			
			System.out.println("NumberFormatxception occurred : "+ ne.getMessage());
			throw ne;
		}catch(Exception ex){}
		
		//System.out.println("Got from file : \n"+ avg_sample.toString());
	}

	/// Verifie si la couleur est bien celle attendu
	boolean isGoodcColor(float [] sample_to_check) throws Exception{
		
		throw new Exception("Not implemented yet.");
	}
	
	
}
