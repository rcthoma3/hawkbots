package org.usfirst.frc.team5229.robot;

public class auto {	
	RobotMovement robot = new RobotMovement();
	Sensors sensors = new Sensors();
	
	private final int RED=0;
	private final int BLUE=1;
	private int mySide=-1;
	
	//States
	public enum STATES {
		STOP,
		PICKUP_GEAR,
		DROP_GEAR,
		SHOOT_BALL,
		FIELD_READ,
		HOPPER,
		PICK_UP_BALLS,
		Exit,
		
	}
	
	STATES state = STATES.STOP;
	boolean hasGear = false;
	boolean hasBalls = false;
	boolean noBalls = false;
	boolean Robot = false; 
	boolean noGear = false;
	
	
	public auto() {
	
	}
	//Finds the burner and which team we are on.
	public void findBurner() {
		robot.turnRight(50, 90);
		int color = -1;
		while(color != RED && color != BLUE) {
			robot.DrivefowardBackward(100);
			color = sensors.ColorSensors();
			//if (sensors.FrontSensors()<1) {
			//	robot.turnLeft(50, 180);
			//}			
		}
		mySide=color;
	}
	//Ideally shoots ball
	//in: nothing
	//out: nothing
	public void shootball() {
		//Step 1:Use sensor to line up robot straight towards burner
		//sensors.lineUp();
		 double distanceToGoal = 0;
		 distanceToGoal = (sensors.SonicLeftCenter() + sensors.SonicRightCenter())/2;
		 while (distanceToGoal>80) {
			 robot.DrivefowardBackward(50);
			 distanceToGoal = (sensors.SonicLeftCenter() + sensors.SonicRightCenter())/2;
		 }
		 //robot.dumpBalls();
		 robot.DrivefowardBackward(-50);
	}
	// the robot lines up to shoot the ball at the top of boiler
	//in: nothing
	//out: nothing
	public void lineupwithburner() {
		double left = sensors.SonicLeftCenter();
		double right = sensors.SonicRightCenter();
		while (Math.abs(left - right) > 20 ) {
			if (left > right)
				robot.turnRight(50, 10);
			else
				robot.turnLeft(50, 10);
			left = sensors.SonicLeftCenter();
			right = sensors.SonicRightCenter();
		}		
	}
	//the robot gets the distance using the sensor then drops the gear once it gets to the gear pully
	//in: distance
	//out: gear drop
	public void dropgear() {
		//Step 1: Have front sensors get distance from wall
		double left = sensors.SonicLeftCenter();
		double right = sensors.SonicRightCenter();
		//Step 2: Line up by making distances equal
		while (Math.abs(left - right) > 20) {
			if (left > right)
				robot.turnRight(50, 10);
			else 
				robot.turnLeft(50, 10);
			left = sensors.SonicLeftCenter();
			right = sensors.SonicRightCenter();
		}
		//Step 3: Move forward certain distance from gear drop location
		double distanceToWall = 0;
		distanceToWall = (sensors.SonicLeftCenter() + sensors.SonicRightCenter())/2;
		while(distanceToWall>80) {
			robot.DrivefowardBackward(50);
			distanceToWall = (sensors.SonicLeftCenter() + sensors.SonicRightCenter())/2;
		}
		//Step 4: Drop gear onto peg
		
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
		
		
		
	
	
	public void run() {
		if (state==STATES.STOP) {
			if (hasBalls) {
				state = STATES.PICKUP_GEAR;
			}
		} //End stop state		
		else if (state==STATES.PICKUP_GEAR) {
			if (hasGear) {
				state = STATES.DROP_GEAR;
			}
		}//End pickup gear state
		else if (state==STATES.DROP_GEAR) {
			if (noGear) {
				state = STATES.FIELD_READ;
			}
		} //End drop gear
	
		else if (state==STATES.HOPPER) {
			if (hasBalls){
				state = STATES.SHOOT_BALL;
			}
		}//End hopper state
		else if (state==STATES.SHOOT_BALL) {
			if (noBalls) {
				state = STATES.Exit;
			}
		}//End shoot ball
	}
	
	
}
