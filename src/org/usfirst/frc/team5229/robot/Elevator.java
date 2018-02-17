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
			_elevatorMoter.setInverted(true);
			_elevatorMoter.setSensorPhase(false);
			
			//Init Encoders
			_elevatorMoter.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
			
			// Set the peak and nominal outputs, 12V means full
			_elevatorMoter.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_elevatorMoter.configNominalOutputReverse(0, timeoutMs);
			_elevatorMoter.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_elevatorMoter.configPeakOutputReverse(-1, timeoutMs);
			
			// Init Sensor to zero
			_elevatorMoter.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs) 
			// PID controls
			//Once ElevatorMoter is made, fix the PID Controls
			_elevatorMoter.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_elevatorMoter.config_kF(0, 0.3, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_elevatorMoter.config_kP(0, 3.0, timeoutMs);
			_elevatorMoter.config_kI(0, 0.03, timeoutMs);
			_elevatorMoter.config_kD(0, 30, timeoutMs);
			
			initElevator = true;
		}
		return initElevator;
	}
	
	//Raises the Elevator based on speed
	//in:speed
	//out:Nothing
    public void raiseElevator(double speed, boolean button) {
    	upperSensorPressed = upperSwitch.getstate();
    	
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator){
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
    		if(!upperSensorPressed) {
    			_elevatorMoter.set(ControlMode.Velocity, speed);
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
    public void raiseElevatorDis(double dis) {
    	upperSensorPressed = upperSwitch.getstate();
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator){
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
    		if(!upperSensorPressed) {
    			_elevatorMoter.set(ControlMode.Position, dis);
    		}
    		else {SmartDashboard.putBoolean("Elevator Max", true);}
    		
    	}
    }
    
    //Lower Elevator based on speed
    //in:speed
    //out:nothing
    public void lowerElevator (double speed, boolean button) {
    	lowerSensorPressed = lowerSwitch.getstate();
    	lowerSpd = speed;
    	
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator) {
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
    		if(!lowerSensorPressed) {
    			_elevatorMoter.set(ControlMode.Velocity, -speed);
    			if(button) {
					lower = true;
					raise = false;
					lowerSpd = speed;
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
    public void lowerElevatorDis(double dis) {
    	lowerSensorPressed = lowerSwitch.getstate();
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator) {
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
    		if(!grabSensorPressed) {
    			_elevatorMoter.set(ControlMode.Position, dis);			
    		}
    		else { SmartDashboard.putBoolean("Elevator Min", true); }
    	}
    }
    
    //Method that grabs the block
    //in:speed
    //out:nothing
    public void grabBlock(double speed) {
    	grabSensorPressed = grabSwitch.getstate();
    	if(!setMoters) {
    		System.err.println("Error: Grabbing moters are not set up");
    	}else if(!setSwitches){
    		System.err.println("Error: Grab Switch not set up");
    	}else {
    		if(!grabSensorPressed){
    			_leftMoter.setSpeed(speed);
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
			_leftMoter.setSpeed(-speed);
			_rightMoter.setSpeed(speed);
    	}
    }  
    
    public void checkSwitches(boolean switchOverride) {
    	lowerSensorPressed = lowerSwitch.getstate(); 
		upperSensorPressed = upperSwitch.getstate();
    	
    	if (upperSensorPressed || switchOverride) {
    		_elevatorMoter.set(ControlMode.Velocity, 0);
    		raise = false;
    	}
    	
    	else if (!lower && raise) {
    		 _elevatorMoter.set (ControlMode.Velocity, raiseSpd);
    	}
    	
    	if(lowerSensorPressed || switchOverride) {
    		_elevatorMoter.set (ControlMode.Velocity, 0);
    		lower = false;
    		
    	}
    	
    	else if (!lower && raise) {
       		 _elevatorMoter.set (ControlMode.Velocity, -lowerSpd);
       	
    	}
    	
    }
    
    
    
    
    
    
    
}
