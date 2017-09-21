package org.usfirst.frc.team5229.robot;


import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;

public class RobotMovement {
	private boolean modeArcade = true; //change modes Arcade and Tank
	private boolean modeFine = true;  //change modes fine and coarse
    private boolean squaredInputs = true; //make mode fine
    //private static double Track = 24.0;//distance between center of wheels of each side or robot
    public boolean Testing;// start test function
	Timer timer = new Timer();//timer for testing
	protected SpeedController m_ballmoter;//motor for opening ball entrance
	protected SpeedController m_convayeromoter;//motor for moving balls
	protected SpeedController m_climbmoter;//motor for climbing rope
	protected SpeedController m_shootmoter;//motor for shooting balls
	public boolean ConvayerSwitch=true;//Turn on or off conveyer
	public boolean BallSwitch = false;//Open or close ball entrance
	public boolean climbing = false; //turns on and off the climbing motor
	RobotDrive myRobot = new RobotDrive(2,3,0,1); // why is there 0, 1, 2, 3? What are those?
    Controller myController;//set controller
    
    private double r = 0;
    private double speed = .5;
    
    private static final int DOOR_MOTOR = 4;
    private static final int CLIMB_MOTOR = 5;
    private static final int CONVAYER_MOTOR = 6;
    private static final int BALL_MOTOR = 7;
    
    
    
    //Initialize states
    private enum State {
		STOPPED, FORWARD, BACKWARD, LEFT, RIGHT, CLIMBING, DESCENDING
	}
 
	State state = State.STOPPED;
	private VictorSP m_ballmotor;
	private VictorSP m_conveyormotor;
	public boolean conveyorSwitch;
	private PWM m_climbmotor;
	private VictorSP m_doormotor;
	
	//a constructor that doesn't take an input
	public RobotMovement(){
		m_climbmotor = new VictorSP(CLIMB_MOTOR);
		m_ballmotor = new VictorSP(BALL_MOTOR);		
		m_doormotor = new VictorSP(DOOR_MOTOR);
		m_conveyormotor = new VictorSP(CONVAYER_MOTOR);		
	}
	
	//in:Controller myController, wheelbase
	//out: nothing
	public RobotMovement(Controller myController){
		this();
		this.myController = myController;		
	}
	
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
			climbmotormovement(speed);
			break;
		case DESCENDING:
			climbmotormovement(speed);
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
	

	//should all of the motors be inverted?
	//make motors inverted
	public void init(){
		myRobot.setInvertedMotor(RobotDrive.MotorType.kFrontLeft,true);
		myRobot.setInvertedMotor(RobotDrive.MotorType.kFrontRight,true);
		myRobot.setInvertedMotor(RobotDrive.MotorType.kRearLeft,true);
		myRobot.setInvertedMotor(RobotDrive.MotorType.kRearRight,true);
	} 
   
    //set drive forward and backward//
    //in: speed
    //out: nothing
    //speed>0, moves forward
    //speed<0, moves backward
    public void DrivefowardBackward(double speed){
    	speed = speedLimit(speed);
    	myRobot.drive(-speed,-.01);   	// WHY is there a -? Should motors be reversed?
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
    
    //in:distance that the robot needs to move
    //out:number of rotations of the wheel needed to travel that distance
//    public double distanceToRot(double dist){
//    	return dist/(wheelDiameter*Math.PI);
//    }
    
    //turn left
    //in: speed, radius(r)
    //out: nothing
    public void turnLeft(double speed, double r){
    	speed = speedLimit(speed);
    	if (r<0){
    		r=0;
    	}
    	if(r>180){
    		r=180;
    	}
    	//myRobot.drive(speed, rToCurve(r));
    	myRobot.drive(speed,1);
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
    	myRobot.drive(speed, -1);
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
		//myRobot.setMaxOutput(.3);
		//myRobot.setSensitivity(.3);
		if(modeArcade == true && modeFine == true){
	    	myRobot.setMaxOutput(.4);
	    	myRobot.setSensitivity(.4);
	    	myRobot.arcadeDrive(myController.stick, squaredInputs);
	    }else if(modeArcade == false && modeFine == true){
	    	myRobot.setMaxOutput(.4);
	    	myRobot.setSensitivity(.4);
	    	myRobot.tankDrive(myController.getLeftJoyY(), myController.getRightJoyY(), squaredInputs);
	    	
	    }else if(modeArcade == true && modeFine == false){
	    	myRobot.setMaxOutput(.4);
	    	myRobot.setSensitivity(.4);
	    	myRobot.arcadeDrive(myController.stick, squaredInputs);
	    	
	    }else{
	    	myRobot.setMaxOutput(.4);
	    	myRobot.setSensitivity(.4);
	    	myRobot.tankDrive(myController.getLeftJoyY(), myController.getRightJoyY(), squaredInputs); 	    	
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
		

  
    
    //turn on ball motor
    //in:nothing
    //out:nothing
    public void BallOn(){
    	BallSwitch=true;
    }
    //turn off ball motor
    //in:nothing
    //out:nothing
    public void BallOff(){
    	BallSwitch=false;
    }
    //speed of ball motor is set//
    //in:ballspeed
    //out:nonthing
	public void ballmotorwork(){
		if(BallSwitch==true){
			m_ballmotor.set(1.0);
		}
	}
	
	//turn on conveyer motor
	//in:nothing
	//out:nothing
	public void ConveyerOn(){
		conveyorSwitch = true;
	}
	
	//turn off conveyer motor
	//in:nothing
	//out:nothing
	public void ConveyerOff(){
		conveyorSwitch = false;
	}
				
	//set speed for conveyer motor
	//in:speed
	//out:nonthing
    public void coveyormotorwork(){
    	if(conveyorSwitch == true){
    		m_conveyormotor.set(-1.0);
    	} else {
    		m_conveyormotor.set(0);
    	}
    }
	
    public boolean ballon(){
    	return BallSwitch;
    }
    
    public boolean balloff(){
    	return BallSwitch;
    }
    
    public boolean conveyor(){
    	return conveyorSwitch;
    }
	
	//set speed for climb motor
	//in:speed
	//out:nothing
	public void climbmotormovement(double speed){
		//speed = speedLimit(speed);
		m_climbmotor.setSpeed(speed);
		
	}
	
	//set shoot motor speed
	//in:speed
	//out:nothing
	public void setDoorMotorSpeed(double speed){
		if(speed>1.0){
			speed=1.0;
		}
		if(speed<-1){
			speed=-1;
		}
		m_doormotor.set(speed);
	}
	
	
	
	
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
			turnLeft(0,90);
		}else if(timer.get() == 8.0){
			DrivefowardBackward(0);
			Testing = false;
			timer.reset();
		}/*else if(timer.get() < 9.0){
			ConveyerOn();
			coveyormotorwork();
		}else if(timer.get() < 10.0){
			ConveyerOff();
		}else if(timer.get() < 11.0){
			BallOn();
			ballmotorwork();
		}else if(timer.get() == 12.0){
			BallOff();
			Testing = false;
            timer.reset();
		}*/
	
	}
	
		
}