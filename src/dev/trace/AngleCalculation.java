package dev.trace;

import java.util.ArrayList;

public class AngleCalculation {

	private static final float DISTANCE_2PI = 17.165f;	// cm
	private static final int PI_2 = 360;
	private static final float L = 10f;					// cm
	
	ArrayList<Float> theta_list;
	
	public AngleCalculation(){
		
		theta_list = new ArrayList<>();
	}
	
	
	public void generateData(ArrayList<RobotDatum> rl)
	{
		for(int i = 0; i < rl.size(); i++){
			
			float [] darray = new float[]{rl.get(i).getVL()*DISTANCE_2PI/PI_2,
											rl.get(i).getVR()*DISTANCE_2PI/PI_2};

			
			float [] varray = new float[]{darray[0],darray[1]};
			
			if(i < rl.size() - 1){
				
				// seconds
				float difft = (rl.get(i+1).getTime() - rl.get(i).getTime()) / 1000f;
				darray[0] *= difft;
				darray[1] *= difft;
			}

			float theta = (darray[0] - darray[1]) / L;
			
			theta_list.add(theta);
		}
	}
	
	
	public String toString(){
		
		String s = "";
		
		for(float f : theta_list){
			
			s += Float.toString(f) + " | ";
		}
		
		return s;
	}
	
}
