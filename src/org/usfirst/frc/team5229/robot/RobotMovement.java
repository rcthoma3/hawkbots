package org.usfirst.frc.team5229.robot;

import java.util.Scanner;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class RobotMovement {
	private boolean modeArcade = true; 
	private boolean modeFine = true; 
    private boolean squaredInputs = true; 
    private static double wheelBase = 24.0;
    public float outputMagnitude;
    
    private Sensors sensors = new Sensors();
   
	public RobotMovement(){
	}
	
	
	public RobotMovement(double newWheelBase){
		wheelBase = newWheelBase;
	}
	
	public RobotMovement(Joystick Left, Joystick Right){
		//set left joystick set right joystick
		SetLeftJoystick(Left);
		SetRightJoystick(Right);
	}
	

	public RobotMovement(Joystick Left, Joystick Right, double newWheelBase){
		//set left joystick set right joystick
		SetLeftJoystick(Left);
		SetRightJoystick(Right);
		wheelBase = newWheelBase;
	}

	RobotDrive myRobot = new RobotDrive(0 , 1, 2, 3); // why is there 0, 1, 2, 3? What are those?
	Joystick leftStick;
	Joystick rightStick;
	
    Scanner in = new Scanner(System.in);
	
	
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
    //speed>0, moves forward
    //speed<0, moves backward
    public void DrivefowardBackward(double speed){
    	if(speed>1.0){
    		speed=1;
    	}
    	if(speed<-1.0){
    		speed=-1;
    	}
    	myRobot.drive(speed,0);
    	
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
    public int whatisSpeed(int speed){
    	return speed;
    }
    
    //tell how many degrees did the robot turn//
    public double whatisDegree(double r){
    	return rToCurve(r);
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
	
	//order the type of drive and how the drive works//
	public void doDriveType(){
	
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
	
	//Stop when there is something forward//
	public void drivefowardunsafe(double speed){
		float distance = sensors.FrontSensors();
		if(distance==1 && speed>0){
				myRobot.drive(0,0);
			}
		}
	}