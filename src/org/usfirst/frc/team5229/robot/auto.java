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
		//Step 1:Use sensor to line up robot straight towards burner
		//STep 2:Aim towards top goal
		//Step 3:Fire
		//Step 4:Back up
		
	}
	//drives forward
	//in: nothing
	//out: nothing
	public void moveforward() {
		robot.DrivefowardBackward(50);
		
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
		//Turns Left
		robot.turnLeft(50,90);
		
		
	}
    //the robot drives distance them turns right
	//in: nothing
	//out: nothing
	public void turnright() {
		//Turns right 
		robot.turnRight(50,90);
		
		
	}
	//the robot gets the distance using the sensor then drops the gear once it gets to the gear pully
	//in: distance
	//out: gear drop
	public void dropgear() {
		//Step 1: Have front sensors get distance from wall
		//Step 2: Line up by making distances equal
		//Step 3: Move forward certain distance from gear drop location
		//Step 4: Drop fear onto peg
		
	}

	//the robot picks up the gear
	//in: nothing
	//out: gear
	public void pickupgear() {
		//Step 1: Drive forward till sensor picks up distance
		//Step 2: use sensor to reposition so that robot is straight 
		//Step 3: Drive forward until a certain distance from drop off is reached
		//Step 4: Wait for drop off then back up
		
		
	}
	public void findhopperfromburner() {
		if (mySide==RED) 
		{
			robot.turnRight(50,90);
			robot.DrivefowardBackward(50);
		}
		else 
		{ 
				robot.turnLeft(50,90);
				robot.DrivefowardBackward(50);
				
		}
	}
	//the robot follows a pick up pattern for fuel
	//in: pattern
	//out: balls
	public void followpickuppattern() {
		robot.turnLeft(50,90);
		robot.DrivefowardBackward(50);
		robot.turnRight(50,90);
		robot.DrivefowardBackward(50);
		robot.turnRight(50,90);
		robot.DrivefowardBackward(50);
		robot.turnRight(50,90);
		robot.DrivefowardBackward(50);
		
	}
		
				
	
}
