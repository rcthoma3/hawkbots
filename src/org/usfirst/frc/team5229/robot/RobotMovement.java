package org.usfirst.frc.team5229.robot;

import java.util.Scanner;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class RobotMovement {

	RobotDrive myRobot = new RobotDrive(0 , 1, 2, 3); // why is there 0, 1, 2, 3? What are those?
	Joystick leftStick;
	Joystick rightStick;
	
    Scanner in = new Scanner(System.in);
	
	private boolean modeArcade = true; 

	private boolean modeFine = true; 

    private boolean squaredInputs = true; 
    
    private static double wheelBase = 24.0;
    
    //set left stick//
    public void SetLeftJoystick(Joystick lJoystick){
    	leftStick = lJoystick;
    }
    
    //set right stick//
    public void SetRightJoystick(Joystick rJoystick) {
    	rightStick = rJoystick;
    }
    
    //set drive forward and backward//
    //in: nothing
    //out: nothing
    public void DrivefowardBackward(){
    	
    }
    
    private double rToCurve(double r){
    	return Math.exp(-r/wheelBase);
    }
    
  //how far the outside wheel(right wheel) is going
    public double angleToTurnDistance(double r, double angdeg){
	   return (r+wheelBase/2)*Math.toRadians(angdeg);
    }
    
    //turn left
    //in: speed, radius(r)
    //out: nothing
    public void turnLeft(double speed, double r){
    	myRobot.drive(speed, -rToCurve(r));
    }
    
  //turn right
    //in: speed, radius(r)
    //out: nothing
    public void turnRight(double speed, double r){
    	myRobot.drive(speed, rToCurve(r));
    }
    
    //tell what the is speed//
    public void whatisSpeed(){
    	
    }
    
    //tell how many degrees did the robot turn//
    public void whatisDegree(){
    	
    }
    
    //set mode to arcadeDrive//
	public void setmodeArcade(){
		modeArcade = true; 
	}
	
	//set mode to tankDrive//
	public void setmodeTank() {
		modeArcade = false;
	}

	//set mode to Fine//
	public void setmodeFine(){
		modeFine = true;
	}
	
	//set mode to Coarse//
	public void setmodeCoarse(){
		modeFine = false; 
	}
	
	//tell that mode is arcadeDrive//
	public boolean ismodeArcade(){
		return modeArcade;		
	}
	
	//tell that mode is tankDrive//
	public boolean ismodeTank(){
		return ! modeArcade; 
	}
	
	//tell that mode is Fine//
	public boolean ismodeFine(){
		return modeFine;
		
	}
	
	//tell that mode is Coarse//
	public boolean ismodeCoarse(){
		return !modeFine; 
	}
	
	//change the mode of drive and speed//
	public void modeChange(){
	
	if(modeArcade == true && modeFine == true){
    	myRobot.arcadeDrive(leftStick, squaredInputs);
    }else if(modeArcade == false && modeFine == true){
    	myRobot.tankDrive(leftStick, rightStick, squaredInputs);
    }else if(modeArcade == true && modeFine == false){
    	myRobot.arcadeDrive(leftStick);
    }else{
    	myRobot.tankDrive(leftStick, rightStick); 
    }
    
}
	
   	//tell when to stop at a front obstacle//
	public void stopatfrontobstacle(float distance){
		if(distance == 1){
			if(outputMaganitude>0){
			    outputMaganitude=0;
		}
	}
	}	
	
	//tell when to stop at a backward obstacle//
	public void stopatbackwardobstacle(float distance){
	    if(distance == -1){
           if(outputMaganitude<0){
        	   outputMaganitude=0;
           }

	    }
	}

	
}