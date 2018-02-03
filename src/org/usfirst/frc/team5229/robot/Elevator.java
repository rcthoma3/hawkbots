package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.VictorSP;

public class Elevator {
	private WPI_TalonSRX _elevatorMoter;
	private boolean setElevator = false;
	private boolean initElevator = false; 
	private Sensors upperSwitch;
	private Sensors lowerSwitch;
	private boolean setSwitches = false;
	private PWM _leftMoter;
	private PWM _rightMoter;
	private boolean  setMoters;
	private Sensors grabSwitch;
	private int timeoutMs = 10;
	private int pidIdx = 0;
	
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
		upperSwitch.limitswitch(upperSwitchIn);
		lowerSwitch.limitswitch(lowerSwitchIn);
		grabSwitch.limitswitch(grabSwitchIn);
		setSwitches = true;
		return setSwitches;
	}
	
	//Set the side motors of the elevator
	//in:_leftMoterIn, _rightMoterIn
	//out:setMoters
	public boolean setMoters(int _leftMoterIn, int _rightMoterIn) {
		_leftMoter = new VictorSP(_leftMoterIn);
		_rightMoter = new VictorSP(_rightMoterIn);
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
    public void raiseElevator(double speed) {
    	boolean sensorpressed = upperSwitch.getstate();
    	
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator){
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
    		if(!sensorpressed) {
    			_elevatorMoter.set(ControlMode.Velocity, speed);
    		} else {
    			_elevatorMoter.set(ControlMode.Velocity, speed);
    		}
    		
    	}
    		
    } 
    
    //Raises Elevator based on distance
    //in:Distance
    //out:nothing
    public void raiseElevatorDis(double dis) {
    	boolean sensorpressed = upperSwitch.getstate();
    	
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator){
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
    		if(!sensorpressed) {
    			_elevatorMoter.set(ControlMode.Position, dis);
    		}
    		
    	}
    }
    
    //Lower Elevator based on speed
    //in:speed
    //out:nothing
    public void lowerElevator (double speed) {
    	boolean sensorpressed = lowerSwitch.getstate();
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator) {
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
    		if(!sensorpressed) {
    			_elevatorMoter.set(ControlMode.Velocity, -speed);
    			
    		} else {
    			_elevatorMoter.set(ControlMode.Velocity, 0);
    		}
    	}
    }
    
    //Lower Elevator based on Distance
    //in:nothing
    //out:nothing
    public void lowerElevatorDis(double dis) {
    	boolean sensorpressed = lowerSwitch.getstate();
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else if(!setElevator){
    		System.err.println("Error: Elevator moter not set up.");
    	}else if(!initElevator) {
    		System.err.println("Error: Elevator moter not initialized");
    	}else {
    		if(!sensorpressed) {
    			_elevatorMoter.set(ControlMode.Position, dis);
    			
    		}
    	}
    }
    
    //Method that grabs the block
    //in:speed
    //out:nothing
    public void grabBlock(double speed) {
    	boolean sensorpressed = grabSwitch.getstate();
    	if(!setMoters) {
    		System.err.println("Error: Grabbing moters are not set up");
    	}else if(!setSwitches){
    		System.err.println("Error: Grab Switch not set up");
    	}else {
    		if(!sensorpressed){
    			_leftMoter.setSpeed(speed);
    			_rightMoter.setSpeed(-speed);		
    		}else {
    			_leftMoter.setSpeed(0);
    			_rightMoter.setSpeed(0);
    		}
    	}
    }
    
    //Ejects the block
    //in:speed
    //out:nothing
    public void ejectBlock(double speed) {
    	boolean sensorpressed = grabSwitch.getstate();
    	if(!setMoters) {
    		System.err.println("Error: Grabbing moters are not set up.");
    	}else if(!setSwitches){
    		System.err.println("Error: Grab Switch not set up");
    	}else {
    		if(!sensorpressed) {
    			_leftMoter.setSpeed(-speed);
    			_rightMoter.setSpeed(speed);
    		}else {
    			_leftMoter.setSpeed(0);
    			_rightMoter.setSpeed(0);
    		}
    	}
    }    
}
