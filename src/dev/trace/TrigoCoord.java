package dev.trace;

public class TrigoCoord {

	private float r;
	private float theta; 
	
	public TrigoCoord(float radius, float theta){
		
		r = radius;
		this.theta = theta;
	}
	
	
	public float getX(){
		
		return r * (float) Math.cos(theta);
	}
	

	public float getY(){
		
		return r * (float)  Math.sin(theta);
	}


	public String toString(){
		
		return "(" + getX() + "," +getY()+ ")"; 
	}
	
}
