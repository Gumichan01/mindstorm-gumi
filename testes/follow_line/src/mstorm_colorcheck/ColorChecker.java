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
			
			System.out.println("IOException occurred : "+ e.getMessage());
			throw e;
			
		}catch(NumberFormatException ne){
			
			System.out.println("NumberFormatxception occurred : "+ ne.getMessage());
			throw ne;
		}catch(Exception ex){}
	}

	/// Verifie si la couleur est bien celle attendue
	boolean isGoodcColor(float [] sample_to_check) throws Exception{
		
		throw new Exception("Not implemented yet.");
	}
	
	
}
