package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class RobotMovement {
	private boolean modeArcade = true; 
	private boolean modeFine = true; 
    private boolean squaredInputs = true; 
    private static double wheelBase = 15.0;
    //Line below removed, causing errors.
    //private Sensors sensors = new Sensors();
    
    //state machine
    
    private double r = 0;
    private double speed = 1.0;
    
    //intialize states
    private enum State {
		STOPPED, FORWARD, BACKWARD, LEFT, RIGHT, CLIMBING, DESCENDING
	}
 
	State state = State.STOPPED;
	
	//create states
	public void tick(){
		switch (state){
		case STOPPED:
			DrivefowardBackward(0);
			break;
		case FORWARD:


			DrivefowardBackward(speed);
			break; 
		//case BACKWARD://
			//DrivefowardBackward(-1);//
			//DrivefowardBackward(speed);//
			//break;//
			

		case BACKWARD:
            DrivefowardBackward(speed);
			break; 
        case LEFT:
			turnLeft(speed,r);
			break;
		case RIGHT:
			turnRight(speed,r);
			break;
		case CLIMBING:
			climbmotermovement(speed);
			break;
		case DESCENDING:
			climbmotermovement(speed);
			break;
		}
		if (Testing){
			Test();
		}
	}
	
    //tell what the is state//
    //in:nothing
    //out:state
    public State whatisState(){
    	return state;
    }
    
    //sets the state//
    //in: new state
    //out:boolean if true, switch was succesful
    public boolean setState(State newState){
    	if(newState == state){
    		return true;
    	}
    	if(state == State.STOPPED){
    		state = newState;
    		return true;
    	}
    	if(newState == State.STOPPED){
    		state = newState;
    		return true;
    	}
	   if(state == State.RIGHT && newState == State.FORWARD){
		   state = newState;
		   return true;
	   }
	   if(state == State.LEFT && newState == State.FORWARD){
		   state = newState;
		   return true;
	   }
	   if(state == State.FORWARD && newState == State.RIGHT){
		   state = newState;
		   return true;
	   }
	   if(state == State.FORWARD && newState == State.LEFT){
		   state = newState;
		   return true;
	   }
	   return false;
		
    }
   
	//a constructor that doesn't take an input
	public RobotMovement(){
	}
	
	//in: newWheelBase
	//out:nothing
	public RobotMovement(double newWheelBase){
		wheelBase = newWheelBase;
		//wheelBase is the distance from the middle of the right wheel to the middle of the left wheel
	}
	
	//in:Joystick Left, Joystick Right
	//out: nothing
	public RobotMovement(Joystick Left, Joystick Right){
		//set left joystick set right joystick
		SetLeftJoystick(Left);
		SetRightJoystick(Right);
	}
	
    //in:Joystick Left, Joystick Right, newWheelBase
	//out:nothing
	public RobotMovement(Joystick Left, Joystick Right, double newWheelBase){
		//set left joystick set right joystick
		SetLeftJoystick(Left);
		SetRightJoystick(Right);
		wheelBase = newWheelBase;
	}

	//should all of the motors be inverted?
	//make motors inverted
	public void init(){
		myRobot.setInvertedMotor(RobotDrive.MotorType.kFrontLeft,false);
		myRobot.setInvertedMotor(RobotDrive.MotorType.kFrontRight,false);
		myRobot.setInvertedMotor(RobotDrive.MotorType.kRearLeft,false);
		myRobot.setInvertedMotor(RobotDrive.MotorType.kRearRight,false);
	} 
	RobotDrive myRobot = new RobotDrive(2, 3, 0, 1); // why is there 0, 1, 2, 3? What are those?
	Joystick leftStick;
	Joystick rightStick;
	
	
    //set left stick//
    //in:Joystick lJoystick
    //out:nothing
    public void SetLeftJoystick(Joystick lJoystick){
    	leftStick = lJoystick;
    }
    
    //set right stick//
    //in:Joystick rJoystick
    //out:nothing
    public void SetRightJoystick(Joystick rJoystick) {
    	rightStick = rJoystick;
    }
	
    //in:newWheelBase
    //out:nothing
   public void createWheelBase(double newWheelBase){
	wheelBase = newWheelBase;
		//wheelBase is the distance from the middle of the right wheel to the middle of the left wheel
	} 
   
    //set drive forward and backward//
    //in: speed
    //out: nothing
    //speed>0, moves forward
    //speed<0, moves backward
    public void DrivefowardBackward(double speed){
    	speed = speedLimit(speed);
    	myRobot.drive(speed,0);   	// WHY is there a -? Should motors be reversed?
    }

	/**
	 * @param speed
	 * @return speed such that -1<=speed<=1
	 */
    //set speed of stages
    //in:speed
    //out:nothing
	private double speedLimit(double speed) {
		if(state == State.FORWARD){
		if(speed>1.0){
    		speed=1.0;
    	}
    	if(speed<0){
    		speed=0;
    	}
		}
    	if(state == State.BACKWARD){
    		if(speed>0){
    			speed=0;
    		}
    		if(speed<-1.0){
    			speed=-1.0;
    		}
    	}
    	if(state == State.CLIMBING){
    		if(speed>1.0){
    			speed=1.0;
    		}
    		if(speed<0){
    			speed=0;
    		}
    	}
    	if(state == State.DESCENDING){
    		if(speed>0){
    			speed=0;
    		}
    		if(speed<-1.0){
    			speed=-1.0;
    		}
    	}
    	if(state == State.LEFT){
    		if(speed<0){
    			speed=0;
    		}
    		if(speed>1.0){
    			speed=1.0;
    		}
    	}
    	if(state == State.RIGHT){
    		if(speed<0){
    			speed=0;
    		}
    		if(speed>1.0){
    			speed=1.0;
    		}
    	}
    	
    	
	    return speed;
	}
    
    //in:r
    //out:Math.exp(-r/wheelBase)
    private double rToCurve(double r){
    	return Math.exp(-r/wheelBase);
	//takes the the wheelBase of a robot and the radius of the circle that the curve would be part of and imputs
	//it into a function that outputs the the curve 
    }
    
  //how far the outside wheel(right wheel) is going
    //in:r,angdeg
    //out:(r+wheelBase/2)*Math.toradians(angdeg)
    public double angleToTurnDistance(double r, double angdeg){
	   return (r+wheelBase/2)*Math.toRadians(angdeg);
    }
    
    //turn left
    //in: speed, radius(r)
    //out: nothing
    public void turnLeft(double speed, double r){
    	speed = speedLimit(speed);
    	if (r>0){
    		r=0;
    	}
    	if(r<-180){
    		r=-180;
    	}
    	//myRobot.drive(speed, rToCurve(r));
    	myRobot.drive(speed, -rToCurve(Math.toRadians(r)));
    }
    
    //turn right
    //in: speed, radius(r)
    //out: nothing
    public void turnRight(double speed, double r){
    	speed = speedLimit(speed);
    	if(r<0){
    		r=0;
    	}
    	if(r>180){
    		r=180;
    	}
    	//myRobot.drive(speed, rToCurve(r));
    	myRobot.drive(speed, rToCurve(Math.toRadians(r)));
    }
    
    //tell what the is speed//
    //in:nothing
    //out:speed
    public double whatisSpeed(double speed){
    	return speed;
    }
    
    //sets the speed//
    //in: new speed
    //out:nothing
    public void setSpeed(double newSpeed){
    	speed = newSpeed;
    	
    }

    
    //sets the radius//
    //in: new radius
    //out:nothing
    public void setRadius(double newRadius){
    	r = newRadius;
    	
    }
    
    //tell how many degrees did the robot turn//
    //in:r
    //out:rToCurve(r)
    public double whatisDegree(double r){
    	return rToCurve(r);
    }
    
    //set mode to arcadeDrive//
    //in:nothing
    //out:nothing
	public void setmodeArcade(){
		modeArcade = true; 
	}
	
	//set mode to tankDrive//
	//in:nothing
	//out:nothing
	public void setmodeTank() {
		modeArcade = false;
	}

	//set mode to Fine//
	//in:nothing
	//out:nothing
	public void setmodeFine(){
		modeFine = true;
	}
	
	//set mode to Coarse//
	//in:nothing
	//out:nothing
	public void setmodeCoarse(){
		modeFine = false; 
	}
	
	//tell that mode is arcadeDrive//
	//in:nothing
	//out:modeArcade
	public boolean ismodeArcade(){
		return modeArcade;		
	}
	
	//tell that mode is tankDrive//
	//in:nothing
	//out:! modeArcade
	public boolean ismodeTank(){
		return ! modeArcade; 
	}
	
	//tell that mode is Fine//
	//in:nothing
	//out:modeFine
	public boolean ismodeFine(){
		return modeFine;	
	}
	
	//tell that mode is Coarse//
	//in:nothing
	//out:nothing
	public boolean ismodeCoarse(){
		return !modeFine; 
	}
	
	//do the type of drive and how the drive work//
	//in:nothing
	//out:nothing
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
	//in:speed
	//out:true if robot does not stop and false if the robot does stop
	public boolean drivefowardunsafe(double speed){
		speed = speedLimit(speed);
		//Need to take the distance from the right center and 
		//average them.
		
		/* Removed to fix errors
		double distance = sensors.SonicLeftCenter();
		if(distance==1 && speed>0){
				myRobot.drive(0,0);
				return false;
		} else {
			myRobot.drive(speed,0);
			return true;
		}
		*/
		//Added to fix errors
		return true;
	} 
		
	protected SpeedController m_ballmoter;
	protected SpeedController m_convayeromoter;
	protected SpeedController m_climbmoter;
	protected SpeedController m_shootmoter;
   
    //ball motor is set//
    public void setballmoter(){
		m_ballmoter = new Talon(4);
	}
    
    //speed of ball motor is set//
    //in:ballspeed
    //out:nonthing
	public void ballmotorfowardbackward(double speed){
		if(speed>1.0){
			speed=1.0;
		}
		if(speed<-1.0){
			speed=-1.0;
		}
		m_ballmoter.set(speed);
	}
	
	//set conveyer motor
	public void setcaonvayeromotor(){
		m_convayeromoter = new Talon(5);
	}
				
	//set speed for conveyer motor
	//in:speedF
	//out:nonthing
	public void convayermotorforwardbackward(double speed){
		if(speed>1.0){
			speed=1.0;
		}
		if(speed<-1.0){
			speed=-1.0;
		}
		m_convayeromoter.set(speed);
	}
	
	//set climber motor
	public void setclimbmoter(){
		m_climbmoter = new Talon(6);
	}
	
	//set speed for climb motor
	//in:speed
	//out:nothing
	public void climbmotermovement(double speed){
		speed = speedLimit(speed);
		m_climbmoter.set(speed);
	}
	
	//set shoot motor
	public void setshootmoter(){
		m_shootmoter = new Talon(7);
	}
	
	//set shoot motor speed
	//in:speed
	//out:nothing
	public void shootmotorspeed(double speed){
		if(speed>1.0){
			speed=1.0;
		}
		if(speed<0){
			speed=0;
		}
		m_shootmoter.set(speed);
	}
	
	public boolean Testing;
	Timer timer = new Timer();
	
	//Start the Timer
	//in:nothing
	//out:nothing
	public boolean StartTest(){
		if(Testing = true){
		timer.reset();
		timer.start();
		}
		return true;
	}
	//Test the functions
	//in:Testing
	//out:Testing
	
	public void Test(){
		if(timer.get() < 1.0){
			DrivefowardBackward(0.5); //Forward .5
		}else if(timer.get() <2.0){
			DrivefowardBackward(0); //Stop
		}else if(timer.get() < 3.0){
			DrivefowardBackward(-0.5); //Back .5
		}else if(timer.get() < 4.0){
			DrivefowardBackward(0); //Stop
		}else if(timer.get() <5.0){
			turnRight(0.5,90);		//Turn Right .5, 90 degrees
		}else if(timer.get() <6.0){
			DrivefowardBackward(0); //Stop
		}else if(timer.get() <7.0){
			turnLeft(0.5,90);		//Turn Left .5, 90 degrees
		}else if(timer.get()==7.0){
			Testing=false;
			timer.reset();
		}
		 
		
	}
	
	
		
}
	

	 
 
	


