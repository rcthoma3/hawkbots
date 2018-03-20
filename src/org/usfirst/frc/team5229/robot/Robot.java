
package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	// For Testing - Remove in final code
	boolean forward = true;
	boolean backward = true;
	boolean turnRight = true;
	boolean turnLeft = true;
	boolean follow = true;
	private int timeoutMs = 10;
	private int pidIdx = 0;
	public int threshold = 50;
	private int acc = 300; // Acceleration
	private int cruiseVel = 600; // Cruise Velocity 
	
	ControllerLogitech myController = new ControllerLogitech(1); // input is usb value for drive station
	ControllerLogitech myDriveController = new ControllerLogitech(2);
	Climbing myClimber = new Climbing();
	Elevator myElevator = new Elevator();
	Sensors myRobot = new Sensors();
	Autonomous myAutonRobot = new Autonomous();
	ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	
	UsbCamera Camera1;
	UsbCamera Camera2;
		
	MecanumDrive _drive;
	
	// Motor Declarations 
	WPI_TalonSRX _frontLeftMotor; 
	WPI_TalonSRX _rearLeftMotor;
	WPI_TalonSRX _frontRightMotor;
	WPI_TalonSRX _rearRightMotor;
	WPI_TalonSRX _elevatorMotor;
	WPI_TalonSRX _climbMotor;
	VictorSP _leftClawMotor;
	VictorSP _rightClawMotor;
	// Switch Declarations
	DigitalInput topClimbSwitch;
	DigitalInput bottomClimbSwitch;
	DigitalInput topElevatorSwitch;
	DigitalInput bottomElevatorSwitch;
	DigitalInput grabSwitch;

	// These values correspond to roboRIO ports
	//Talons - CAN
	int frontLeftMotorPort = 6;
	int rearLeftMotorPort = 5;
	int frontRightMotorPort = 3;
	int rearRightMotorPort = 2;
	int elevatorMotorPort = 1;
	int climbMotorPort = 4;
	//Switches - DIO
	int topElevatorPort = 2;
	int bottomElevatorPort = 1;
	int topClimbPort =0;
	int bottomClimbPort = 3;
	int grabSwitchPort = 4;
	//Victors - PWM
	int leftClawPort = 0;
	int rightClawPort = 1;
		
	//double whlSize = 8; // Wheel diameter in inches (Test Robot)
	//double roboDim = 30; // Diagonal distance between wheels in inches (Test Robot)
	
	double whlSize = 8; // Wheel diameter in inches (Final Robot)
	double roboDim = 30; // Diagonal distance between wheels in inches (Final Robot)
	String gameMsg = "XXX";
	

	public SendableChooser<Boolean> autonTune;
	public SendableChooser<Integer> autonAction;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		_frontLeftMotor = new WPI_TalonSRX(frontLeftMotorPort); 
		_rearLeftMotor = new WPI_TalonSRX(rearLeftMotorPort);
		_frontRightMotor = new WPI_TalonSRX(frontRightMotorPort);
		_rearRightMotor = new WPI_TalonSRX(rearRightMotorPort);
		
		_climbMotor = new WPI_TalonSRX(climbMotorPort);
		_leftClawMotor = new VictorSP(leftClawPort);
		_rightClawMotor = new VictorSP(rightClawPort);
		_elevatorMotor = new WPI_TalonSRX(elevatorMotorPort);
		
		Camera1 = CameraServer.getInstance().startAutomaticCapture();
		Camera2 = CameraServer.getInstance().startAutomaticCapture();
		
		// Initialize to zero
		_frontLeftMotor.set(0);
		_rearLeftMotor.set(0);
		_frontRightMotor.set(0);
		_rearRightMotor.set(0);
		_climbMotor.set(0);
		_elevatorMotor.set(0);
		
		gyro.calibrate();
		

		topClimbSwitch = new DigitalInput(topClimbPort);
		bottomClimbSwitch = new DigitalInput(bottomClimbPort);
		topElevatorSwitch = new DigitalInput(topElevatorPort);
		bottomElevatorSwitch = new DigitalInput(bottomElevatorPort);
		grabSwitch = new DigitalInput(grabSwitchPort);
		/*
		myClimber.setClimbMotor(_climbMotor);
		myClimber.initElevator();
		myClimber.setSwitches(topClimbSwitch, bottomClimbSwitch);
		myElevator.setElevator(_elevatorMotor);
		myElevator.initElevator();
		myElevator.setSwitches(topElevatorSwitch, bottomElevatorSwitch, grabSwitch);
		myElevator.setGrabMotors(_leftClawMotor, _rightClawMotor);
		
		myRobot.setEncoders (_frontLeftMotor, _rearLeftMotor, _frontRightMotor,  _rearRightMotor);
		myRobot.initEncoders();

		autonTune = new SendableChooser<Boolean>();
		autonTune.addDefault("Off", false);
		autonTune.addObject("On", true);
		
		autonAction = new SendableChooser<Integer>();
		autonAction.addDefault("Stop", 0);
		autonAction.addObject("Drive Forward", 1);
		autonAction.addObject("Drive Backward", 2);
		autonAction.addObject("Turn Left", 3);
		autonAction.addObject("Turn Right", 4);
		autonAction.addObject("Raise Elevator", 5);
		autonAction.addObject("Lower Elevator", 6);
		autonAction.addObject("Grab Cube", 7);
		autonAction.addObject("Eject Cube", 8);
		autonAction.addObject("Open Claw", 9);
		autonAction.addObject("Close Claw", 10);
		autonAction.addObject("Tilt Claw Up", 11);
		autonAction.addObject("Tilt Claw Up", 12);

		myAutonRobot.setAutoChooser();
		
		SmartDashboard.putNumber("Forward Distance", 0);
		SmartDashboard.putNumber("Backward Distance", 0);
		SmartDashboard.putNumber("Turning Left", 0);
		SmartDashboard.putNumber("Turning Right", 0);
		SmartDashboard.putNumber("Raise Elevator", 0);
		SmartDashboard.putNumber("Lower Elevator", 0);
		SmartDashboard.putNumber("Grab Block", 0);
		SmartDashboard.putNumber("Eject Block", 0);
		SmartDashboard.putNumber("Lift Block", 0);
		SmartDashboard.putNumber("Lower Block", 0);
		SmartDashboard.putNumber("Open claws", 0);
		SmartDashboard.putNumber("Close claws", 0);
		
		populateSmartDashboard();
		*/
		// Inverts Motors
					
					
					_frontLeftMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
					_frontLeftMotor.config_kF(0, 1.3, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
					_frontLeftMotor.config_kP(0, 4.0, timeoutMs);
					_frontLeftMotor.config_kI(0, 0.03, timeoutMs);
					_frontLeftMotor.config_kD(0, 30, timeoutMs);
					_frontLeftMotor.config_IntegralZone(0, 20, timeoutMs);
					
					// PID controls Rear Left Motor
					_rearLeftMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
					_rearLeftMotor.config_kF(0, 1.4, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
					_rearLeftMotor.config_kP(0, 4, timeoutMs);
					_rearLeftMotor.config_kI(0, 0.02, timeoutMs);
					_rearLeftMotor.config_kD(0, 40, timeoutMs);
					_rearLeftMotor.config_IntegralZone(0, 20, timeoutMs);
					
					// PID controls Front Right Motor
					_frontRightMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
					_frontRightMotor.config_kF(0, 2.3, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
					_frontRightMotor.config_kP(0, 4.0, timeoutMs);
					_frontRightMotor.config_kI(0, 0.03, timeoutMs);
					_frontRightMotor.config_kD(0, 50, timeoutMs);
					_frontRightMotor.config_IntegralZone(0, 20, timeoutMs);
					
					// PID controls Rear Right Motor
					_rearRightMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
					_rearRightMotor.config_kF(0, 1.4, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
					_rearRightMotor.config_kP(0, 4.0, timeoutMs);
					_rearRightMotor.config_kI(0, 0.02, timeoutMs);
					_rearRightMotor.config_kD(0, 5, timeoutMs);
					_rearRightMotor.config_IntegralZone(0, 20, timeoutMs);
		
	}
	

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		/*
		gameMsg = myAutonRobot.getGameMsg();
		
		myRobot.setWheelSize(whlSize);
		myRobot.setChassisSize(roboDim);	
		myRobot.setOverride(false);
		myRobot.setGyro(gyro);
		myAutonRobot.setSensor(myRobot);
		myAutonRobot.setElevator(myElevator);
*/
		// For Testing - Remove in final code
		forward = true;
		backward = true;
		turnRight = true;
		turnLeft = true;
		follow = true;
		
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
		
		//setPID();

		// Init acceleration and cruise velocity - MotionMagic
		_frontLeftMotor.configMotionCruiseVelocity(cruiseVel, timeoutMs); //(int sensorUnitsPer100ms, int timeoutMs)
		_frontLeftMotor.configMotionAcceleration(acc, timeoutMs); //(int sensorUnitsPer100msPerSec, int timeoutMs)
		_rearLeftMotor.configMotionCruiseVelocity(cruiseVel, timeoutMs);
		_rearLeftMotor.configMotionAcceleration(acc, timeoutMs);
		_frontRightMotor.configMotionCruiseVelocity(cruiseVel, timeoutMs);
		_frontRightMotor.configMotionAcceleration(acc, timeoutMs);
		_rearRightMotor.configMotionCruiseVelocity(cruiseVel, timeoutMs);
		_rearRightMotor.configMotionAcceleration(acc, timeoutMs);
		
		_frontLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs) 
		_rearLeftMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
		_frontRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
		_rearRightMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {	
		
		int enc = 14400;
		
		_frontLeftMotor.set(ControlMode.MotionMagic, enc); 
		_frontRightMotor.set(ControlMode.MotionMagic, enc);
		_rearLeftMotor.set(ControlMode.MotionMagic, enc); 
		_rearRightMotor.set(ControlMode.MotionMagic, enc); 
		//if (forward) {System.out.println("Going Forward"); forward = !myRobot.driveFowardAuto(240); System.out.println("Done Going Forward"); myRobot.stopRobot(); }
		//if (backward) {System.out.println("Going Backward"); backward = !myRobot.driveBackwardAuto(120); System.out.println("Done Going Backward"); myRobot.stopRobot(); Timer.delay(0.010);}
		//if (turnRight) {System.out.println("Doing Right Turn"); turnRight = !myRobot.turnRobotRightGyro(90); System.out.println("Done Turning Right"); myRobot.stopRobot(); Timer.delay(0.010);}
		//if (turnLeft) {System.out.println("Doing Left Turn"); turnLeft = !myRobot.turnRobotLeftGyro(90); System.out.println("Done Turning Left"); myRobot.stopRobot(); Timer.delay(0.010);}
		//if(follow) { follow = !myAutonRobot.followPath(); System.out.println("Done"); }
		//if (turnLeft) {System.out.println("Doing Left Turn"); turnLeft = !myRobot.turnRobotLeftGyro(90); System.out.println("Done Turning Left"); myRobot.stopRobot(); Timer.delay(0.010);}
		//myRobot.stopRobot();
		SmartDashboard.putNumber("Front Left Pos: ", _frontLeftMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Front Right Pos: ", _frontRightMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Rear Left Pos: ", _rearLeftMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Rear Right Pos: ", _rearRightMotor.getSelectedSensorPosition(0));
		
		//Updates wheel velocity
		SmartDashboard.putNumber("Front Left Vel: ", _frontLeftMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Front Right Vel: ", _frontRightMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Rear Left Vel: ", _rearLeftMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Rear Right Vel: ", _rearRightMotor.getSelectedSensorVelocity(0));
		
		Timer.delay(0.005);
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {	
		/*
		myRobot.setOverride(true);
		myRobot.stopRobot();
		follow = false;
		
		// Initialize mecanum drive
		_drive = new MecanumDrive(_frontLeftMotor, _rearLeftMotor, _frontRightMotor, _rearRightMotor);		
		
		// Something to do with safety 
		_drive.setSafetyEnabled(false);
		_drive.setExpiration(0.1);
		
		// Set Max output
		_drive.setMaxOutput(1.0);	
		
		// Motors should not be inverted for WPILib MecanumDrive Function
		_frontRightMotor.setInverted(false);
		_rearRightMotor.setInverted(false);
		_frontLeftMotor.setInverted(false);
		_rearLeftMotor.setInverted(false);
		
		populateSmartDashboard();
		*/
	}

	/**
	 * This function is called periodically during operator control 
	 */
	@Override
	public void teleopPeriodic() {
		/*
		if(!autonTune.getSelected())
		{
			_drive.driveCartesian(myDriveController.getLeftJoyX(), -myDriveController.getLeftJoyY(), myDriveController.getRightJoyX(), 0);	
			
			if (myDriveController.getButtonLeftBumber() && myDriveController.getButtonRightBumber()) { _drive.setMaxOutput(.5); }
			else if (myDriveController.getButtonLeftBumber()) { _drive.setMaxOutput(.25); }
			else if (myDriveController.getButtonRightBumber()) { _drive.setMaxOutput(.25); }
			else { _drive.setMaxOutput(1.0); }
			
			if (myController.getButtonRightD()) { myClimber.raiseElevator(1200, true); }
			if (myController.getButtonLeftD()) { myClimber.lowerElavator(400, true); }
			
			if (myController.getButtonUpD() ) { myClimber.raiseElevator(1200, false); }
			else if (myController.getButtonDownD() ) { myClimber.lowerElavator(400, false); }
			else { myClimber.checkSwitches(false); }
			
			
			if (myController.getButtonA()) { myElevator.lowerElevatorDis(0); }
			if (myController.getButtonX()) { myElevator.raiseElevatorDis(20000); } //Switch height
			if (myController.getButtonY()) { myElevator.raiseElevatorDis(70000); } //Scale height
			
			if (myController.getLeftJoyY() < -0.1) { myElevator.raiseElevator(myController.getLeftJoyY()*-600, false); }
			else if (myController.getLeftJoyY() > 0.1) { myElevator.lowerElevator(myController.getLeftJoyY()*600, false); }
			//if (myController.getLeftJoyY() > 0.1) { System.out.println(myController.getLeftJoyY()); }
			//else if (myController.getLeftJoyY() < -0.1) { System.out.println(myController.getLeftJoyY()); }
			else { myElevator.checkSwitches(false); }
			
			if (myController.getButtonLeftBumber()) { myElevator.grabBlock(1); }
			else if (myController.getButtonRightBumber()) { myElevator.ejectBlock(1); }
			else { myElevator.ejectBlock(0); }
		}
		else {
			autonTune();
		}
	
		populateSmartDashboard();

		Timer.delay(0.005);
		*/
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
		
	}
	
	@Override
	public void disabledInit() {
		/*
		myRobot.setOverride(true);
		myRobot.stopRobot();
		follow = false;
		*/
	}
	
	public void populateSmartDashboard ()
	{/*
		SmartDashboard.putNumber("Climb Motor Current", _climbMotor.getOutputCurrent());
		SmartDashboard.putNumber("Climb Motor Voltage", _climbMotor.getMotorOutputVoltage());
		SmartDashboard.putNumber("Climb Motor Percent Output", _climbMotor.getMotorOutputPercent());
		SmartDashboard.putNumber("Climb Position", _climbMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Climb Velocity", _climbMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putBoolean("Climb Max Height", myClimber.getTopSwitch());
		SmartDashboard.putBoolean("Climb Min Height", myClimber.getBottomSwitch());
		
		SmartDashboard.putNumber("Elevator Motor Current", _elevatorMotor.getOutputCurrent());
		SmartDashboard.putNumber("Elevator Motor Voltage", _elevatorMotor.getMotorOutputVoltage());
		SmartDashboard.putNumber("Elevator Motor Percent Output", _elevatorMotor.getMotorOutputPercent());
		SmartDashboard.putNumber("Elevator Position", _elevatorMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Elevator Veocity", _elevatorMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putBoolean("Elevator Max Height", myElevator.getElevatorTop());
		SmartDashboard.putBoolean("Elevator Min Height", myElevator.getElevatorBottom());
		SmartDashboard.getNumber("Foward Dis", 5);
	
		SmartDashboard.putString("Game message", gameMsg);
		
		int pos = myAutonRobot.getPositoin();
		if(pos == 0) {
			SmartDashboard.putString("Position", "Left");
		}else if(pos == 1) {
			SmartDashboard.putString("Position", "Center");
		}else if(pos == 2) {
			SmartDashboard.putString("Position", "Right");
		}
		
		int goal = myAutonRobot.getGlobalGoal();
		if(goal == 0) {
			SmartDashboard.putString("Goal", "Switch");
		}else if(goal == 1) {
			SmartDashboard.putString("Goal", "Scale");
		}else if(goal == 2) {
			SmartDashboard.putString("Goal", "Neither");
		}
		else {SmartDashboard.putString("Goal", "Error");}
		
		myRobot.updateDashboard(); 
		*/		
	}
	
	public void autonTune() {
		/*
		int autonChoice = autonAction.getSelected();
		
		switch(autonChoice) {
	    
		case 1:
			myRobot.driveFowardAuto((int)SmartDashboard.getNumber("Forward Distance", 0));
			myRobot.stopRobot();
			break;
		case 2:
			myRobot.driveBackwardAuto((int)SmartDashboard.getNumber("Backward Distance", 0));
			myRobot.stopRobot();
			break;
		case 3:			
			myRobot.turnRobotLeftGyro(SmartDashboard.getNumber("Turning Left", 0));
			myRobot.stopRobot();
			break;
		case 4:			
			myRobot.turnRobotRightGyro(SmartDashboard.getNumber("Turning Right", 0));
			myRobot.stopRobot();
			break;
		case 5:			
			myElevator.raiseElevatorDis(SmartDashboard.getNumber("Raise Elevator", 0));
			myRobot.stopRobot();
			break;
		case 6:			
			myElevator.lowerElevatorDis(SmartDashboard.getNumber("Lower Elevator", 0));
			myRobot.stopRobot();
			break;
		case 7:			
			myElevator.grabBlock(SmartDashboard.getNumber("Grab Block", 0));
			myRobot.stopRobot();
			break;
		case 8:			
			myElevator.ejectBlock(SmartDashboard.getNumber("Eject Block", 0));
			myRobot.stopRobot();
			break;
		case 9:			
			SmartDashboard.getNumber("Lift Block", 0);
			myRobot.stopRobot();
			break;
		case 10:			
			SmartDashboard.getNumber("Lower Block", 0);
			myRobot.stopRobot();
			break;
		case 11:			
			SmartDashboard.getNumber("Open claws", 0);
			myRobot.stopRobot();
			break;
		case 12:			
			SmartDashboard.getNumber("Close claws", 0);
			myRobot.stopRobot();
			break;
	    default:
	    	myRobot.stopRobot();
			myElevator.raiseElevatorDis(0);
			myElevator.grabBlock(0);	
			break;
	    
		}
		*/
	}	
}
