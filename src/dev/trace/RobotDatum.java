package dev.trace;

public class RobotDatum {

	private int vl;
	private int vr;
	private long t;
	
	public RobotDatum(int vl, int vr, long t){
		
		this.vl = vl;
		this.vr = vr;
		this.t = t;
	}
	
	
	public int getVL()
	{
		return vl;
	}

	public int getVR()
	{
		return vr;
	}

	public long getTime()
	{
		return t;
	}
	
	
	@Override
	public String toString(){
		
		return vl + " " + vr + " " + t + "\n";
	}
}
