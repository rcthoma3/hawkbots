package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	
	private String gameData;//String obatin that is used to tell ownership of the scales and switches for the alliance
	private int startpos = 0;//Determine the start position of the robot
	private SendableChooser<Integer> autoChooser;//Created a method that allowed driver to input position
	private boolean setAutoChooser = false;//Check if AutoChooser is set up
   
	
	//Return game string
	//in:nothing
	//out:gameData
	public String getGameMsg(){
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		return gameData;
	} 
	
	//Set AutoChooser
	//in:nothing
	//out:setAutoChooser
   public boolean setAutoChooser() {
	   autoChooser = new SendableChooser<Integer>();
	   autoChooser.addDefault("Left", 0);//0 show that the robot is on the left side
	   autoChooser.addObject("Center", 1);//1 show that the robot is in the center
	   autoChooser.addObject("Right", 2);//2 show that the robot is on the right side
	   SmartDashboard.putData("Autonomous Mode Chooser", autoChooser);
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
			startpos = (int) autoChooser.getSelected();
			System.out.println("Starting Pos: " + startpos);
		}
		return startpos;
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
	
	//TODO: Function to pick path
	
}
