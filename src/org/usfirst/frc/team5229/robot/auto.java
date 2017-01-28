package org.usfirst.frc.team5229.robot;

public class auto {	
	RobotMovement robot = new RobotMovement();
	Sensors sensors = new Sensors();
	
	private final int RED=0;
	private final int BLUE=1;
	private int mySide=-1;
	
	
	public auto() {
	
	}
	//Finds the burner and which team we are on.
	public void findBurner() {
		robot.turnRight(50, 90);
		int color = -1;
		while(color != RED && color != BLUE) {
			robot.DrivefowardBackward(100);
			color = sensors.ColorSensors();
			if (sensors.FrontSensors()<1) {
				robot.turnLeft(50, 180);
			}			
		}
		mySide=color;
	}
	//Ideally shoots ball
	//in: nothing
	//out: nothing
	public void shootball() {
		
	}
	//drives forward
	//in: nothing
	//out: nothing
	public void moveforward() {
		
	}
	// the robot lines up to shoot the ball at the top of boiler
	//in: nothing
	//out: nothing
	public void lineupwithburner() {
		
	}
	// the robot drives distance then turns left
	//in: nothing
	//out: nothing 
	public void turnleft() {
		
	}
    //the robot drives distance them turns right
	//in: nothing
	//out: nothing
	public void turnright() {
		
	}
	//the robot gets the distance using the sensor then drops the gear once it gets to the gear pully
	//in: distance
	//out: gear drop
	public void dropgear() {
		
	}

	//the robot picks up the gear
	//in: nothing
	//out: gear
	public void pickupgear() {
		
	}
	public void findhopperfromburner() {
		if (mySide==RED) {
			robot.turnRight(50,90);
		}
				
	}
}
