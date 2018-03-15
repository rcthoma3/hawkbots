package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Sensors {
	
	private int timeoutMs = 10;
	private int pidIdx = 0;
	private int encTicksPerRot = 1440;
	private int acc = 150; // Acceleration
	private int cruiseVel = 300; // Cruise Velocity 
	public int threshold = 50;
	private boolean override = false;
	
	private boolean setEnc = false;
	private boolean initEnc = false;
	private boolean setWhlSize = false;
	private boolean setChsSize = false;
	private boolean setGyro = false;
	
	private double whlSize; // Robot wheel size
	private double roboDim; // Robot diagonal distance between wheels
	
	private WPI_TalonSRX _frontLeftMotor;
	private WPI_TalonSRX _rearLeftMotor;
	private WPI_TalonSRX _frontRightMotor;
	private WPI_TalonSRX _rearRightMotor;
	private ADXRS450_Gyro gyro;
	
	private boolean frontLeftValid = true;
	private boolean frontRightValid = true;
	private boolean rearLeftValid = true;
	private boolean rearRightValid = true;
	
	private DigitalInput limSwitch;
	
	public void resetEncValid() {
		frontLeftValid = true;
		frontRightValid = true;
		rearLeftValid = true;
		rearRightValid=true;		
	}
	
	public boolean setGyro (ADXRS450_Gyro gyroIn) {
		gyro = gyroIn;
		setGyro = true;
		return setGyro;
	}
	
	public boolean setOverride(boolean input) {
		System.out.println("Override set");
		override = input;
		return true;
	}
	// Set Encoders/Talons 
	//in:All 4 Encoders or Motor Controllers
	//out:setEnc
	public  boolean setEncoders (WPI_TalonSRX _frontLeftMotorIn, WPI_TalonSRX _rearLeftMotorIn, WPI_TalonSRX _frontRightMotorIn, WPI_TalonSRX _rearRightMotorIn) {
		_frontLeftMotor = _frontLeftMotorIn;
		_rearLeftMotor = _rearLeftMotorIn;
		_frontRightMotor = _frontRightMotorIn;
		_rearRightMotor = _rearRightMotorIn;
		
        // Initialize to zero
		_frontLeftMotor.set(0);
		_rearLeftMotor.set(0);
		_frontRightMotor.set(0);
		_rearRightMotor.set(0);	
		
		setEnc = true;
		
		return setEnc;
	}
	
	// Set up wheel size
	//in:whlSizeIn or the size of the wheel
	//out:setWhlSize
	public boolean setWheelSize(double whlSizeIn) {
		whlSize = whlSizeIn;
		setWhlSize = true;
		return setWhlSize;
	}
	
	// Set the distance between the diagonal wheels
	//in:ChsSizeIn or the Chassis Size
	//out:setChsSize
	public boolean setChassisSize(double ChsSizeIn) {
		roboDim = ChsSizeIn;
		setChsSize = true;
		return setChsSize;
	}
	
	//Intiate Encoders or WPI Talons
	//in:4 Motor Controllers
	//out:intEnc
	public boolean initEncoders() {
		if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else {
			//Resetting the encoderValid statements to true
			frontRightValid = true;
			frontLeftValid = true;
			rearRightValid = true;
			rearLeftValid = true;
			
			// Inverts Motors
			_frontRightMotor.setInverted(true);
			_rearRightMotor.setInverted(true);
			_frontLeftMotor.setInverted(false);
			_rearLeftMotor.setInverted(false);
			_frontLeftMotor.setSensorPhase(false);
			_rearLeftMotor.setSensorPhase(false);
			_frontRightMotor.setSensorPhase(false);
			_rearRightMotor.setSensorPhase(false);
			
			// Init Encoders
			_frontLeftMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0); // (feedbackDevice, int pidIdx, int timeoutMs)
			_rearLeftMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
			_frontRightMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
			_rearRightMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
			
			//Sets closed loop error range
			_frontLeftMotor.configAllowableClosedloopError(0, threshold, timeoutMs);
			_frontRightMotor.configAllowableClosedloopError(0, threshold, timeoutMs);
			_rearLeftMotor.configAllowableClosedloopError(0, threshold, timeoutMs);
			_rearRightMotor.configAllowableClosedloopError(0, threshold, timeoutMs);
			
			// Set the peak and nominal outputs, 12V means full 
			_frontLeftMotor.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_frontLeftMotor.configNominalOutputReverse(0, timeoutMs);
			_frontLeftMotor.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_frontLeftMotor.configPeakOutputReverse(-1, timeoutMs);
			
			_rearLeftMotor.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_rearLeftMotor.configNominalOutputReverse(0, timeoutMs);
			_rearLeftMotor.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_rearLeftMotor.configPeakOutputReverse(-1, timeoutMs);
			
			_frontRightMotor.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_frontRightMotor.configNominalOutputReverse(0, timeoutMs);
			_frontRightMotor.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_frontRightMotor.configPeakOutputReverse(-1, timeoutMs);
			
			_rearRightMotor.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
			_rearRightMotor.configNominalOutputReverse(0, timeoutMs);
			_rearRightMotor.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
			_rearRightMotor.configPeakOutputReverse(-1, timeoutMs);
			
			// Init Sensor to zero
			_frontLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs) 
			_rearLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_frontRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_rearRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			
			setPID();

			// Init acceleration and cruise velocity - MotionMagic
			_frontLeftMotor.configMotionCruiseVelocity(cruiseVel, timeoutMs); //(int sensorUnitsPer100ms, int timeoutMs)
			_frontLeftMotor.configMotionAcceleration(acc, timeoutMs); //(int sensorUnitsPer100msPerSec, int timeoutMs)
			_rearLeftMotor.configMotionCruiseVelocity(cruiseVel, timeoutMs);
			_rearLeftMotor.configMotionAcceleration(acc, timeoutMs);
			_frontRightMotor.configMotionCruiseVelocity(cruiseVel, timeoutMs);
			_frontRightMotor.configMotionAcceleration(acc, timeoutMs);
			_rearRightMotor.configMotionCruiseVelocity(cruiseVel, timeoutMs);
			_rearRightMotor.configMotionAcceleration(acc, timeoutMs);	
			
			initEnc = true;
		}
		return initEnc;
	}
	
	public boolean setPID() {
		if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else {
			// PID controls Front Left Motor
			_frontLeftMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_frontLeftMotor.config_kF(0, 0.7, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_frontLeftMotor.config_kP(0, 3.0, timeoutMs);
			_frontLeftMotor.config_kI(0, 0.03, timeoutMs);
			_frontLeftMotor.config_kD(0, 30, timeoutMs);
			_frontLeftMotor.config_IntegralZone(0, 20, timeoutMs);
			
			// PID controls Rear Left Motor
			_rearLeftMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_rearLeftMotor.config_kF(0, 0.84, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_rearLeftMotor.config_kP(0,3, timeoutMs);
			_rearLeftMotor.config_kI(0, 0.01, timeoutMs);
			_rearLeftMotor.config_kD(0, 30, timeoutMs);
			_rearLeftMotor.config_IntegralZone(0, 20, timeoutMs);
			
			// PID controls Front Right Motor
			_frontRightMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_frontRightMotor.config_kF(0, 0.01, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_frontRightMotor.config_kP(0, 3.0, timeoutMs);
			_frontRightMotor.config_kI(0, 0.03, timeoutMs);
			_frontRightMotor.config_kD(0, 30, timeoutMs);
			_frontRightMotor.config_IntegralZone(0, 20, timeoutMs);
			
			// PID controls Rear Right Motor
			_rearRightMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_rearRightMotor.config_kF(0, 0.5, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_rearRightMotor.config_kP(0, 3.0, timeoutMs);
			_rearRightMotor.config_kI(0, 0, timeoutMs);
			_rearRightMotor.config_kD(0, 30, timeoutMs);
			_rearRightMotor.config_IntegralZone(0, 0, timeoutMs);

			return true; 
		}
		return false;
	}

	//Converts Encoder variable to distance
	//in: wheel size in inches and encoder counts
	//out: distance traveled in inches 
	public double encToDis (int enc) {
		
		if (!setWhlSize) {
			System.err.println("ERROR: Wheel Size Not Set");
		}
		else {
			//Converts the diameter of the wheel to radius
			//in inches
			double r = whlSize / 2;
			//Gets rotation from encoder value in inches
			double rotations = enc / encTicksPerRot;
			// Distance obtained from 2PIr multiplied
			//By rotations in inches
			double dis = 2*Math.PI*r * rotations;
			
			return dis;
		}
		return 0;
	}
	
	//Converts distance(in inches) to a variable for
	//in: wheel size in inches and desired distance in inches
	//out: encoder counts
	public int disToEnc (double dis) {
		
		if (!setWhlSize) {
			System.err.println("ERROR: Wheel Size Not Set");
		}
		else {
			//Converts the diameter of the wheel to radius
			//in inches
			double r = whlSize / 2;
			// Encoder variable obtained from 2PIr multiplied
			//By rotations
			int enc = (int) Math.round(((encTicksPerRot*dis)/( 2 * Math.PI * r)));
			
			return enc;
		}
		
		return 0;
	}
	
	//Stops robot at the end of whatever movements it is set to make during auton
	public boolean stopRobot() {
		if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else if(!initEnc) {
			System.err.println("ERROR: Encoders Not Initalized");
		}
		else {
			_frontLeftMotor.set(0);
			_rearLeftMotor.set(0);
			_frontRightMotor.set(0);
			_rearRightMotor.set(0);
			
			Timer.delay(0.010);
		
			return true;
		}
		return false;
	}
	
	//Make a robot move forward during autonomous
	//in:Distance, 4 motor controllers
	//out:nothing
	public boolean driveFowardAuto(int dis) {
		
		if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else if(!initEnc) {
			System.err.println("ERROR: Encoders Not Initalized");
		}
		else {
			
			int enc = disToEnc(dis);
			int cur = 0;	
			
			_frontRightMotor.setInverted(true);
			_rearRightMotor.setInverted(true);
			_frontLeftMotor.setInverted(false);
			_rearLeftMotor.setInverted(false);
			_frontLeftMotor.setSensorPhase(false);
			_rearLeftMotor.setSensorPhase(false);
			_frontRightMotor.setSensorPhase(false);
			_rearRightMotor.setSensorPhase(false);
			
			// Reset Sensor to zero
			_frontLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs) 
			_rearLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_frontRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_rearRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			
			resetEncValid();						
			
			while(!(cur < enc + threshold && cur > enc - threshold) && !override) {
				
				override = !RobotState.isAutonomous();
				
				if (frontLeftValid) { _frontLeftMotor.set(ControlMode.MotionMagic, enc); }
				if (frontRightValid) { _frontRightMotor.set(ControlMode.MotionMagic, enc); }
				if (rearLeftValid) { _rearLeftMotor.set(ControlMode.MotionMagic, enc); }
				if (rearRightValid) { _rearRightMotor.set(ControlMode.MotionMagic, enc); }
				
				checkEncoders();
				updateDashboard();
				
				cur = Math.max(Math.max(_frontLeftMotor.getSelectedSensorPosition(0), _frontRightMotor.getSelectedSensorPosition(0)),
						Math.max(_rearLeftMotor.getSelectedSensorPosition(0), _rearRightMotor.getSelectedSensorPosition(0)));

				Timer.delay(0.005);
			}
			
			
			return true;
		}
		return false;
	}
	
	//Make a robot move backwards
	//in:Distance 4 motor controller
	//out:nothing
	public boolean driveBackwardAuto(int dis){
		
		if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else if(!initEnc) {
			System.err.println("ERROR: Encoders Not Initalized");
		}
		else {
			int enc = disToEnc(dis);//Robot move backwards
			int cur = 0;
			
			_frontRightMotor.setInverted(false);
			_rearRightMotor.setInverted(false);
			_frontLeftMotor.setInverted(true);
			_rearLeftMotor.setInverted(true);
			_frontLeftMotor.setSensorPhase(false);
			_rearLeftMotor.setSensorPhase(false);
			_frontRightMotor.setSensorPhase(false);
			_rearRightMotor.setSensorPhase(false);
			
			// Reset Sensor to zero
			_frontLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs) 
			_rearLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_frontRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_rearRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			
			resetEncValid();
			
			while(!(cur < enc + threshold && cur > enc - threshold) && !override) {
				
				override = !RobotState.isAutonomous();
				
				if (frontLeftValid) { _frontLeftMotor.set(ControlMode.MotionMagic, enc); }
				if (frontRightValid) { _frontRightMotor.set(ControlMode.MotionMagic, enc); }
				if (rearLeftValid) { _rearLeftMotor.set(ControlMode.MotionMagic, enc); }
				if (rearRightValid) { _rearRightMotor.set(ControlMode.MotionMagic, enc); }
				
				checkEncoders();
				updateDashboard();
				
				cur = Math.max(Math.max(_frontLeftMotor.getSelectedSensorPosition(0), _frontRightMotor.getSelectedSensorPosition(0)),
						Math.max(_rearLeftMotor.getSelectedSensorPosition(0), _rearRightMotor.getSelectedSensorPosition(0)));
				
				Timer.delay(0.005);
			}
			
			
						
			return true;
		}
		return false;
	}
	
	public boolean turnRobotRightGyro(double deg) {
		if(!setChsSize) {
			System.err.println("ERROR: Chassis Size Not Set");
		}
		else if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else if(!initEnc) {
			System.err.println("ERROR: Encoders Not Initalized");
		}
		else {
			gyro.reset();
			double cur = gyro.getAngle();
			double target = cur + deg -4;
			
			_frontRightMotor.setInverted(false);
			_rearRightMotor.setInverted(false);
			_frontLeftMotor.setInverted(false);
			_rearLeftMotor.setInverted(false);
			_frontLeftMotor.setSensorPhase(false);
			_rearLeftMotor.setSensorPhase(false);
			_frontRightMotor.setSensorPhase(false);
			_rearRightMotor.setSensorPhase(false);			
			
			resetEncValid();
			
			int enc = 175;
			
			while (!(cur < target + 2 && cur > target - 2) && !override ) {
				
				//enc = enc + 20;
				override = !RobotState.isAutonomous();
				
				if (frontLeftValid) { _frontLeftMotor.set(ControlMode.Velocity, enc); }
				if (frontRightValid) { _frontRightMotor.set(ControlMode.Velocity, enc); }
				if (rearLeftValid) { _rearLeftMotor.set(ControlMode.Velocity, enc); }
				if (rearRightValid) { _rearRightMotor.set(ControlMode.Velocity, enc); }
				
				//checkEncoders();
				updateDashboard();
				
				cur = gyro.getAngle();
				Timer.delay(0.005);
			}
			
			// Reset Sensor to zero
			_frontLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs) 
			_rearLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_frontRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_rearRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			
			return true;
		}
		return false;
	}
	
	// This allows the robot to turn right
	//In: Distance 4 motor controller
	//out:Nothing
	public boolean turnRobotRight (double deg) {
		
		if(!setChsSize) {
			System.err.println("ERROR: Chassis Size Not Set");
		}
		else if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else if(!initEnc) {
			System.err.println("ERROR: Encoders Not Initalized");
		}
		else {	
			double dis = (2 * Math.PI *(roboDim / 2) * (deg / 360))*1.85;
			int enc = disToEnc(dis);
			int cur = 0;
			
			_frontRightMotor.setInverted(false);
			_rearRightMotor.setInverted(false);
			_frontLeftMotor.setInverted(false);
			_rearLeftMotor.setInverted(false);
			_frontLeftMotor.setSensorPhase(false);
			_rearLeftMotor.setSensorPhase(false);
			_frontRightMotor.setSensorPhase(false);
			_rearRightMotor.setSensorPhase(false);
			
			// Reset Sensor to zero
			_frontLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs) 
			_rearLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_frontRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_rearRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			
			resetEncValid();			
					
			while (!(cur < enc + threshold && cur > enc - threshold) && !override) {
				override = !RobotState.isAutonomous();
				
				if (frontLeftValid) { _frontLeftMotor.set(ControlMode.MotionMagic, enc); }
				if (frontRightValid) { _frontRightMotor.set(ControlMode.MotionMagic, enc); }
				if (rearLeftValid) { _rearLeftMotor.set(ControlMode.MotionMagic, enc); }
				if (rearRightValid) { _rearRightMotor.set(ControlMode.MotionMagic, enc); }
				
				checkEncoders();
				updateDashboard();
				
				cur = Math.max(Math.max(_frontLeftMotor.getSelectedSensorPosition(0), _frontRightMotor.getSelectedSensorPosition(0)),
						Math.max(_rearLeftMotor.getSelectedSensorPosition(0), _rearRightMotor.getSelectedSensorPosition(0)));
				
				Timer.delay(0.005);
			}
		
			return true;
		}
		return false;
	}
		
	//This allows the robot to turn left
	//In: Distance 4 motor controller
	//out:Nothing
	public boolean turnRobotLeft (double deg) {
		
		if(!setChsSize) {
			System.err.println("ERROR: Chassis Size Not Set");
		}
		else if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else if(!initEnc) {
			System.err.println("ERROR: Encoders Not Initalized");
		}
		else {
			double dis = 2 * Math.PI *(roboDim / 2) * (deg / 360)*1.9;
			int enc = disToEnc(dis);
			int cur = 0;
			
			_frontRightMotor.setInverted(true);
			_rearRightMotor.setInverted(true);
			_frontLeftMotor.setInverted(true);
			_rearLeftMotor.setInverted(true);
			_frontLeftMotor.setSensorPhase(false);
			_rearLeftMotor.setSensorPhase(false);
			_frontRightMotor.setSensorPhase(false);
			_rearRightMotor.setSensorPhase(false);
			
			// Reset Sensor to zero
			_frontLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs) 
			_rearLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_frontRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_rearRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			
			resetEncValid();
			
			while (!(cur < enc + threshold && cur > enc - threshold) && !override) {
				override = !RobotState.isAutonomous();
			
				if (frontLeftValid) { _frontLeftMotor.set(ControlMode.MotionMagic, enc); }
				if (frontRightValid) { _frontRightMotor.set(ControlMode.MotionMagic, enc); }
				if (rearLeftValid) { _rearLeftMotor.set(ControlMode.MotionMagic, enc); }
				if (rearRightValid) { _rearRightMotor.set(ControlMode.MotionMagic, enc); }
				
				checkEncoders();
				updateDashboard();
				
				cur = Math.max(Math.max(_frontLeftMotor.getSelectedSensorPosition(0), _frontRightMotor.getSelectedSensorPosition(0)),
						Math.max(_rearLeftMotor.getSelectedSensorPosition(0), _rearRightMotor.getSelectedSensorPosition(0)));
				
				Timer.delay(0.005);
			}
			
			return true;
		}
		return false;
	}
	
	public boolean turnRobotLeftGyro(double deg) {
		if(!setChsSize) {
			System.err.println("ERROR: Chassis Size Not Set");
		}
		else if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else if(!initEnc) {
			System.err.println("ERROR: Encoders Not Initalized");
		}
		else {
			gyro.reset();
			double cur = gyro.getAngle();
			double target = -cur + deg -4 ;
			
			_frontRightMotor.setInverted(true);
			_rearRightMotor.setInverted(true);
			_frontLeftMotor.setInverted(true);
			_rearLeftMotor.setInverted(true);
			_frontLeftMotor.setSensorPhase(false);
			_rearLeftMotor.setSensorPhase(false);
			_frontRightMotor.setSensorPhase(false);
			_rearRightMotor.setSensorPhase(false);
			
			resetEncValid();
			
			int enc = 175;
			
			while (!(-cur < target + 2 && -cur > target - 2) && !override ) {
				
				//enc = enc + 20;
				override = !RobotState.isAutonomous();
				
				if (frontLeftValid) { _frontLeftMotor.set(ControlMode.Velocity, enc); }
				if (frontRightValid) { _frontRightMotor.set(ControlMode.Velocity, enc); }
				if (rearLeftValid) { _rearLeftMotor.set(ControlMode.Velocity, enc); }
				if (rearRightValid) { _rearRightMotor.set(ControlMode.Velocity, enc); }
				
				//checkEncoders();
				updateDashboard();
				
				cur = gyro.getAngle();
				Timer.delay(0.005);
			}
			
			// Reset Sensor to zero
			_frontLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs) 
			_rearLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_frontRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_rearRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			
			return true;
		}
		return false;
	}
	// Set up the limit switch
	//in:input
	//out:limswitch
	public DigitalInput limitswitch (DigitalInput input) {
		
		limSwitch = input;
		
		return limSwitch;
	}
	
	// Get the state of the limit switch
	//in:nothing
	//out:limSwitch.get()
	public boolean getstate () {
		
		return !limSwitch.get();
	}
	
	// Check the Encoders to see if they work properly
	//in:nothing
	//out:nothing
	public void checkEncoders() {
		//Get encoder positions
		int frontLeft = _frontLeftMotor.getSelectedSensorPosition(0);
		int frontRight = _frontRightMotor.getSelectedSensorPosition(0);
		int rearLeft = _rearLeftMotor.getSelectedSensorPosition(0);
		int rearRight = _rearRightMotor.getSelectedSensorPosition(0);
		
		//"Stationary" range, must move beyond this position to check
		final int CheckPos = 100;
		//Error value to check difference against for each encoder pair
		final int ErrorVal = 350;
		
		 if (frontLeft > CheckPos || frontRight > CheckPos || rearLeft > CheckPos || rearRight > CheckPos) {
			
			//Differences between encoders
			int frontDiff = Math.abs(_frontRightMotor.getSelectedSensorPosition(0) - _frontLeftMotor.getSelectedSensorPosition(0));
			int rearDiff = Math.abs(_rearRightMotor.getSelectedSensorPosition(0) - _rearLeftMotor.getSelectedSensorPosition(0));
			int leftDiff = Math.abs(_frontLeftMotor.getSelectedSensorPosition(0) - _rearLeftMotor.getSelectedSensorPosition(0));
			int rightDiff = Math.abs(_frontRightMotor.getSelectedSensorPosition(0) - _rearRightMotor.getSelectedSensorPosition(0));
			int frontRightRearLeftDiff = Math.abs(_frontRightMotor.getSelectedSensorPosition(0) - _rearLeftMotor.getSelectedSensorPosition(0));
			int frontLeftRearRightDiff = Math.abs(_frontLeftMotor.getSelectedSensorPosition(0) - _rearRightMotor.getSelectedSensorPosition(0));
		
			//Check if any encoders have a difference greater than the allowed error range
			if ((frontDiff > ErrorVal && frontLeftValid && frontRightValid)
					|| (rearDiff > ErrorVal && rearLeftValid && rearRightValid)
					|| (leftDiff > ErrorVal && frontLeftValid && rearLeftValid)
					|| (rightDiff > ErrorVal && frontRightValid && rearRightValid)
					|| (frontRightRearLeftDiff > ErrorVal && frontRightValid && rearLeftValid)
					|| (frontLeftRearRightDiff > ErrorVal && frontLeftValid && rearRightValid)) {
				
				//Calculate total differences for each encoder with every other encoder
				int frontLeftTotalDiff = 0;//frontDiff + leftDiff + frontLeftRearRightDiff;
				int frontRightTotalDiff = 0;//frontDiff + rightDiff + frontRightRearLeftDiff;
				int rearLeftTotalDiff = 0;//rearDiff + leftDiff + frontRightRearLeftDiff;
				int rearRightTotalDiff = 0;//rearDiff + rightDiff + frontLeftRearRightDiff;
				
				//Add totals (including only valid encoders)
				if (frontLeftValid || frontRightValid || rearLeftValid || rearRightValid)
				{
					if (frontRightValid) {
						frontLeftTotalDiff += frontDiff;
					}
					if (rearLeftValid) {
						frontLeftTotalDiff += leftDiff;
					}
					if (rearRightValid) {
						frontLeftTotalDiff += frontLeftRearRightDiff;
					}
				}
				if (frontRightValid)
				{
					if (frontLeftValid) {
						frontRightTotalDiff += frontDiff;
					}
					if (rearLeftValid) {
						frontRightTotalDiff += frontRightRearLeftDiff;
					}
					if (rearRightValid) {
						frontRightTotalDiff += rightDiff;
					}
				}
				if (rearLeftValid)
				{
					if (frontLeftValid) {
						rearLeftTotalDiff += leftDiff;
					}
					if (frontRightValid) {
						rearLeftTotalDiff += frontRightRearLeftDiff;
					}
					if (rearRightValid) {
						rearLeftTotalDiff += rearDiff;
					}
				}
				if (rearRightValid)
				{
					if (frontLeftValid) {
						rearRightTotalDiff += frontLeftRearRightDiff;
					}
					if (frontRightValid) {
						rearRightTotalDiff += rightDiff;
					}
					if (rearLeftValid) {
						rearRightTotalDiff += rearDiff;
					}
				}
				
				//Find Greatest
				//1 - 4 Values
				//1 gives frontLeft, 2 gives frontRight, 3 gives rearLeft, 4 gives rearRight
				int badPosition = 0;
				int currentLargest = 0;
				if (frontLeftTotalDiff > currentLargest && frontLeftValid)
				{
					badPosition = 1;
					currentLargest = frontLeftTotalDiff;
				}
				if (frontRightTotalDiff > currentLargest)
				{
					badPosition = 2;
					currentLargest = frontRightTotalDiff;
				}
				if (rearLeftTotalDiff > currentLargest)
				{
					badPosition = 3;
					currentLargest = rearLeftTotalDiff;
				}
				if (rearRightTotalDiff > currentLargest)
				{
					badPosition = 4;
					currentLargest = rearRightTotalDiff;
				}
				
				//For the greatest value, set that motor to follow and assign it to not be valid
				if (badPosition == 1) {
					
					//Tells that wheel what motor to follow
					if (rearLeftValid) { _frontLeftMotor.follow(_rearLeftMotor); }
					else if (rearRightValid) { _frontLeftMotor.follow(_rearRightMotor); }
					else { _frontLeftMotor.follow(_frontRightMotor); }
					frontLeftValid = false;
					System.out.println("frontLeftError");
				}
				if (badPosition == 2) {
					
					//Tells that wheel what motor to follow
					if (rearRightValid) { _frontRightMotor.follow(_rearRightMotor); }
					else if (rearLeftValid) { _frontRightMotor.follow(_rearLeftMotor); }
					else { _frontRightMotor.follow(_frontLeftMotor); }
					frontRightValid = false;
					System.out.println("frontRightError");
				}
				if (badPosition == 3) {
					
					//Tells that wheel what motor to follow
					if (frontLeftValid) {  _rearLeftMotor.follow(_frontLeftMotor); }
					else if (frontRightValid) { _rearLeftMotor.follow(_frontRightMotor); }
					else { _rearLeftMotor.follow(_rearRightMotor); }
					rearLeftValid = false;
					System.out.println("rearLeftError");
				}
				if (badPosition == 4) {
          
					//Tells that wheel what motor to follow
					if (frontRightValid) { _rearRightMotor.follow(_frontRightMotor); }
					else if (frontLeftValid) { _rearRightMotor.follow(_frontLeftMotor); }
					else { _rearRightMotor.follow(_rearLeftMotor); }
					rearRightValid = false;
					System.out.println("rearRightError");
				}
			}
		}
	}
	
	//Updates SmartDashboard with wheel position, velocity, and whether or not the encoder for a certain wheel is valid
	public void updateDashboard() {
		
		//Updates wheel position
		SmartDashboard.putNumber("Front Left Pos: ", _frontLeftMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Front Right Pos: ", _frontRightMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Rear Left Pos: ", _rearLeftMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Rear Right Pos: ", _rearRightMotor.getSelectedSensorPosition(0));
		
		//Updates wheel velocity
		SmartDashboard.putNumber("Front Left Vel: ", _frontLeftMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Front Right Vel: ", _frontRightMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Rear Left Vel: ", _rearLeftMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Rear Right Vel: ", _rearRightMotor.getSelectedSensorVelocity(0));
		
		//Updates whether or not the wheel encoders are valid
		SmartDashboard.putBoolean("Front Right Valid", frontRightValid);
		SmartDashboard.putBoolean("Front Left Valid", frontLeftValid);
		SmartDashboard.putBoolean("Rear Right Valid", rearRightValid);
		SmartDashboard.putBoolean("Rear Left Valid", rearLeftValid);
	}
}