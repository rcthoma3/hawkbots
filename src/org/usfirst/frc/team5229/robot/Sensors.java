package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;


public class Sensors {
	
	private int timeoutMs = 10;
	private int pidIdx = 0;
	private int encTicksPerRot = 1440;
	private boolean setEnc = false;
	private boolean initEnc = false;
	private boolean setWhlSize = false;
	private double whlSize;
	
	private WPI_talonSRX _frontLeftMotor;
	private WPI_TalonSRX _rearLeftMotor;
	private WPI_TalonSRX _frontRightMotor;
	private WPI_TalonSRX _rearRightMotor;
	
	public boolean setEncoders (WPI_TalonSRX _frontLeftMotorIn, WPI_TalonSRX _rearLeftMotorIn, WPI_TalonSRX _frontRightMotorIn, WPI_TalonSRX _rearRightMotorIn) {
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
	
	//Intiate Encoders or WPI Talons
	//in:4 Motor Controllers
	//out:nothing
	public boolean initEncoders() {
		if (!setEnc) {
			System.out.println("Encoders Not Set");
		}
		else {
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
			
			// Init Sensor to zero
			_frontLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs) 
			_rearLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_frontRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_rearRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			
			// PID controls Front Left Motor
			_frontLeftMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_frontLeftMotor.config_kF(0, 0.45, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_frontLeftMotor.config_kP(0, 3.0, timeoutMs);
			_frontLeftMotor.config_kI(0, 0.02, timeoutMs);
			_frontLeftMotor.config_kD(0, 60, timeoutMs);
			
			// PID controls Rear Left Motor
			_rearLeftMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_rearLeftMotor.config_kF(0, 0, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_rearLeftMotor.config_kP(0, 0, timeoutMs);
			_rearLeftMotor.config_kI(0, 0, timeoutMs);
			_rearLeftMotor.config_kD(0, 0, timeoutMs);
			
			// PID controls Front Right Motor
			_frontRightMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_frontRightMotor.config_kF(0, 0, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_frontRightMotor.config_kP(0, 0, timeoutMs);
			_frontRightMotor.config_kI(0, 0, timeoutMs);
			_frontRightMotor.config_kD(0, 0, timeoutMs);
			
			// PID controls Rear Right Motor
			_rearRightMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
			_rearRightMotor.config_kF(0, 0, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
			_rearRightMotor.config_kP(0, 0, timeoutMs);
			_rearRightMotor.config_kI(0, 0, timeoutMs);
			_rearRightMotor.config_kD(0, 0, timeoutMs);
			
			// Init Sensor to zero
			_frontLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs)
			_rearLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			_frontRightMotor.setSelectedSensorPosition(0,pidIdx, timeoutMs);
			_rearRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
			
			// Inverts Motors
			_frontRightMotor.setInverted(true);
			_rearRightMotor.setInverted(true);
			_frontLeftMotor.setSensorPhase(false);
			_rearLeftMotor.setSensorPhase(false);
			_frontRightMotor.setSensorPhase(false);
			_rearRightMotor.setSensorPhase(false);
			
			// Init acceleration and cruise velocity - MotionMagic
			_frontLeftMotor.configMotionCruiseVelocity(600, 10);
			_frontLeftMotor.configMotionAcceleration(600, 10);
			_rearLeftMotor.configMotionCruiseVelocity(600, 10);
			_rearLeftMotor.configMotionAcceleration(600, 10);
			_frontRightMotor.configMotionCruiseVelocity(600, 10);
			_frontRightMotor.configMotionAcceleration(600, 10);
			_rearRightMotor.configMotionCruiseVelocity(600, 10);
			_rearRightMotor.configMotionAcceleration(600, 10);	
			
			initEnc = true;
		}
		return initEnc;
	}

	//Gets robot position
	//in:distance
	//out:Nothing Yet
	public void getPosition (double distance) {
		
		//use sensor to figure out what starting position we are in
		//relay information if needed
		
	}

	//Converts Encoder variable to distance
	//in: wheel size in inches and encoder counts
	//out: distance traveled in inches 
	public double encToDis (int enc) {
		if (!setWhlSize) {
			System.out.println("Wheel Size Not Set");
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
	}
	
	//Converts distance(in inches) to a variable for
	//in: wheel size in inches and desired distance in inches
	//out: encoder counts
	public int disToEnc (double dis) {
		if (!setWhlSize) {
			System.out.println("Wheel Size Not Set");
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
	}

	//Make a robot move forward during autonomous
	//in:Distance, 4 motor controllers
	//out:nothing
	public void driveFowardAuto(int dis, ControlMode ControlMode) {
		
		int enc = disToEnc(dis);
		
		if (!setEnc) {
			System.out.println("Encoders Not Set");
		}
		else if(!initEnc) {
			System.out.println("Encoders Not Initalized");
		}
		else {
			_frontLeftMotor.set(ControlMode.MotionMagic, enc );
			_frontRightMotor.set(ControlMode.MotionMagic, enc);
			_rearLeftMotor.set(ControlMode.MotionMagic, enc);
			_rearRightMotor.set(ControlMode.MotionMagic, enc);
		}
	}
	
	//Make a robot move backwards
	//in:Distance 4 motor controller
	//out:nothing
	public void driveBackwardAuto(int dis, ControlMode ControlMode){
		int enc = disToEnc(dis) * -1;//Robot move backwards
		
		if (!setEnc) {
			System.out.println("Encoders Not Set");
		}
		else if(!initEnc) {
			System.out.println("Encoders Not Initalized");
		}
		else {
			_frontLeftMotor.set(ControlMode.MotionMagic, enc );
			_frontRightMotor.set(ControlMode.MotionMagic, enc);
			_rearLeftMotor.set(ControlMode.MotionMagic, enc);
			_rearRightMotor.set(ControlMode.MotionMagic, enc);
		}
	}
}
