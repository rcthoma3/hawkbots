package org.usfirst.frc.team5229.robot;



public class Sensors {

	//Creates a public variable for encoders
	public static int encTicksPerRot = 1440;
	
	//Gets robot position
	//in:distance
	//out:Nothing Yet
	public void getPosition (double distance) {
		
		//use sensor to figure out what starting position we are in
		//relay information if needed
		
	}

	
	
	//Make a robot move forward during autonomous
	//in:Distance, Speed
	//out:nothing
	public void DriveFowardAuto(int Distance, double speed) {

	}
	
	//Converts Encoder variable to distance
	//in: wheel size in inches and encoder counts
	//out: distance traveled in inches 
	public double EncToDis (double whlsize, int enc) {
		
		//Converts the diameter of the wheel to radius
		//in inches
		double r = whlsize / 2;
		//Gets rotation from encoder value in inches
		double rotations = enc / encTicksPerRot;
		// Distance obtained from 2PIr multiplied
		//By rotations in inches
		double dis = 2*Math.PI*r * rotations;
		return dis;
	}
	
	//Converts distance(in inches) to a variable for
	//in: wheel size in inches and desired distance in inches
	//out: encoder counts
	public int DisToEnc (double whlsize, double dis) {
		
		//Converts the diameter of the wheel to radius
		//in inches
		double r = whlsize / 2;
		// Encoder variable obtained from 2PIr multiplied
		//By rotations
		int enc = (int) Math.round(((encTicksPerRot*dis)/( 2 * Math.PI * r)));
		
		return enc;
	}


}
