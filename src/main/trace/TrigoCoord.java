package main.trace;

public class TrigoCoord {

	private float r;
	private float theta;
	private long t;

	public TrigoCoord(float radius, float theta, long time) {

		r = radius;
		this.theta = theta;
		t = time;
	}

	public float getX() {

		return r * (float) Math.cos(theta);
	}

	public float getY() {

		return r * (float) Math.sin(theta);
	}

	public String toString() {

		return "(" + getX() + "," + getY() + "," + t + ")";
	}

}
