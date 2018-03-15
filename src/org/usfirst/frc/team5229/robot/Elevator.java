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
	private WPI_TalonSRX _liftMoter; //This motor should lift the claw up
	private WPI_TalonSRX _extendLeftMoter; //This motor should extend the left claw
	private WPI_TalonSRX _extendRightMoter;//This motor should extend the right claw
	private int timeoutMs = 10;
	private int pidIdx = 0;
	private int peakCurrent = 39;
	private int peakCurrentDur = 0;
	private int contCurrent = 37;
	private boolean upperSensorPressed = false;
	private boolean lowerSensorPressed = false;
	private boolean grabSensorPressed = false;
	private boolean setClawMoters = false;
	private boolean initClawMoters = false;
	private boolean raise = false;
	private boolean lower = false;
	private double raiseSpd = 0;
	private double lowerSpd = 0;
			
	
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
	
	//Set up the new motors for the claws
	//in:_liftMoterIn, _extendRightMoterIn, _extendLeftMoterIn
	//out:setClawMoters
	public boolean setClawMotors(WPI_TalonSRX _liftMoterIn ,  WPI_TalonSRX _extendRightMoterIn, WPI_TalonSRX _extendLeftMoterIn ) {
		_liftMoter = _liftMoterIn;
		_extendRightMoter = _extendRightMoterIn;
		_extendLeftMoter = _extendLeftMoterIn;
		setClawMoters = true;
		return setClawMoters;
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
			_elevatorMotor.setSensorPhase(false);
			
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
	
	//Initialize Claw Motors
	//in:nothing
	//out:initClawMoters
	public boolean initClawMoters() {
		if(!setClawMoters) {
			System.err.println("Error : Claw Motors are not set up yet");
		}else {
			//Invert Motor
			_liftMoter.setInverted(false);
			_liftMoter.setSensorPhase(false);
			
			//Init Encoders
			_liftMoter.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
			
			// Set the peak and nominal outputs, 12V means full
			_liftMoter.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_liftMoter.configNominalOutputReverse(0, timeoutMs);
			_liftMoter.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_liftMoter.configPeakOutputReverse(-1, timeoutMs);
			
			// Current Limiting
			_liftMoter.configPeakCurrentLimit(peakCurrent, timeoutMs); /* 39 A */
			_liftMoter.configPeakCurrentDuration(peakCurrentDur, timeoutMs); /* 0ms */
			_liftMoter.configContinuousCurrentLimit(contCurrent, timeoutMs); /* 37A */
			_liftMoter.enableCurrentLimit(true); /* turn it on */
			
			// Init Sensor to zero
			_liftMoter.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs)
			
			// PID controls
			//TODO: Tune these
			_liftMoter.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_liftMoter.config_kF(0, 1.7 , timeoutMs); //(int slotIdx, double value, int timeoutMs)
			_liftMoter.config_kP(0, 0, timeoutMs);
			_liftMoter.config_kI(0, 0, timeoutMs);
			_liftMoter.config_kD(0, 0, timeoutMs);
			_liftMoter.config_IntegralZone(0, 0, timeoutMs);
			
			//Invert Motor
			_extendRightMoter.setInverted(false);
			_extendRightMoter.setSensorPhase(false);
			
			//Init Encoders
			_extendRightMoter.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
			
			// Set the peak and nominal outputs, 12V means full
			_extendRightMoter.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_extendRightMoter.configNominalOutputReverse(0, timeoutMs);
			_extendRightMoter.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_extendRightMoter.configPeakOutputReverse(-1, timeoutMs);
			
			// Current Limiting
			_extendRightMoter.configPeakCurrentLimit(peakCurrent, timeoutMs); /* 39 A */
			_extendRightMoter.configPeakCurrentDuration(peakCurrentDur, timeoutMs); /* 0ms */
			_extendRightMoter.configContinuousCurrentLimit(contCurrent, timeoutMs); /* 37A */
			_extendRightMoter.enableCurrentLimit(true); /* turn it on */
			
			// Init Sensor to zero
			_extendRightMoter.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs)
			
			// PID controls
			//TODO: Tune these
			_extendRightMoter.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_extendRightMoter.config_kF(0, 1.7 , timeoutMs); //(int slotIdx, double value, int timeoutMs)
			_extendRightMoter.config_kP(0, 0, timeoutMs);
			_extendRightMoter.config_kI(0, 0, timeoutMs);
			_extendRightMoter.config_kD(0, 0, timeoutMs);
			_extendRightMoter.config_IntegralZone(0, 0, timeoutMs);
			
			//Invert Motor
			_extendLeftMoter.setInverted(false);
			_extendLeftMoter.setSensorPhase(false);
			
			//Init Encoders
			_extendLeftMoter.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
			
			// Set the peak and nominal outputs, 12V means full
			_extendLeftMoter.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_extendLeftMoter.configNominalOutputReverse(0, timeoutMs);
			_extendLeftMoter.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_extendLeftMoter.configPeakOutputReverse(-1, timeoutMs);
			
			// Current Limiting
			_extendLeftMoter.configPeakCurrentLimit(peakCurrent, timeoutMs); /* 39 A */
			_extendLeftMoter.configPeakCurrentDuration(peakCurrentDur, timeoutMs); /* 0ms */
			_extendLeftMoter.configContinuousCurrentLimit(contCurrent, timeoutMs); /* 37A */
			_extendLeftMoter.enableCurrentLimit(true); /* turn it on */
			
			// Init Sensor to zero
			_extendLeftMoter.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs)
			
			// PID controls
			//TODO: Tune these
			_extendLeftMoter.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_extendLeftMoter.config_kF(0, 1.7 , timeoutMs); //(int slotIdx, double value, int timeoutMs)
			_extendLeftMoter.config_kP(0, 0, timeoutMs);
			_extendLeftMoter.config_kI(0, 0, timeoutMs);
			_extendLeftMoter.config_kD(0, 0, timeoutMs);
			_extendLeftMoter.config_IntegralZone(0, 0, timeoutMs);
			
			initClawMoters = true;
		}
		return initClawMoters;
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
				_elevatorMotor.set(ControlMode.Velocity, speed);
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
				_elevatorMotor.set(ControlMode.Velocity, 0);
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
    			_elevatorMotor.set(ControlMode.Position, dis);
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
    			_elevatorMotor.set(ControlMode.Velocity, -speed);
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
    			_elevatorMotor.set(ControlMode.Velocity, 0);
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
    			if (_elevatorMotor.getSelectedSensorPosition(0) > 0)
    			_elevatorMotor.set(ControlMode.Position, dis);
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
    
    public void liftBlock() {
    	if(!setClawMoters) {
    		System.err.println("Error : Claw Lift Moter not set up");
    	}else if(!initClawMoters) {
    		System.err.println("Error : Claw Lift Moter not initialize");
    	}else {
    		
    	}
    }
    
    public void lowerBlock() {
    	if(!setClawMoters) {
    		System.err.println("Error : Claw Lift Moter not set up");
    	}else if(!initClawMoters) {
    		System.err.println("Error : Claw Lift Moter not initialize");
    	}else {
    		
    	}
    }
    
    public void extendClaws() {
    	if(!setClawMoters) {
    		System.err.println("Error : Extend Motors not set up");
    	}else if(!setClawMoters) {
    		System.err.println("Error : Extend Motors not initialize");
    	}else {
    		
    	}
    }
    
    public void closeClaws() {
    	if(!setClawMoters) {
    		System.err.println("Error : Extend Motors not set up");
    	}else if(!setClawMoters) {
    		System.err.println("Error : Extend Motors not initialize");
    	}else {
    		
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
				_elevatorMotor.set(ControlMode.Velocity, raiseSpd);
			}else if((!lowerSensorPressed && !switchOverride) && (lower && !raise)) { 
				_elevatorMotor.set(ControlMode.Velocity, lowerSpd); 
			}else {
				_elevatorMotor.set(ControlMode.Velocity, 0);
				raise = false;
				lower = false;		
			}	  	
    	}
    }
}
