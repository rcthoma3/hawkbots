package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PWM;
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
	
	boolean forward = true;
	boolean backward = true;
	boolean turnRight = true;
	boolean turnLeft = true;
	boolean follow = true;

	ControllerLogitech myController = new ControllerLogitech();
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
	VictorSP _climbMotor;
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
	int rearLeftMotorPort = 8;
	int frontRightMotorPort = 5;
	int rearRightMotorPort = 7;
	int elevatorMotorPort = 9;
	//Switches - DIO
	int topElevatorPort = 0;
	int bottomElevatorPort = 1;
	int topClimbPort =2;
	int bottomClimbPort = 3;
	int grabSwitchPort = 4;
	//Victors - PWM
	int leftClawPort = 0;
	int rightClawPort = 1;
	int climbMotorPort = 2;
		
	double whlSize = 8; // Wheel diameter in inches
	double roboDim = 30; // Diagonal distance between wheels in inches
	
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
		
		Camera1 = CameraServer.getInstance().startAutomaticCapture();
		Camera2 = CameraServer.getInstance().startAutomaticCapture();
		
		// Initialize to zero
		_frontLeftMotor.set(0);
		_rearLeftMotor.set(0);
		_frontRightMotor.set(0);
		_rearRightMotor.set(0);
		
		gyro.calibrate();
		
		myAutonRobot.setAutoChooser();
		
		_climbMotor = new VictorSP(climbMotorPort);
		_leftClawMotor = new VictorSP(leftClawPort);
		_rightClawMotor = new VictorSP(rightClawPort);
		_elevatorMotor = new WPI_TalonSRX(elevatorMotorPort);
		
		topClimbSwitch = new DigitalInput(topClimbPort);
		bottomClimbSwitch = new DigitalInput(bottomClimbPort);
		topElevatorSwitch = new DigitalInput(topElevatorPort);
		bottomElevatorSwitch = new DigitalInput(bottomElevatorPort);
		grabSwitch = new DigitalInput(grabSwitchPort);
		
		myClimber.setClimbMotor(_climbMotor);
		myClimber.setSwitches(topClimbSwitch, bottomClimbSwitch);
		myElevator.setElevator(_elevatorMotor);
		//myElevator.initElevator();
		myElevator.setSwitches(topElevatorSwitch, bottomElevatorSwitch, grabSwitch);
		myElevator.setGrabMotors(_leftClawMotor, _rightClawMotor);
		

	}
	

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		
		myRobot.setEncoders (_frontLeftMotor, _rearLeftMotor, _frontRightMotor,  _rearRightMotor);
		myRobot.setWheelSize(whlSize);
		myRobot.setChassisSize(roboDim);	
		myRobot.initEncoders();
		myRobot.setOverride(false);
		myRobot.setGyro(gyro);
		myAutonRobot.setSensor(myRobot);
		String gameMsg = myAutonRobot.getGameMsg();
		int pos = myAutonRobot.getPositoin();
		
		SmartDashboard.putString("Game message", gameMsg);
		if(pos == 0) {
			SmartDashboard.putString("Position", "Left");
		}else if(pos == 1) {
			SmartDashboard.putString("Position", "Center");
		}else if(pos == 2) {
			SmartDashboard.putString("Position", "Right");
		}
		forward = true;
		backward = true;
		turnRight = true;
		turnLeft = true;
		follow = true;
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {	
		
		//if (forward) {System.out.println("Going Forward"); forward = !myRobot.driveFowardAuto(120); System.out.println("Done Going Forward"); myRobot.stopRobot(); Timer.delay(0.010);}
		//if (backward) {System.out.println("Going Backward"); backward = !myRobot.driveBackwardAuto(120); System.out.println("Done Going Backward"); myRobot.stopRobot(); Timer.delay(0.010);}
		//if (turnRight) {System.out.println("Doing Rigt Turn"); turnRight = !myRobot.turnRobotRight(90); System.out.println("Done Turning Right"); myRobot.stopRobot(); Timer.delay(0.010);}
		//if (turnLeft) {System.out.println("Doing Left Turn"); turnLeft = !myRobot.turnRobotLeft(90); System.out.println("Done Turning Left"); myRobot.stopRobot(); Timer.delay(0.010);}
		//if(follow) { follow = !myAutonRobot.followPath(); System.out.println("Done"); }
		if (turnLeft) {System.out.println("Doing Left Turn"); turnLeft = !myRobot.turnRobotLeftGyro(90); System.out.println("Done Turning Left"); myRobot.stopRobot(); Timer.delay(0.010);}
		myRobot.stopRobot();
		
		Timer.delay(0.005);
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {	
		
		// Initialize mecanum drive
		_drive = new MecanumDrive(_frontLeftMotor, _rearLeftMotor, _frontRightMotor, _rearRightMotor);		
		
		// Something to do with safety 
		_drive.setSafetyEnabled(false);
		_drive.setExpiration(0.1);
		
		// Set Max output
		_drive.setMaxOutput(0.6);	
		
		_frontRightMotor.setInverted(false);
		_rearRightMotor.setInverted(false);
		_frontLeftMotor.setInverted(false);
		_rearLeftMotor.setInverted(false);
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
			
		_drive.driveCartesian(myController.getLeftJoyX(), -myController.getLeftJoyY(), myController.getRightJoyX(), 0);

		//myClimber.checkSwitches(false);
		myElevator.checkSwitches(false);
		
		if (myController.getButtonY()) { myClimber.raiseElevator(.6); }
		else {myClimber.raiseElevator(0);}
		if (myController.getButtonA()) { myClimber.lowerElavator(.6); }
		else { myClimber.lowerElavator(0); }
		if (myController.getButtonX()) { myElevator.raiseElevator(.3); }
		if (myController.getButtonB()) { myElevator.lowerElevator(.3); }
		/*
		if (myController.getLeftTrigger() > 0) { myElevator.raiseElevator(myController.getLeftTrigger()*0.3); }
		else if (myController.getRightTrigger() < 0) { myElevator.lowerElevatorDis(myController.getRightTrigger()*0.3); }
		else { myElevator.raiseElevator(0); }
		*/
		if (myController.getButtonLeftBumber()) { myElevator.grabBlock(1); }
		else if (myController.getButtonRightBumber()) { myElevator.ejectBlock(1); }
		else { myElevator.ejectBlock(0); }
		
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
	}
	
	/* Ethans thoughts on TeleOp
	 * function to keep robot from curving when driving forward
	 * function to act as gateway between controller and any items the controller is activating if needed
	 * function to set robot to starting and/or ending configuration using a button on the controller
	 * function that activates the controller when auton ends so there is no chance of the controller interfering with auton
	 * function to disable any blocks black on the movement range of robot pieces to keep robot within allowed height/length/width/etc
	 * function that allows you to press a button on the controller and let go while the part activated keeps moving to allow for more things to be done at once if needed. EX: press a button versus hold for an evelvator to go up or down
	 * function that disables controller at end of teleop for a set amount of time so there's no chance of continuing to move the robot after time is up
	 * function to override auton and skip to teleop for testing or any other purpose using the controller or something else
	 * any other functions that fit best inside the TeleOp class 
	 * */
}