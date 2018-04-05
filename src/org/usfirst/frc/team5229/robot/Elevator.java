package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.AnalogTrigger;
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
	private VictorSP _leftWheelMotor;
	private VictorSP _rightWheelMotor;
	private WPI_TalonSRX _tiltMotor; //This motor should lift the claw up
	private WPI_TalonSRX _leftClawMotor; //This motor should extend the left claw
	private WPI_TalonSRX _rightClawMotor;//This motor should extend the right claw
	private boolean  setMotors;
	//private AnalogTrigger clawTiltTrigger;
	//private AnalogTrigger rightClawArmTrigger;
	//private AnalogTrigger leftClawArmTrigger;
	
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
	private double raiseDis = 0;
	private double lowerDis = 0;
	private int moveRange = 20;
	private int rightClawCnt = 0;
	private int leftClawCnt = 0;
	private int tiltCnt = 0;
	private boolean tiltSenPrev;
	private boolean rightClawSenPrev;
	private boolean leftClawSenPrev;
			
	
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
		_leftWheelMotor =_leftMoterIn;
		_rightWheelMotor =_rightMoterIn;
		setMotors = true;
		return setMotors;
	}
	
	//Set up the new motors for the claws
	//in:_tiltMoterIn, _rightClawMoterIn, _leftClawMoterIn
	//out:setClawMoters
	public boolean setClawMotors(WPI_TalonSRX _tiltMotorIn ,  WPI_TalonSRX _rightClawMotorIn, WPI_TalonSRX _leftClawMotorIn ) {
		_tiltMotor = _tiltMotorIn;
		_rightClawMotor = _rightClawMotorIn;
		_leftClawMotor = _leftClawMotorIn;
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
	
	//Initialize Claw Motors
	//in:nothing
	//out:initClawMoters
	public boolean initClawMotors() {
		if(!setClawMoters) {
			System.err.println("Error : Claw Motors are not set up yet");
		}else {
			
			
			//Invert Motor
			_tiltMotor.setInverted(false);
			_tiltMotor.setSensorPhase(false);
			
			//Init Encoders
			_tiltMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
			
			// Set the peak and nominal outputs, 12V means full
			_tiltMotor.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_tiltMotor.configNominalOutputReverse(0, timeoutMs);
			_tiltMotor.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_tiltMotor.configPeakOutputReverse(-1, timeoutMs);
			
			// Current Limiting
			_tiltMotor.configPeakCurrentLimit(11, timeoutMs); 
			_tiltMotor.configPeakCurrentDuration(peakCurrentDur, timeoutMs); /* 0ms */
			_tiltMotor.configContinuousCurrentLimit(10, timeoutMs); 
			_tiltMotor.enableCurrentLimit(true); /* turn it on */
			
			// Init Sensor to zero
			_tiltMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs)
			
			// PID controls
			//TODO: Tune these
			_tiltMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_tiltMotor.config_kF(0, 0.5 , timeoutMs); //(int slotIdx, double value, int timeoutMs)
			_tiltMotor.config_kP(0, 0, timeoutMs);
			_tiltMotor.config_kI(0, 0, timeoutMs);
			_tiltMotor.config_kD(0, 0, timeoutMs);
			_tiltMotor.config_IntegralZone(0, 0, timeoutMs);
			
			//Invert Motor
			_rightClawMotor.setInverted(false);
			_rightClawMotor.setSensorPhase(false);
			
			//Init Encoders
			_rightClawMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
			
			// Set the peak and nominal outputs, 12V means full
			_rightClawMotor.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_rightClawMotor.configNominalOutputReverse(0, timeoutMs);
			_rightClawMotor.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_rightClawMotor.configPeakOutputReverse(-1, timeoutMs);
			
			// Current Limiting
			_rightClawMotor.configPeakCurrentLimit(7, timeoutMs);
			_rightClawMotor.configPeakCurrentDuration(peakCurrentDur, timeoutMs); /* 0ms */
			_rightClawMotor.configContinuousCurrentLimit(6, timeoutMs);
			_rightClawMotor.enableCurrentLimit(true); /* turn it on */
			
			// Init Sensor to zero
			_rightClawMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs)
			
			// PID controls
			//TODO: Tune these
			_rightClawMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_rightClawMotor.config_kF(0, 0.5 , timeoutMs); //(int slotIdx, double value, int timeoutMs)
			_rightClawMotor.config_kP(0, 0, timeoutMs);
			_rightClawMotor.config_kI(0, 0, timeoutMs);
			_rightClawMotor.config_kD(0, 0, timeoutMs);
			_rightClawMotor.config_IntegralZone(0, 0, timeoutMs);
			
			//Invert Motor
			_leftClawMotor.setInverted(false);
			_leftClawMotor.setSensorPhase(false);
			
			//Init Encoders
			_leftClawMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
			
			// Set the peak and nominal outputs, 12V means full
			_leftClawMotor.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_leftClawMotor.configNominalOutputReverse(0, timeoutMs);
			_leftClawMotor.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_leftClawMotor.configPeakOutputReverse(-1, timeoutMs);
			
			// Current Limiting
			_leftClawMotor.configPeakCurrentLimit(7, timeoutMs);
			_leftClawMotor.configPeakCurrentDuration(peakCurrentDur, timeoutMs); /* 0ms */
			_leftClawMotor.configContinuousCurrentLimit(6, timeoutMs);
			_leftClawMotor.enableCurrentLimit(true); /* turn it on */
			
			// Init Sensor to zero
			_leftClawMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs)
			
			// PID controls
			//TODO: Tune these
			_leftClawMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_leftClawMotor.config_kF(0, 0.5 , timeoutMs); //(int slotIdx, double value, int timeoutMs)
			_leftClawMotor.config_kP(0, 0, timeoutMs);
			_leftClawMotor.config_kI(0, 0, timeoutMs);
			_leftClawMotor.config_kD(0, 0, timeoutMs);
			_leftClawMotor.config_IntegralZone(0, 0, timeoutMs);
			
			initClawMoters = true;
		}
		return initClawMoters;
	}
		
	public boolean setClawTriggers(AnalogTrigger clawTiltTriggerIn, AnalogTrigger rightClawArmTriggerIn, AnalogTrigger leftClawArmTriggerIn) {
		//clawTiltTrigger = clawTiltTriggerIn;
		//rightClawArmTrigger = rightClawArmTriggerIn;
		//leftClawArmTrigger = leftClawArmTriggerIn;
		
		return true;
	}
	
	public boolean initClawTriggers() {
		
		//clawTiltTrigger.setLimitsVoltage(3.5, 5.0);
		//rightClawArmTrigger.setLimitsVoltage(3.5, 5.0);
		//leftClawArmTrigger.setLimitsVoltage(3.5, 5.0);
		
		//tiltSenPrev = clawTiltTrigger.getInWindow();				
		//rightClawSenPrev = rightClawArmTrigger.getInWindow();				
		//leftClawSenPrev = leftClawArmTrigger.getInWindow();		
		
		return true;
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
			int pos = _elevatorMotor.getSelectedSensorPosition(0);
			if(!upperSensorPressed && pos < 71000) { //71000
				//_elevatorMotor.set(ControlMode.Velocity, speed);
				_elevatorMotor.set(1);
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
    		if(!lowerSensorPressed && pos > 0) {
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
    	
    	if(!setMotors) {
    		System.err.println("Error: Grabbing moters are not set up");
    	}else if(!setSwitches){
    		System.err.println("Error: Grab Switch not set up");
    	}else {
    		grabSensorPressed = grabSwitch.getstate();
    		if(!grabSensorPressed){
    			_leftWheelMotor.setSpeed(speed);
    			_rightWheelMotor.setSpeed(-speed);		
    		}else {
    			_leftWheelMotor.setSpeed(0);
    			_rightWheelMotor.setSpeed(0);
    		
    		}
    	}
    }
    
    //Ejects the block
    //in:speed
    //out:nothing
    public void ejectBlock(double speed) {
    	
    	if(!setMotors) {
    		System.err.println("Error: Grabbing moters are not set up.");
    	}else if(!setSwitches){
    		System.err.println("Error: Grab Switch not set up");
    	}else {    		
    		_leftWheelMotor.setSpeed(-speed);
    		_rightWheelMotor.setSpeed(speed);
    	}
    }
    public void stopWheels() {
    	_leftWheelMotor.setSpeed(0);
    	_rightWheelMotor.setSpeed(0);
    }
    
    public void printClawPos() {
    	SmartDashboard.putNumber("Claw Tilt Position", tiltCnt);
    	SmartDashboard.putNumber("Right Claw Position", rightClawCnt);
    	SmartDashboard.putNumber("Left Claw Position", leftClawCnt);
    }
    
  //Lifts the block
    //in:speed
    //out:nothing
    public void tiltClawUp(double speed) {
    	if(!setClawMoters) {
    		System.err.println("Error : Claw Lift Moter not set up");
    	}else if(!initClawMoters) {
    		System.err.println("Error : Claw Lift Moter not initialize");
    	}else {
    		_tiltMotor.set(speed);
    		//boolean sen = clawTiltTrigger.getInWindow();
    		//if(sen!=tiltSenPrev) {tiltCnt++; tiltSenPrev=sen;}
    	}
    }
    
    //Lowers the block
    //in:speed
    //out:nothing
    public boolean tiltClawDown(double speed) {
    	if(!setClawMoters) {
    		System.err.println("Error : Claw Lift Moter not set up");
    	}else if(!initClawMoters) {
    		System.err.println("Error : Claw Lift Moter not initialize");
    	}else {
    		if (_tiltMotor.getOutputCurrent() <= 5) {
	    		_tiltMotor.set(-speed);
	    		//boolean sen = clawTiltTrigger.getInWindow();
	    		//if(sen!=tiltSenPrev) {tiltCnt--; tiltSenPrev=sen;} 
    		} else {return true;}
    		return false;
    	}
    	return false;
    }
    
    public void moveLeftClaw(double speed) {
    	//boolean senLeft = clawTiltTrigger.getInWindow();
    	_leftClawMotor.set(speed);
		//if(senLeft!=leftClawSenPrev && speed < 0) {leftClawCnt++; leftClawSenPrev=senLeft;}
		//if(senLeft!=leftClawSenPrev && speed > 0) {leftClawCnt--; leftClawSenPrev=senLeft;}
    }
    
    public void moveRightClaw(double speed) {
		//boolean senRight = clawTiltTrigger.getInWindow();
		_rightClawMotor.set(speed);
		//if(senRight!=rightClawSenPrev && speed > 0) {rightClawCnt++; rightClawSenPrev=senRight;}
		//if(senRight!=rightClawSenPrev && speed < 0) {rightClawCnt--; rightClawSenPrev=senRight;}
    }
    
    //Extend/Opens the claws
    //in:speed
    //out:nothing
    public void openClaws(double speed) {
    	if(!setClawMoters) {
    		System.err.println("Error : Extend Motors not set up");
    	}else if(!setClawMoters) {
    		System.err.println("Error : Extend Motors not initialize");
    	}else {
    		_leftClawMotor.set(speed);
    		_rightClawMotor.set(-speed);
    		//boolean senRight = clawTiltTrigger.getInWindow();
    		//boolean senLeft = clawTiltTrigger.getInWindow();
    		//if(senRight!=rightClawSenPrev) {rightClawCnt++; rightClawSenPrev=senRight;}
    		//if(senLeft!=leftClawSenPrev) {leftClawCnt++; leftClawSenPrev=senLeft;}
    	}
    }
    
    public void closeClaws(double speed) {
    	if(!setClawMoters) {
    		System.err.println("Error : Extend Motors not set up");
    	}else if(!setClawMoters) {
    		System.err.println("Error : Extend Motors not initialize");
    	}else {
    		_leftClawMotor.set(-speed);
    		_rightClawMotor.set(speed);
    		//boolean senRight = clawTiltTrigger.getInWindow();
    		//boolean senLeft = clawTiltTrigger.getInWindow();
    		//if(senRight!=rightClawSenPrev) {rightClawCnt--; rightClawSenPrev=senRight;}
    		//if(senLeft!=leftClawSenPrev) {leftClawCnt--; leftClawSenPrev=senLeft;}
    	}
    }
    public void noGrip() {
    	_leftClawMotor.set(0);
		_rightClawMotor.set(0);
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
