package dev.trace;

import java.util.ArrayList;

public class AngleCalculation {

	private static final float DISTANCE_2PI = 0.17165f;	// meter
	private static final int PI_2 = 360;
	private static final float L = 0.1438f;				// meter
	private ArrayList<TrigoCoord> coordinates;
	
	public AngleCalculation(){
		
		coordinates = new ArrayList<>();
	}
	
	
	public void generateData(ArrayList<RobotDatum> rl)
	{
		for(int i = 0; i < rl.size(); i++){
			
			float d1 = Math.abs(rl.get(i).getVL()*DISTANCE_2PI/PI_2);
			float d2 = Math.abs(rl.get(i).getVR()*DISTANCE_2PI/PI_2);
			float [] darray = new float[]{d1,d2};
			float [] varray = new float[]{darray[0],darray[1]};
			
			if(i < rl.size() - 1){
				
				float difft = (rl.get(i+1).getTime() - rl.get(i).getTime()) / 1000f;
				darray[0] = varray[0] * difft;
				darray[1] = varray[1] * difft;
			}

			float theta = (darray[0] - darray[1]) / L;
			float rad = Math.abs((darray[0] + darray[1]) / 2*theta);
			
			coordinates.add(new TrigoCoord(rad, theta, rl.get(i).getTime()));
		}
	}
	
	
	public String toString(){
		
		String s = "| ";
		int i = 0;
		
		for(TrigoCoord coord : coordinates){
			
			s += coord.toString() + " | ";
			i++;
			
			if(i%4 == 0)
				s += "\n| ";
		}
		
		return s;
	}
}
