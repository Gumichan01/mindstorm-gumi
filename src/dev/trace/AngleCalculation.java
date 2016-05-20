package dev.trace;

import java.util.ArrayList;

public class AngleCalculation {

	private static final float DISTANCE_2PI = 0.17165f;	// meter
	private static final int PI_2 = 360;
	private static final float L = 0.1438f;				// meter
	
	private ArrayList<Float> radius_list;
	private ArrayList<Float> theta_list;
	
	public AngleCalculation(){
		
		radius_list = new ArrayList<>();
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
				/* Is that correct ? */
				darray[0] = varray[0] * difft;
				darray[1] = varray[1] * difft;
			}

			float theta = (darray[0] - darray[1]) / L;
			float rad = Math.abs((darray[0] + darray[1]) / 2*theta);
			radius_list.add(rad);
			theta_list.add(theta);
		}
	}
	
	
	public String toString(){
		
		String s = "";
		
		for(float f : theta_list){
			
			s += Float.toString(f) + " | ";
		}

		s += "\n";
		
		for(float r : radius_list){
			
			s += Float.toString(r) + " | ";
		}
		
		return s;
	}
	
}
