package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	
	private String gameData;//String obatin that is used to tell ownership of the scales and switches for the alliance
	private int startpos = 0;//Determine the start position of the robot
	private int startgoal = 0;//Determine the goal we are trying to reach
	private SendableChooser<Integer> postionChooser;//Created a method that allowed driver to input position
	private SendableChooser<Integer> goalChooser;//Created a method that allowed the driver to choose thier goal
	private boolean setAutoChooser = false;//Check if AutoChooser is set up
	Sensors sensor;
	Elevator elevator = new Elevator();
	public double autoSpeed = 1;
	public int autoDis = 0;
	
   
	public boolean setSensor(Sensors sensorIn) {
		sensor = sensorIn;
		return true;
	}
	//Return game string
	//in:nothing
	//out:gameData
	public String getGameMsg(){
		gameData = DriverStation.getInstance().getGameSpecificMessage();
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
	   goalChooser.addDefault("Switch", 0);//0 show that the robot is going to the switch
	   goalChooser.addObject("Scale", 1);//1 show that the robot is going to the scale
	   goalChooser.addObject("Neither", 2);//2 show that the robot is doing neither
	   SmartDashboard.putData("Goal Mode Chooser", goalChooser);
	   setAutoChooser = true; 
	   
	   return setAutoChooser;
   }
	
   //Obtain position of the function
   //in:nothing
   //out:startpos
	public int getPositoin() {
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
		return startgoal;
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
		char mySwitch = getMySwitch();
		int myPosition = getPositoin();
		int myGoal = getGoal();
		char myScale = getScale();
		if(myGoal == 0) {
		if(myPosition == 0) {
			if(mySwitch == 'L') {
				sensor.driveFowardAuto(149);
				sensor.stopRobot();
				sensor.turnRobotRightGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(40); 
				//elevator.raiseElevatorDis(autoDis);
				sensor.stopRobot();
				elevator.ejectBlock(autoSpeed);
				sensor.stopRobot();
				exit = true;
			}else if (mySwitch == 'R') {
				sensor.driveFowardAuto(218);
				sensor.stopRobot();
				sensor.turnRobotRightGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(168);
				sensor.stopRobot();
				sensor.turnRobotRightGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(24);
				exit = true;
			}
		}else if(myPosition == 1) {
			if(mySwitch == 'L') {
				sensor.driveFowardAuto(125);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(30);
				sensor.stopRobot();
				sensor.turnRobotRightGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(15);
				sensor.stopRobot();
				//elevator.raiseElevatorDis(autoDis);
				//sensor.stopRobot();
				//elevator.ejectBlock(autoSpeed);
				//sensor.stopRobot();
				exit = true;
			}else if(mySwitch == 'R') {
				sensor.driveFowardAuto(125);
				sensor.stopRobot();
				sensor.turnRobotRightGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(30);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(15);
				sensor.stopRobot();
				//elevator.raiseElevatorDis(autoDis);
				//sensor.stopRobot();
				//elevator.ejectBlock(autoSpeed);
				//sensor.stopRobot();
				exit = true;
			}
		}else if(myPosition == 2) {
			if(mySwitch == 'R') {
				sensor.driveFowardAuto(149);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(40);
				sensor.stopRobot();
				//elevator.raiseElevatorDis(autoDis);
				//sensor.stopRobot();
				elevator.ejectBlock(autoSpeed);
				sensor.stopRobot();
				exit = true;
			}else if(mySwitch == 'L') {
				sensor.driveFowardAuto(218);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(168);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(24);
				sensor.stopRobot();
				//elevator.raiseElevatorDis(autoDis);
				//sensor.stopRobot();
				//elevator.ejectBlock(autoSpeed);
				//sensor.stopRobot()
                exit = true;
			}
			
		}
		}else if(myGoal == 1) {
			if(myPosition == 0) {
				if(myScale == 'L') {
					sensor.driveFowardAuto(323);
					sensor.stopRobot();
					sensor.turnRobotRightGyro(90);
					sensor.stopRobot();
					//elevator.raiseElevatorDis(autoDis);
					//sensor.stopRobot();
					//elevator.ejectBlock(autoSpeed);
					//sensor.stopRobot()
					exit = true;
				}else if(myScale == 'R') {
					sensor.driveFowardAuto(218);
					sensor.stopRobot();
					sensor.turnRobotRightGyro(90);
					sensor.stopRobot();
					sensor.driveFowardAuto(240);
					sensor.stopRobot();
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();
					sensor.driveFowardAuto(68);
					sensor.stopRobot();
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();
					//elevator.raiseElevatorDis(autoDis);
					//sensor.stopRobot();
					//elevator.ejectBlock(autoSpeed);
					//sensor.stopRobot()
					exit = true;
				}
			}else if(myPosition == 1) {
				if(myScale == 'R') {
					sensor.turnRobotRightGyro(31);
					sensor.stopRobot();
					sensor.driveFowardAuto(140);
					sensor.stopRobot();
					sensor.turnRobotLeftGyro(31);
					sensor.stopRobot();
					sensor.driveFowardAuto(204);
					sensor.turnRobotLeftGyro(90);
					sensor.stopRobot();
					//elevator.raiseElevatorDis(autoDis);
					//sensor.stopRobot();
					//elevator.ejectBlock(autoSpeed);
					//sensor.stopRobot()
					exit = true;
				}else if(myScale == 'L') {
					sensor.turnRobotLeftGyro(45);
					sensor.stopRobot();
					sensor.driveFowardAuto(170);
					sensor.turnRobotRightGyro(45);
					sensor.driveFowardAuto(204);
					sensor.turnRobotRightGyro(90);
					sensor.stopRobot();
					//elevator.raiseElevatorDis(autoDis);
					//sensor.stopRobot();
					//elevator.ejectBlock(autoSpeed);
					//sensor.stopRobot();
					exit = true;
				}
			}else if(myPosition == 2) {
				if(myScale == 'R');
				sensor.driveFowardAuto(323);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				//elevator.raiseElevatorDis(autoDis);
				//sensor.stopRobot();
				//elevator.ejectBlock(autoSpeed);
				//sensor.stopRobot()
				exit = true;
			}else if(myScale == 'L') {
				sensor.driveFowardAuto(218);
				sensor.stopRobot();
				sensor.turnRobotRightGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(240);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				sensor.driveFowardAuto(68);
				sensor.stopRobot();
				sensor.turnRobotLeftGyro(90);
				sensor.stopRobot();
				//elevator.raiseElevatorDis(autoDis);
				//sensor.stopRobot();
				//elevator.ejectBlock(autoSpeed);
				//sensor.stopRobot()
				exit = true;
			}
			
		}else if(myGoal == 2) {
			if(myPosition == 0) {
				sensor.driveFowardAuto(126);
				sensor.stopRobot();
				exit = true;
			}else if(myPosition == 1) {
			   sensor.driveFowardAuto(55);
			   sensor.stopRobot();
			   sensor.turnRobotRightGyro(90);
		       sensor.stopRobot();
		       sensor.driveFowardAuto(60);
		       sensor.stopRobot();
		       sensor.turnRobotLeftGyro(90);
		       sensor.driveFowardAuto(40);
		       sensor.stopRobot();
		       exit = true;
			}else if(myPosition == 2) {
				sensor.driveFowardAuto(126);
				sensor.stopRobot();
				exit = true;
			}
		}
		return exit;
	}
	
}
