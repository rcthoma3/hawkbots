package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
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
	
	//TODO: Add Comments
	public boolean setElevator(WPI_TalonSRX elevatorMoterIn) {
		_elevatorMoter = elevatorMoterIn;
		// TODO: Initialize to zero
		setElevator = true;
		return setElevator;
	}
	
	//TODO: Add Comments
	public boolean setSwitches(int upperSwitchIn, int lowerSwitchIn, int grabSwitchIn) {
		upperSwitch.limitswitch(upperSwitchIn);
		lowerSwitch.limitswitch(lowerSwitchIn);
		grabSwitch.limitswitch(grabSwitchIn);
		setSwitches = true;
		return setSwitches;
	}
	
	//TODO: Add Comments
	public boolean setMoters(int _leftMoterIn, int _rightMoterIn) {
		_leftMoter = new VictorSP(_leftMoterIn);
		_rightMoter = new VictorSP(_rightMoterIn);
		setMoters = true;
		return setMoters;
	}
	
	//TODO: Add Comments
	public boolean initElevator() {
		if(!setElevator) {
			System.err.println("Error: Elevator Moter not set up yet.");
		}else {
			// TODO: Init Encoders
			// TODO: Set the peak and nominal outputs, 12V means full
			// TODO: Init Sensor to zero
			// TODO: PID controls
			initElevator = true;
		}
		return initElevator;
	}
	
	//TODO: Add Comments
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
    		} //TODO: Add else to set speed to zero
    		
    	}
    		
    } 
    
    //TODO: Add Comments
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
    
    //TODO: Add Comments
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
    			
    		} //TODO: Added else to set speed to zero
    	}
    }
    
    //TODO: Add Comments
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
    
    //TODO: Add Comments
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
    
    //TODO: Add Comments
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
