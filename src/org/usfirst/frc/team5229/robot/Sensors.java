package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;


public class Sensors {
	
	private int timeoutMs = 10;
	private int pidIdx = 0;
	private int encTicksPerRot = 1440;
	private int acc = 150; // Acceleration
	private int cruiseVel = 300; // Cruise Velocity 
	
	private boolean setEnc = false;
	private boolean initEnc = false;
	private boolean setWhlSize = false;
	private boolean setChsSize = false;
	
	private double whlSize; // Robot wheel size
	private double roboDim; // Robot diagonal distance between wheels
	
	private  WPI_TalonSRX _frontLeftMotor;
	private  WPI_TalonSRX _rearLeftMotor;
	private  WPI_TalonSRX _frontRightMotor;
	private  WPI_TalonSRX _rearRightMotor;
	
	public  boolean setEncoders (WPI_TalonSRX _frontLeftMotorIn, WPI_TalonSRX _rearLeftMotorIn, WPI_TalonSRX _frontRightMotorIn, WPI_TalonSRX _rearRightMotorIn) {
		_frontLeftMotor = _frontLeftMotorIn;
		_rearLeftMotor = _rearLeftMotorIn;
		_frontRightMotor = _frontRightMotorIn;
		_rearRightMotor = _rearRightMotorIn;
		setEnc = true;
		return setEnc;
	}
	
	public boolean setWheelSize(double whlSizeIn) {
		whlSize = whlSizeIn;
		setWhlSize = true;
		return setWhlSize;
	}
	
	public boolean setChassisSize(double ChsSizeIn) {
		roboDim = ChsSizeIn;
		setChsSize = true;
		return setChsSize;
	}
	
	//Intiate Encoders or WPI Talons
	//in:4 Motor Controllers
	//out:nothing
	public boolean initEncoders() {
		if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else {
			// Inverts Motors
			_frontRightMotor.setInverted(true);
			_rearRightMotor.setInverted(true);
			_frontLeftMotor.setInverted(false);
			_rearLeftMotor.setInverted(false);
			_frontLeftMotor.setSensorPhase(false);
			_rearLeftMotor.setSensorPhase(false);
			_frontRightMotor.setSensorPhase(false);
			_rearRightMotor.setSensorPhase(false);
			
	        // Initialize to zero
			_frontLeftMotor.set(0);
			_rearLeftMotor.set(0);
			_frontRightMotor.set(0);
			_rearRightMotor.set(0);	
			
			// Init Encoders
			_frontLeftMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0); // (feedbackDevice, int pidIdx, int timeoutMs)
			_rearLeftMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
			_frontRightMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
			_rearRightMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
			
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
			
			// PID controls Front Left Motor
			_frontLeftMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_frontLeftMotor.config_kF(0, 0.45, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_frontLeftMotor.config_kP(0, 3.0, timeoutMs);
			_frontLeftMotor.config_kI(0, 0.03, timeoutMs);
			_frontLeftMotor.config_kD(0, 65, timeoutMs);
			
			// PID controls Rear Left Motor
			_rearLeftMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_rearLeftMotor.config_kF(0, 0.6, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_rearLeftMotor.config_kP(0,2.2, timeoutMs);
			_rearLeftMotor.config_kI(0, 0.03, timeoutMs);
			_rearLeftMotor.config_kD(0, 40, timeoutMs);
			
			// PID controls Front Right Motor
			_frontRightMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_frontRightMotor.config_kF(0, 0.3, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_frontRightMotor.config_kP(0, 3.0, timeoutMs);
			_frontRightMotor.config_kI(0, 0.03, timeoutMs);
			_frontRightMotor.config_kD(0, 30, timeoutMs);
			
			// PID controls Rear Right Motor
			_rearRightMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_rearRightMotor.config_kF(0, 0.6, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_rearRightMotor.config_kP(0, 3.2, timeoutMs);
			_rearRightMotor.config_kI(0, 0.02, timeoutMs);
			_rearRightMotor.config_kD(0, 32, timeoutMs);
			
			// Init Sensor to zero
			_frontLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs)
			_rearLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_frontRightMotor.setSelectedSensorPosition(0,pidIdx, timeoutMs);
			_rearRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			
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

	//Make a robot move forward during autonomous
	//in:Distance, 4 motor controllers
	//out:nothing
	public void driveFowardAuto(int dis) {
		
		int enc = disToEnc(dis);
		
		if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else if(!initEnc) {
			System.err.println("ERROR: Encoders Not Initalized");
		}
		else {
			_frontLeftMotor.set(ControlMode.MotionMagic, enc);
			_frontRightMotor.set(ControlMode.MotionMagic, enc);
			_rearLeftMotor.set(ControlMode.MotionMagic, enc);
			_rearRightMotor.set(ControlMode.MotionMagic, enc);
		}
		
		SmartDashboard.putNumber("Front Left Pos: ", _frontLeftMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Front Right Pos: ", _frontRightMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Rear Left Pos: ", _rearLeftMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Rear Right Pos: ", _rearRightMotor.getSelectedSensorPosition(0));
		
		SmartDashboard.putNumber("Front Left Vel: ", _frontLeftMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Front Right Vel: ", _frontRightMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Rear Left Vel: ", _rearLeftMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Rear Right Vel: ", _rearRightMotor.getSelectedSensorVelocity(0));
		
		
	}
	
	//Make a robot move backwards
	//in:Distance 4 motor controller
	//out:nothing
	public void driveBackwardAuto(int dis){
		int enc = disToEnc(dis) * -1;//Robot move backwards
		
		if (!setEnc) {
			System.err.println("ERROR: Encoders Not Set");
		}
		else if(!initEnc) {
			System.err.println("ERROR: Encoders Not Initalized");
		}
		else {
			_frontLeftMotor.set(ControlMode.MotionMagic, enc );
			_frontRightMotor.set(ControlMode.MotionMagic, enc);
			_rearLeftMotor.set(ControlMode.MotionMagic, enc);
			_rearRightMotor.set(ControlMode.MotionMagic, enc);
		}
	}
	
	// This allows the robot to turn right
	//In: Distance 4 motor controller
	//out:Nothing
	public void turnRobotRight (double deg) {
		
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
			double dis = 2 * Math.PI *(roboDim / 2) * (deg / 360);
			int enc = disToEnc(dis);
			
			_frontLeftMotor.set(ControlMode.MotionMagic, enc );
			_frontRightMotor.set(ControlMode.MotionMagic, -enc);
			_rearLeftMotor.set(ControlMode.MotionMagic, enc);
			_rearRightMotor.set(ControlMode.MotionMagic, -enc);
		}
	}
		
	
	//This allows the robot to turn left
	//In: Distance 4 motor controller
	//out:Nothing
	public void turnRobotLeft (double deg) {
		
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
			double dis = 2 * Math.PI *(roboDim / 2) * (deg / 360);
			int enc = disToEnc(dis);
			
			_frontLeftMotor.set(ControlMode.MotionMagic, -enc );
			_frontRightMotor.set(ControlMode.MotionMagic, enc);
			_rearLeftMotor.set(ControlMode.MotionMagic, -enc);
			_rearRightMotor.set(ControlMode.MotionMagic, enc);
		}
	}
}
