package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {
	private WPI_TalonSRX _elevatorMoter;
	private boolean setElevator = false;
	private boolean initElevator = false; 
	private Sensors upperSwitch;
	private Sensors lowerSwitch;
	private Sensors grabSwitch;
	private boolean setSwitches = false;
	private VictorSP _leftMoter;
	private VictorSP _rightMoter;
	private boolean  setMoters;
	
	private int timeoutMs = 10;
	private int pidIdx = 0;
	private int peakCurrent = 39;
	private int peakCurrentDur = 0;
	private int contCurrent = 37;
	private boolean upperSensorPressed = false;
	private boolean lowerSensorPressed = false;
	private boolean grabSensorPressed = false;
	
	private boolean raise = false;
	private boolean lower = false;
	private double raiseSpd = 0;
	private double lowerSpd = 0;
			
	
	//Set up Elevator motor
	//in:elevatorMoterIn
	//out:setElevator
	public boolean setElevator(WPI_TalonSRX elevatorMoterIn) {
		_elevatorMoter = elevatorMoterIn;
		setElevator = true;
		return setElevator;
	}
	
	//Set up Switches
	//in:uppperSwitchIn, lowerSwitchIn, grabSwitchIn
	//out:setSwitches
	public boolean setSwitches(DigitalInput upperSwitchIn, DigitalInput lowerSwitchIn, DigitalInput grabSwitchIn) {
		upperSwitch = new Sensors();
		lowerSwitch = new Sensors();
		grabSwitch = new Sensors();
		upperSwitch.limitswitch(upperSwitchIn);
		lowerSwitch.limitswitch(lowerSwitchIn);
		grabSwitch.limitswitch(grabSwitchIn);
		setSwitches = true;
		return setSwitches;
	}
	
	//Set the side motors of the elevator
	//in:_leftMoterIn, _rightMoterIn
	//out:setMoters
	public boolean setGrabMotors(VictorSP _leftMoterIn, VictorSP _rightMoterIn) {
		_leftMoter =_leftMoterIn;
		_rightMoter =_rightMoterIn;
		setMoters = true;
		return setMoters;
	}
	
	//Initialize the Elevator
	//in:nothing
	//out:initElevator
	public boolean initElevator() {
		if(!setElevator) {
			System.err.println("Error: Elevator Moter not set up yet.");
		}else {
			//Invert Motor
			_elevatorMoter.setInverted(false);
			_elevatorMoter.setSensorPhase(false);
			
			//Init Encoders
			_elevatorMoter.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
			
			// Set the peak and nominal outputs, 12V means full
			_elevatorMoter.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_elevatorMoter.configNominalOutputReverse(0, timeoutMs);
			_elevatorMoter.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_elevatorMoter.configPeakOutputReverse(-1, timeoutMs);
			
			// Current Limiting
			_elevatorMoter.configPeakCurrentLimit(peakCurrent, timeoutMs); /* 39 A */
			_elevatorMoter.configPeakCurrentDuration(peakCurrentDur, timeoutMs); /* 0ms */
			_elevatorMoter.configContinuousCurrentLimit(contCurrent, timeoutMs); /* 37A */
			_elevatorMoter.enableCurrentLimit(true); /* turn it on */
			
			// Init Sensor to zero
			_elevatorMoter.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs)
			
			// PID controls
			//TODO: Tune these
			_elevatorMoter.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_elevatorMoter.config_kF(0, 1.7 , timeoutMs); //(int slotIdx, double value, int timeoutMs)
			_elevatorMoter.config_kP(0, 0, timeoutMs);
			_elevatorMoter.config_kI(0, 0, timeoutMs);
			_elevatorMoter.config_kD(0, 0, timeoutMs);
			_elevatorMoter.config_IntegralZone(0, 0, timeoutMs);
			
			initElevator = true;
		}
		return initElevator;
	}
	
	//Raises the Elevator based on speed
	//in:speed
	//out:Nothing
	public void raiseElevator(double speed, boolean button) {
    	
		if(!setSwitches) {
			System.err.println("Error: Switches not set up.");
		}else if(!setElevator){
			System.err.println("Error: Elevator moter not set up.");
		}else if(!initElevator){
			System.err.println("Error: Elevator moter not initialized");
		}else {
			upperSensorPressed = upperSwitch.getstate();
			System.out.println("ele upper sen: " + upperSensorPressed);
			if(!upperSensorPressed) {
				_elevatorMoter.set(ControlMode.Velocity, speed);
				System.out.println("ele up: " + speed);
				if(button) {
					raise = true;
					lower = false;
					raiseSpd = speed;
				}
				else {
					raise = false;
					lower = false;
					raiseSpd = 0;
					lowerSpd = 0;
				}
			} else {
				_elevatorMoter.set(ControlMode.Velocity, 0);
				SmartDashboard.putBoolean("Elevator Max", true);
			}

		}	
    } 
    
    //Raises Elevator based on distance
    //in:Distance
    //out:nothing
    public boolean raiseElevatorDis(double dis) {
    	
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator){
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
    		upperSensorPressed = upperSwitch.getstate();
    		if(!upperSensorPressed) {
    			_elevatorMoter.set(ControlMode.Position, dis);
    			return true;
    		}
    		else { SmartDashboard.putBoolean("Elevator Max", true); return false; }   		
    	}
    	return false;
    }
    
    //Lower Elevator based on speed
    //in:speed
    //out:nothing
    public void lowerElevator (double speed, boolean button) {
   	
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator) {
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
    		lowerSensorPressed = lowerSwitch.getstate();
    		if(!lowerSensorPressed) {
    			_elevatorMoter.set(ControlMode.Velocity, -speed);
    			if(button) {
				lower = true;
				raise = false;
				lowerSpd = -speed;
			}
    			else {
    				raise = false;
    				lower = false;
    				raiseSpd = 0;
    				lowerSpd = 0;
    			}
    		} else {
    			_elevatorMoter.set(ControlMode.Velocity, 0);
    			SmartDashboard.putBoolean("Elevator Min", true);
    		}
    	}
    }
    
    //Lower Elevator based on Distance
    //in:nothing
    //out:nothing
    public boolean lowerElevatorDis(double dis) {
    	
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator) {
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
    		lowerSensorPressed = lowerSwitch.getstate();
    		if(!lowerSensorPressed) {
    			_elevatorMoter.set(ControlMode.Position, dis);
    			return true;
    		}
    		else { SmartDashboard.putBoolean("Elevator Min", true); return false;}
    	}
    	return false;
    }
    
    //Method that grabs the block
    //in:speed
    //out:nothing
    public void grabBlock(double speed) {
    	
    	if(!setMoters) {
    		System.err.println("Error: Grabbing moters are not set up");
    	}else if(!setSwitches){
    		System.err.println("Error: Grab Switch not set up");
    	}else {
    		grabSensorPressed = grabSwitch.getstate();
    		if(!grabSensorPressed){
    			_leftMoter.setSpeed(-speed);
    			_rightMoter.setSpeed(-speed);		
    		}else {
    			_leftMoter.setSpeed(0);
    			_rightMoter.setSpeed(0);
    			SmartDashboard.putBoolean("Block Grabbed", true);
    		}
    	}
    }
    
    //Ejects the block
    //in:speed
    //out:nothing
    public void ejectBlock(double speed) {
    	
    	if(!setMoters) {
    		System.err.println("Error: Grabbing moters are not set up.");
    	}else if(!setSwitches){
    		System.err.println("Error: Grab Switch not set up");
    	}else {    		
    		_leftMoter.setSpeed(speed);
    		_rightMoter.setSpeed(speed);
    	}
    }  
    
    public void checkSwitches(boolean switchOverride) {
    	
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator){
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
	    	lowerSensorPressed = lowerSwitch.getstate(); 
			upperSensorPressed = upperSwitch.getstate();
			
			//TODO: Determine encoder count for max elevator position
			if(upperSensorPressed) { SmartDashboard.putBoolean("Elevator Max", true); raise = false; lower = false; }
			if(lowerSensorPressed) { SmartDashboard.putBoolean("Elevator Min", true); lower = false; raise = false; } 
	
			if ((!upperSensorPressed && !switchOverride) && (raise && !lower)) { 
				_elevatorMoter.set(ControlMode.Velocity, raiseSpd);
			}else if((!lowerSensorPressed && !switchOverride) && (lower && !raise)) { 
				_elevatorMoter.set(ControlMode.Velocity, lowerSpd); 
			}else {
				_elevatorMoter.set(ControlMode.Velocity, 0);
				raise = false;
				lower = false;		
			}	  	
    	}
    }
}
