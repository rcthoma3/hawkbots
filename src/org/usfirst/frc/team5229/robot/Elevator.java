package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {
	private WPI_TalonSRX _elevatorMotor;
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
	private double raiseDis = 0;
	private double lowerDis = 0;
	private int moveRange = 20;
			
	
	//Set up Elevator motor
	//in:elevatorMoterIn
	//out:setElevator
	public boolean setElevator(WPI_TalonSRX elevatorMoterIn) {
		_elevatorMotor = elevatorMoterIn;
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
			_elevatorMotor.setInverted(false);
			_elevatorMotor.setSensorPhase(true);
			
			//Init Encoders
			_elevatorMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
			
			// Set the peak and nominal outputs, 12V means full
			_elevatorMotor.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_elevatorMotor.configNominalOutputReverse(0, timeoutMs);
			_elevatorMotor.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_elevatorMotor.configPeakOutputReverse(-1, timeoutMs);
			
			// Current Limiting
			_elevatorMotor.configPeakCurrentLimit(peakCurrent, timeoutMs); /* 39 A */
			_elevatorMotor.configPeakCurrentDuration(peakCurrentDur, timeoutMs); /* 0ms */
			_elevatorMotor.configContinuousCurrentLimit(contCurrent, timeoutMs); /* 37A */
			_elevatorMotor.enableCurrentLimit(true); /* turn it on */
			
			// Init Sensor to zero
			_elevatorMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs)
			
			// PID controls
			//TODO: Tune these
			_elevatorMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_elevatorMotor.config_kF(0, 1.7 , timeoutMs); //(int slotIdx, double value, int timeoutMs)
			_elevatorMotor.config_kP(0, 0, timeoutMs);
			_elevatorMotor.config_kI(0, 0, timeoutMs);
			_elevatorMotor.config_kD(0, 0, timeoutMs);
			_elevatorMotor.config_IntegralZone(0, 0, timeoutMs);
			
			initElevator = true;
		}
		return initElevator;
	}
	
	public boolean getElevatorTop() {
		int pos = _elevatorMotor.getSelectedSensorPosition(0);
		if(pos >= 71000) {return true;}
		else {return false;}
	}
	
	public boolean getElevatorBottom() {
		int pos = _elevatorMotor.getSelectedSensorPosition(0);
		if(pos <= 20) {return true;}
		else {return false;}
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
			int pos = _elevatorMotor.getSelectedSensorPosition(0);
			if(!upperSensorPressed ) { //71000
				//_elevatorMotor.set(ControlMode.Velocity, speed);
				_elevatorMotor.set(1);
				System.out.println("ele up: " + speed);
				if(button) {
					raise = true;
					lower = false;
				}
				else {
					raise = false;
					lower = false;
				}
			} else {
				_elevatorMotor.set(ControlMode.Velocity, 0);
				
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
    			if (Math.abs(_elevatorMotor.getSelectedSensorPosition(0) - dis) > moveRange) {
    			raiseDis = dis;
    			_elevatorMotor.set(ControlMode.MotionMagic, dis);
    			raise = true;
    			lower = false;
    			return true;
    			}
    		}
    		else {  return false; }   		
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
    		int pos = _elevatorMotor.getSelectedSensorPosition(0);
    		if(!lowerSensorPressed) {
    			//_elevatorMotor.set(ControlMode.Velocity, -speed);
    			_elevatorMotor.set(-1);
    			if(button) {
				lower = true;
				raise = false;
			}
    			else {
    				raise = false;
    				lower = false;
    			}
    		} else {
    			_elevatorMotor.set(ControlMode.Velocity, 0);
    			
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
    			if (Math.abs(_elevatorMotor.getSelectedSensorPosition(0) - dis) > moveRange) {
    				lowerDis = dis;
    			_elevatorMotor.set(ControlMode.MotionMagic, dis);
    			raise = false;
    			lower = true;
    			return true;
    			}
    		}
    		else {  return false;}
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
			
			if ((!upperSensorPressed && !switchOverride) && (raise && !lower) && (Math.abs(_elevatorMotor.getSelectedSensorPosition(0) - raiseDis) > moveRange)) { 
				_elevatorMotor.set(ControlMode.MotionMagic, raiseDis);
			}else if((!lowerSensorPressed && !switchOverride) && (lower && !raise) && (Math.abs(_elevatorMotor.getSelectedSensorPosition(0) - lowerDis) > moveRange)) {
				_elevatorMotor.set(ControlMode.MotionMagic, lowerDis); 
			}else {
				_elevatorMotor.set(ControlMode.Velocity, 0);
				raise = false;
				lower = false;		
			}	  	
    	}
    }
    
    public boolean getTopSwitch() {
    	return upperSwitch.getstate();
    }
    public boolean getBottomSwitch() {
    	return lowerSwitch.getstate();
    }
}
