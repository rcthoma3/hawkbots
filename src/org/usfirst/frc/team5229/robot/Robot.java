
package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
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
	

	ControllerLogitech myController = new ControllerLogitech(1); // input is usb value for drive station
	//TODO: Uncomment the second controller once it is ready
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
	WPI_TalonSRX _leftClawArmMotor;
	WPI_TalonSRX _rightClawArmMotor;
	WPI_TalonSRX _clawTiltMotor;
	VictorSP _leftClawWheelMotor;
	VictorSP _rightClawWheelMotor;
	// Switch Declarations
	DigitalInput topClimbSwitch;
	DigitalInput bottomClimbSwitch;
	DigitalInput topElevatorSwitch;
	DigitalInput bottomElevatorSwitch;
	DigitalInput grabSwitch;
	// Analog Declarations
	//AnalogTrigger clawTiltTrigger;
	//AnalogTrigger rightClawArmTrigger;
	//AnalogTrigger leftClawArmTrigger;

	// These values correspond to roboRIO ports
	//Talons - CAN
	int frontLeftMotorPort = 6;
	int rearLeftMotorPort = 5;
	int frontRightMotorPort = 3;
	int rearRightMotorPort = 2;
	int elevatorMotorPort = 1;
	int climbMotorPort = 4;
	int clawTiltMotorPort = 7;
	int rightClawArmMotorPort = 8;
	int leftClawArmMotorPort = 9;
	//Switches - DIO
	int topElevatorPort = 2;
	int bottomElevatorPort = 1;
	int topClimbPort =0;
	int bottomClimbPort = 3;
	int grabSwitchPort = 4;
	//Victors - PWM
	int leftClawPort = 0;
	int rightClawPort = 1;
	//Bosch Motor Sensors - Analog
	//int clawTiltTriggerPort = 0;
	//int rightClawArmTriggerPort = 1;
	//int leftClawArmTriggerPort = 2;
		
	//double whlSize = 8; // Wheel diameter in inches (Test Robot)
	//double roboDim = 30; // Diagonal distance between wheels in inches (Test Robot)
	
	double whlSize = 8; // Wheel diameter in inches (Final Robot)
	double roboDim = 30; // Diagonal distance between wheels in inches (Final Robot)
	String gameMsg = "XXX";
	
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
		_leftClawWheelMotor = new VictorSP(leftClawPort);
		_rightClawWheelMotor = new VictorSP(rightClawPort);
		_elevatorMotor = new WPI_TalonSRX(elevatorMotorPort);
		_clawTiltMotor = new WPI_TalonSRX(clawTiltMotorPort);
		_rightClawArmMotor = new WPI_TalonSRX(rightClawArmMotorPort);
		_leftClawArmMotor = new WPI_TalonSRX(leftClawArmMotorPort);

		
		Camera1 = CameraServer.getInstance().startAutomaticCapture();
		Camera2 = CameraServer.getInstance().startAutomaticCapture();
		
		// Initialize to zero
		_frontLeftMotor.set(0);
		_rearLeftMotor.set(0);
		_frontRightMotor.set(0);
		_rearRightMotor.set(0);
		_climbMotor.set(0);
		_elevatorMotor.set(0);
		_clawTiltMotor.set(0);
		_rightClawArmMotor.set(0);
		_leftClawArmMotor.set(0);
		
		gyro.calibrate();	

		topClimbSwitch = new DigitalInput(topClimbPort);
		bottomClimbSwitch = new DigitalInput(bottomClimbPort);
		topElevatorSwitch = new DigitalInput(topElevatorPort);
		bottomElevatorSwitch = new DigitalInput(bottomElevatorPort);
		grabSwitch = new DigitalInput(grabSwitchPort);
		
		//clawTiltTrigger = new AnalogTrigger(clawTiltTriggerPort);
		//rightClawArmTrigger = new AnalogTrigger(rightClawArmTriggerPort);
		//leftClawArmTrigger = new AnalogTrigger(leftClawArmTriggerPort);
		
		myClimber.setClimbMotor(_climbMotor);
		myClimber.initElevator();
		myClimber.setSwitches(topClimbSwitch, bottomClimbSwitch);
		
		myElevator.setElevator(_elevatorMotor);
		myElevator.initElevator();
		myElevator.setSwitches(topElevatorSwitch, bottomElevatorSwitch, grabSwitch);
		myElevator.setGrabMotors(_leftClawWheelMotor, _rightClawWheelMotor);
		myElevator.setClawMotors(_clawTiltMotor, _rightClawArmMotor, _leftClawArmMotor);
		myElevator.initClawMotors();
		//myElevator.setClawTriggers(clawTiltTrigger, rightClawArmTrigger, leftClawArmTrigger);
		
		myRobot.setEncoders (_frontLeftMotor, _rearLeftMotor, _frontRightMotor,  _rearRightMotor);
		myRobot.initEncoders();
		myRobot.setWheelSize(whlSize);
		myRobot.setChassisSize(roboDim);
		
		myAutonRobot.setAutoChooser();
		
		populateSmartDashboard();
	}
	

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
					
		myRobot.setOverride(false);
		myRobot.setGyro(gyro);
		myAutonRobot.setSensor(myRobot);
		myAutonRobot.setElevator(myElevator);

		gameMsg= myAutonRobot.getGameMsg();
		
		myAutonRobot.setAutoChooser();
		
		populateSmartDashboard() ;
		
		follow = true;
		
		// For Testing - Remove in final code
		forward = true;
		backward = true;
		turnRight = true;
		turnLeft = true;
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {	
		
		//if (forward) {System.out.println("Going Forward"); forward = !myRobot.driveFowardAuto(100); System.out.println("Done Going Forward"); myRobot.stopRobot(); Timer.delay(0.010);}
		//if (turnLeft) {System.out.println("Doing Left Turn"); turnLeft = !myRobot.turnRobotLeftGyro(90); System.out.println("Done Turning Left"); myRobot.stopRobot(); Timer.delay(0.010);}
		//if (backward) {System.out.println("Going Backward"); backward = !myRobot.driveBackwardAuto(120); System.out.println("Done Going Backward"); myRobot.stopRobot(); Timer.delay(0.010);}
		//if (turnRight) {System.out.println("Doing Right Turn"); turnRight = !myRobot.turnRobotRightGyro(90); System.out.println("Done Turning Right"); myRobot.stopRobot(); Timer.delay(0.010);}
		//if (turnLeft) {System.out.println("Doing Left Turn"); turnLeft = !myRobot.turnRobotLeftGyro(90); System.out.println("Done Turning Left"); myRobot.stopRobot(); Timer.delay(0.010);}
		if(follow) { follow = !myAutonRobot.followPath(); System.out.println("Done"); }
		//if (turnLeft) {System.out.println("Doing Left Turn"); turnLeft = !myRobot.turnRobotLeftGyro(90); System.out.println("Done Turning Left"); myRobot.stopRobot(); Timer.delay(0.010);}
		myRobot.stopRobot();
		
		Timer.delay(0.005);
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {	
		
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
		
		populateSmartDashboard() ;
	}

	/**
	 * This function is called periodically during operator control 
	 */
	@Override
	public void teleopPeriodic() {

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
		
		if (myController.getButtonA() ) { myElevator.openClaws(0.8); } 
		else if (myController.getButtonB() ) { myElevator.closeClaws(0.8); }
		else if (myDriveController.getButtonA() && myDriveController.getButtonLeftD()) {myElevator.moveLeftClaw(0.8);}
		else if (myDriveController.getButtonA() && myDriveController.getButtonRightD()) {myElevator.moveLeftClaw(-0.8);}
		else if (myDriveController.getButtonB() && myDriveController.getButtonLeftD()) {myElevator.moveRightClaw(0.8);}
		else if (myDriveController.getButtonB() && myDriveController.getButtonRightD()) {myElevator.moveRightClaw(-0.8);}
		else { myElevator.openClaws(0);}
		
		if (myController.getRightJoyY() > 0.1) { myElevator.tiltClawUp(.8);}
		else if (myController.getRightJoyY() < -0.1) {myElevator.tiltClawDown(0.8); }
		else { myElevator.tiltClawUp(0); }
		
		if (myController.getButtonX()) { myElevator.raiseElevatorDis(13000); } //Switch height
		if (myController.getButtonY()) { myElevator.raiseElevatorDis(70000); } //Scale height
		
		if (myController.getLeftJoyY() < -0.1) { myElevator.raiseElevator(myController.getLeftJoyY()*-600, false); }
		else if (myController.getLeftJoyY() > 0.1) { myElevator.lowerElevator(myController.getLeftJoyY()*600, false); }
		else { myElevator.checkSwitches(false); }
		
		if (myController.getButtonLeftBumber()) { myElevator.grabBlock(0.5); }
		else if (myController.getButtonRightBumber()) { myElevator.ejectBlock(0.5); }
		else { myElevator.ejectBlock(0); }
	
		populateSmartDashboard() ;

		Timer.delay(0.005);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
		
	}
	
	@Override
	public void disabledInit() {
		myRobot.setOverride(true);
		myRobot.stopRobot();
		follow = false;
	}
	
	public void populateSmartDashboard ()
	{
		SmartDashboard.putNumber("Climb Motor Current", _climbMotor.getOutputCurrent());
		//SmartDashboard.putNumber("Climb Motor Voltage", _climbMotor.getMotorOutputVoltage());
		//SmartDashboard.putNumber("Climb Motor Percent Output", _climbMotor.getMotorOutputPercent());
		SmartDashboard.putNumber("Climb Position", _climbMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Climb Velocity", _climbMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putBoolean("Climb Max Height", myClimber.getTopSwitch());
		SmartDashboard.putBoolean("Climb Min Height", myClimber.getBottomSwitch());
		
		SmartDashboard.putNumber("Elevator Motor Current", _elevatorMotor.getOutputCurrent());
		//SmartDashboard.putNumber("Elevator Motor Voltage", _elevatorMotor.getMotorOutputVoltage());
		//SmartDashboard.putNumber("Elevator Motor Percent Output", _elevatorMotor.getMotorOutputPercent());
		SmartDashboard.putNumber("Elevator Position", _elevatorMotor.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Elevator Veocity", _elevatorMotor.getSelectedSensorVelocity(0));
		SmartDashboard.putBoolean("Elevator Max Height", myElevator.getElevatorTop());
		SmartDashboard.putBoolean("Elevator Min Height", myElevator.getElevatorBottom());
		
		SmartDashboard.putNumber("Right Arm Current", _rightClawArmMotor.getOutputCurrent());
		SmartDashboard.putNumber("Left Arm Current", _leftClawArmMotor.getOutputCurrent());
		SmartDashboard.putNumber("Claw Tilt Current", _clawTiltMotor.getOutputCurrent());
		SmartDashboard.putString("Game message", gameMsg);
		
		int pos = myAutonRobot.getPosition();
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
		myElevator.printClawPos();
	}		
}
