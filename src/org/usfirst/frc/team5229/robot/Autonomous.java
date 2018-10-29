package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	
	private String gameData;//String obtain that is used to tell ownership of the scales and switches for the alliance
	private int startpos = 0;//Determine the start position of the robot
	private int startgoal = 2;//Determine the goal we are trying to reach
	private SendableChooser<Integer> postionChooser;//Created a method that allowed driver to input position
	private SendableChooser<Integer> goalChooser;//Created a method that allowed the driver to choose thier goal
	private boolean setAutoChooser = false;//Check if AutoChooser is set up
	Sensors sensor;
	Elevator elevator;
	public double autoSpeed = 0.25;
	public int autoDis = 13000;
	public int autoDis2 = 70000;
	boolean validMsg = false;
	Timer time = new Timer();
	int globalGoal = 5;
	
   
	public boolean setSensor(Sensors sensorIn) {
		sensor = sensorIn;
		return true;
	}
	
	public boolean setElevator(Elevator elevatorIn) {
		elevator = elevatorIn;
		return true;
	}
	//Return game string
	//in:nothing
	//out:gameData
	public String getGameMsg(){
		time.start();
		do {
			gameData = DriverStation.getInstance().getGameSpecificMessage();
				if (gameData.compareTo("LLL") == 0 || gameData.compareTo("LRL") == 0 || gameData.compareTo("RRR") == 0 || gameData.compareTo("RLR") == 0) {
					validMsg = true;
				}
		} while (!time.hasPeriodPassed(2) && !validMsg);
		return gameData;
	} 
	
	//Set AutoChoosers
	//in:nothing
	//out:setAutoChooser
   public boolean setAutoChooser() {
	   postionChooser = new SendableChooser<Integer>();
	   postionChooser.addDefault("Left", 0);//0 show that the robot is on the left side
	   postionChooser.addObject("Center", 1);//1 show that the robot is in the center
	   postionChooser.addObject("Right", 2);//2 show that the robot is on the right side
	   SmartDashboard.putData("Position Mode Chooser", postionChooser);
	   goalChooser = new SendableChooser<Integer>();
	   goalChooser.addDefault("Neither", 2);//2 show that the robot is doing neither
	   goalChooser.addObject("Scale", 1);//1 show that the robot is going to the scale
	   goalChooser.addObject("Switch", 0);//0 show that the robot is going to the switch
	   SmartDashboard.putData("Goal Mode Chooser", goalChooser);
	   setAutoChooser = true; 
	   
	   return setAutoChooser;
   }
	
   //Obtain position of the function
   //in:nothing
   //out:startpos
	public int getPosition() {
		if(!setAutoChooser) {
			System.err.println("Error: Auto Chooser not set up");
		}else {
			startpos = (int) postionChooser.getSelected();
		}
		return startpos;
	}
	
	//Obtain the goal for auton
	//in:nothing
	//out:startgoal
	public int getGoal() {
		if(!setAutoChooser) {
			System.err.println("Error: Auto Chooser not set up");
		}else {
			startgoal = (int) goalChooser.getSelected();
		}
		globalGoal = startgoal;
		return startgoal;
	}
	
	public int getGlobalGoal() {
		return globalGoal;
	}
	
	//Obtain what side of the switch the alliance have 
	//in:nothing
	//out:firstSwitch
	public char getMySwitch() {
		char firstSwitch = gameData.charAt(0);//Character that show what side of the alliance's switch is obtained
		return firstSwitch;
	}
	
	//Set what side of the scale that the alliance have
	//in:nothing
	//out:scale
	public char getScale() {
		char scale = gameData.charAt(1);//Character that show what side of the scale is obtained
		return scale;		
	}
	
	//Set what side of the opponent's switch that the alliance gain ownership of 
	//in:nothing
	//out:nothing
	public char getOppSwitch() {
		char secondSwitch = gameData.charAt(2);//Character that show what side of the opponent's switch is obtained
		return secondSwitch;
	}
	
	// Do autonomous 
	//in:nothing
	//out:exitd
	public boolean followPath() {
		boolean exit = false;
		boolean clawDown = false;
		int myPosition = getPosition();
		int myGoal = getGoal();
		char myScale = 'X';
		char mySwitch = 'X';
		if (validMsg) {
			myScale = getScale();
			mySwitch = getMySwitch();
		}
		else {
			myGoal = 2;
		}
		globalGoal = myGoal;
		if(myGoal == 0) { //Switch
		if(myPosition == 0) { //Left
			if(mySwitch == 'L') {
				sensor.driveForwardAuto(145);
				sensor.stopRobot();
				sensor.turnRobotRightGyro(90);
				sensor.stopRobot();
				do {clawDown = elevator.tiltClawDown(0.8);}while(!clawDown);
				elevator.openClaws(0.8);
				elevator.raiseElevatorDis(autoDis);
				Timer.delay(3);
				sensor.driveForwardAuto(30);
				sensor.stopRobot();	
				elevator.ejectBlock(autoSpeed);
				elevator.openClaws(0);
				Timer.delay(0.5);
				elevator.stopWheels();
				exit = true;
				
			}else if (mySwitch == 'R') {
				sensor.driveForwardAuto(150);
				sensor.stopRobot();
				/*sensor.driveForwardAuto(210);
				sensor.stopRobot();
				sensor.turnRobotRightGyro(90);
				do {clawDown = elevator.tiltClawDown(0.8);}while(!clawDown);
				elevator.openClaws(0.8);
				elevator.raiseElevatorDis(autoDis);
				sensor.driveForwardAuto(165);
				sensor.turnRobotRightGyro(135);
				sensor.driveForwardAuto(40);
				elevator.ejectBlock(autoSpeed);
				elevator.noGrip();
				Timer.delay(0.5);
				elevator.stopWheels(); Use this later*/
				exit = true;
			}
		}else if(myPosition == 1) { //Center
			if(mySwitch == 'L') {
				sensor.driveForwardAuto(45); //48
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				sensor.driveForwardAuto(110);
				sensor.stopRobot();
				sensor.turnRobotRightGyro(90);
				sensor.stopRobot();
				do {clawDown = elevator.tiltClawDown(0.8);}while(!clawDown);
				elevator.openClaws(0.8);
				elevator.raiseElevatorDis(autoDis);
				Timer.delay(1.5);				
				sensor.driveForwardAuto(65);
				sensor.stopRobot();
				elevator.ejectBlock(autoSpeed);
				elevator.noGrip();
				Timer.delay(1.0);
				elevator.stopWheels();
				exit = true;
			}else if(mySwitch == 'R') { 
				System.out.println("Im running");
				do {clawDown = elevator.tiltClawDown(0.8);}while(!clawDown);
				elevator.openClaws(0.8);
				elevator.raiseElevatorDis(autoDis);			
				Timer.delay(1);
				sensor.driveForwardAuto(100); //48
				sensor.stopRobot();
				elevator.ejectBlock(autoSpeed);
				elevator.noGrip();
				Timer.delay(0.5);
				elevator.stopWheels();
				//sensor.turnRobotRightGyro(90);
				//sensor.stopRobot();
				//sensor.driveFowardAuto(60);
				//sensor.stopRobot();
				//sensor.turnRobotLeftGyro(90);
				//sensor.stopRobot();
				//elevator.raiseElevatorDis(autoDis);
				//sensor.stopRobot();		
				//sensor.driveFowardAuto(15);
				//sensor.stopRobot();	
				//elevator.ejectBlock(autoSpeed);
				//sensor.stopRobot();
				exit = true;
			}
		}else if(myPosition == 2) { //Right
			if(mySwitch == 'R') {
				sensor.driveForwardAuto(145);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				do {clawDown = elevator.tiltClawDown(0.8);}while(!clawDown);
				elevator.openClaws(0.8);
				elevator.raiseElevatorDis(autoDis);
				Timer.delay(3);
				sensor.driveForwardAuto(30);
				sensor.stopRobot();	
				elevator.ejectBlock(autoSpeed);
				elevator.noGrip();
				Timer.delay(0.5);
				elevator.stopWheels();
				exit = true;
				
			}else if(mySwitch == 'L') {
				sensor.driveForwardAuto(150);
				sensor.stopRobot();
				/*sensor.driveFowardAuto(218);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(168);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				elevator.raiseElevatorDis(autoDis);
				sensor.stopRobot();
				sensor.driveFowardAuto(24);
				sensor.stopRobot();
				elevator.ejectBlock(autoSpeed);
				sensor.stopRobot();*/
                exit = true;
			}
			
		}
		}else if(myGoal == 1) { //Scale
			if(myPosition == 0) { //Left
				if(myScale == 'L') {
					do {clawDown = elevator.tiltClawDown(0.8);}while(!clawDown);
					elevator.openClaws(0.8);
					elevator.raiseElevatorDis(autoDis2);
					sensor.driveForwardAuto(300);
					sensor.stopRobot();
					Timer.delay(2);
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();
					//elevator.raiseElevatorDis(autoDis);
					//sensor.stopRobot();
					elevator.ejectBlock(autoSpeed);
					elevator.noGrip();
					Timer.delay(0.5);
					elevator.stopWheels();
					exit = true;
				}else if(myScale == 'R') {
					sensor.driveForwardAuto(150);
					sensor.stopRobot();
					sensor.turnRobotRightGyro(90);
					sensor.stopRobot();
					do {clawDown = elevator.tiltClawDown(0.8);}while(!clawDown);
					elevator.openClaws(0.8);
					elevator.raiseElevatorDis(autoDis2);
					sensor.driveForwardAuto(240);
					sensor.stopRobot();
					Timer.delay(3);
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();
					elevator.ejectBlock(0.8);
					elevator.noGrip();
					Timer.delay(0.5);
					elevator.stopWheels();
					/*sensor.turnRobotRightGyro(90);
					sensor.stopRobot();
					sensor.driveForwardAuto(240);
					sensor.stopRobot();
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();
					sensor.driveForwardAuto(68);
					sensor.stopRobot();
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();*/
					//elevator.raiseElevatorDis(autoDis);
					//sensor.stopRobot();
					//elevator.ejectBlock(autoSpeed);
					//sensor.stopRobot()
					exit = true;
				}
			}else if(myPosition == 1) { //Center
				if(myScale == 'R') {
					sensor.driveForwardAuto(45);
					sensor.stopRobot();
					sensor.turnRobotRightGyro(90);
					sensor.stopRobot();
					sensor.driveForwardAuto(30);
					sensor.stopRobot();
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();
					do {clawDown = elevator.tiltClawDown(0.8);}while(!clawDown);
					elevator.openClaws(0.8);
					elevator.raiseElevatorDis(autoDis2);
					sensor.driveForwardAuto(265);
					sensor.stopRobot();
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();
					elevator.ejectBlock(0.8);
					elevator.noGrip();
					Timer.delay(0.5);
					elevator.stopWheels();
					/*sensor.turnRobotRightGyro(31);
					sensor.stopRobot();
					sensor.driveForwardAuto(140);
					sensor.stopRobot();
					sensor.turnRobotLeftGyro(31);
					sensor.stopRobot();
					sensor.driveForwardAuto(204);
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();*/
					//elevator.raiseElevatorDis(autoDis);
					//sensor.stopRobot();
					//elevator.ejectBlock(autoSpeed);
					//sensor.stopRobot()
					exit = true;
				}else if(myScale == 'L') {
					sensor.driveForwardAuto(45);
					sensor.turnRobotRightGyro(90);
					sensor.driveForwardAuto(140);
					sensor.stopRobot();
					sensor.driveForwardAuto(30);
					sensor.stopRobot();
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();
					do {clawDown = elevator.tiltClawDown(0.8);}while(!clawDown);
					elevator.openClaws(0.8);
					elevator.raiseElevatorDis(autoDis2);
					sensor.driveForwardAuto(265);
					sensor.stopRobot();
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();
					elevator.ejectBlock(0.8);
					elevator.noGrip();
					Timer.delay(0.5);
					elevator.stopWheels();
					/*sensor.turnRobotLeftGyro(45);
					sensor.stopRobot();
					sensor.driveForwardAuto(170);
					sensor.turnRobotRightGyro(45);
					sensor.driveForwardAuto(204);
					sensor.turnRobotRightGyro(90);
					sensor.stopRobot();*/
					//elevator.raiseElevatorDis(autoDis);
					//sensor.stopRobot();
					//elevator.ejectBlock(autoSpeed);
					//sensor.stopRobot();
					exit = true;
				}
			}else if(myPosition == 2) { //Right
				if(myScale == 'R');
				do {clawDown = elevator.tiltClawDown(0.8);}while(!clawDown);
				elevator.openClaws(0.8);
				elevator.raiseElevatorDis(autoDis2);
				sensor.driveForwardAuto(300);
				sensor.stopRobot();
				Timer.delay(2);
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				//elevator.raiseElevatorDis(autoDis);
				//sensor.stopRobot();
				elevator.ejectBlock(autoSpeed);
				elevator.noGrip();
				Timer.delay(0.5);
				elevator.stopWheels();
				exit = true;
			}else if(myScale == 'L') {
				sensor.driveForwardAuto(150);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				do {clawDown = elevator.tiltClawDown(0.8);}while(!clawDown);
				elevator.openClaws(0.8);
				elevator.raiseElevatorDis(autoDis2);
				sensor.driveForwardAuto(240);
				sensor.stopRobot();
				Timer.delay(3);
				sensor.turnRobotRightGyro(90);
				sensor.stopRobot();
				sensor.driveForwardAuto(50);
				sensor.stopRobot();
				elevator.ejectBlock(autoSpeed);
				elevator.noGrip();
				Timer.delay(0.5);
				elevator.stopWheels();
				exit = true;
			}
			
		}else if(myGoal == 2) { //Neither
			if(myPosition == 0) { //Left
				sensor.driveForwardAuto(126);
				sensor.stopRobot();
				exit = true;
			}else if(myPosition == 1) { //Center
				sensor.driveForwardAuto(100);
				sensor.stopRobot();
			   /*sensor.driveFowardAuto(55);
			   sensor.stopRobot();
			   sensor.turnRobotRightGyro(90);
		       sensor.stopRobot();
		       sensor.driveFowardAuto(60);
		       sensor.stopRobot();
		       sensor.turnRobotLeftGyro(90);
		       sensor.driveFowardAuto(40);
		       sensor.stopRobot(); */
		       exit = true;
			}else if(myPosition == 2) { //Right
				sensor.driveForwardAuto(126);
				sensor.stopRobot();
				exit = true;
			}
		}
		return exit;
	}
	
}
