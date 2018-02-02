package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWM;
public class Elevator {
	private WPI_TalonSRX _elevatorMoter;
	private boolean setElevator = false;
	private boolean inElevator = false; 
	public Sensors upperSwitch;
	public Sensors lowerSwitch;
	private boolean setSwitches = false;
	private PWM _leftMoter;
	private PWM _rightMoter;
	private boolean  setMoters;
	
	public boolean setElevator(WPI_TalonSRX elevatorMoterIn) {
		_elevatorMoter = elevatorMoterIn;
		setElevator = true;
		return setElevator;
	}
	
	public boolean setSwitches(int upperSwitchIn, int lowerSwitchIn) {
		upperSwitch.limitswitch(upperSwitchIn);
		lowerSwitch.limitswitch(lowerSwitchIn);
		setSwitches = true;
		return setSwitches;
	}
	
	public boolean setMoters(PWM _leftMoterIn, PWM _rightMoterIn) {
		_leftMoter = _leftMoterIn;
		_rightMoter = _rightMoterIn;
		setMoters = true;
		return setMoters;
	}
	
	
	public boolean inElevator() {
		if(!setElevator) {
			System.err.println("Error: Elevator Moter not set up yet.");
		}else {
			inElevator = true;
		}
		return inElevator;
	}
	
    public void raiseElevator(double speed) {
    	boolean sensorpressed = upperSwitch.getstate();
    	
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else {
    		while(sensorpressed) {
    			_elevatorMoter.set(speed);
    		}
    		
    	}
    		
    }
    
    public void lowerElevator (double speed) {
    	boolean sensorpressed = lowerSwitch.getstate();
    	if(!setSwitches) {
    		System.err.println("Error: Switches not set up.");
    	}else {
    		while(sensorpressed) {
    			_elevatorMoter.set(speed);
    			
    		}
    	}
    }
    
    public void grabBlock(double speed) {
    	if(!setMoters) {
    		System.err.println("Error: Grabbing moters are not set up");
    	}else {
    		_leftMoter.setSpeed(speed);
    		_rightMoter.setSpeed(-speed);
    	}
    }
    
    public void ejectBlock(double speed) {
    	if(!setMoters) {
    		System.err.println("Error: Grabbing moters are not set up.");
    	}else {
    		_leftMoter.setSpeed(-speed);
    		_rightMoter.setSpeed(speed);
    	}
    }
    
    
}
