package main;

import main.mstorm_moving.Engine;

public class LineFollower {

	public static void main(String[] args) {
		try{
			Engine engine = new Engine();
			engine.move();
		}catch(Exception ie){
			ie.printStackTrace();
		}
		
	}
}
