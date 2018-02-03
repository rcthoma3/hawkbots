package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	ControllerLogitech myController = new ControllerLogitech();
	Climbing myClimber = new Climbing();
	Elevator myElevator = new Elevator();
	Sensors myAutonRobot = new Sensors();
	
	//UsbCamera Camera1;
	//UsbCamera Camera2;
	
	MecanumDrive _drive;
	
	// Motor Declerations 
	WPI_TalonSRX _frontLeftMotor; 
	WPI_TalonSRX _rearLeftMotor;
	WPI_TalonSRX _frontRightMotor;
	WPI_TalonSRX _rearRightMotor;
	//TODO: Declare elevator motor
	//TODO: Declare climb motor
	//TODO: Declare left claw motor
	//TODO: Declare right claw motor

	// These values correspond to roboRIO ports
	// TODO: Global variable for top elevator switch 
	// TODO: Global variable for bottom elevator switch
	// TODO: Global variable for top climb switch
	// TODO: Global variable for bottom climb switch
	// TODO: Global Variable for left claw motor
	// TODO: Global Variable for right claw motor
	// TODO: Global Variable for elevator motor
	// TODO: Global Variable for climb motor
	
	double whlSize = 8; // Wheel diameter in inches
	double roboDim = 30; // Diagonal distance between wheels in inches
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		_frontLeftMotor = new WPI_TalonSRX(6); 
		_rearLeftMotor = new WPI_TalonSRX(8);
		_frontRightMotor = new WPI_TalonSRX(5);
		_rearRightMotor = new WPI_TalonSRX(7);
		
		//Camera1 = CameraServer.getInstance().startAutomaticCapture();
		//Camera2 = CameraServer.getInstance().startAutomaticCapture();
		
		// Initialize to zero
		_frontLeftMotor.set(0);
		_rearLeftMotor.set(0);
		_frontRightMotor.set(0);
		_rearRightMotor.set(0);
		
		//TODO: setAutoChooser	
	}
	

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		
		myAutonRobot.setEncoders (_frontLeftMotor, _rearLeftMotor, _frontRightMotor,  _rearRightMotor);
		myAutonRobot.setWheelSize(whlSize);
		myAutonRobot.setChassisSize(roboDim);	
		myAutonRobot.initEncoders();
		//TODO: getGameMsg
		//TODO: getPosition
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {	
		
		myAutonRobot.driveFowardAuto(120);
		
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
		_drive.setSafetyEnabled(true);
		_drive.setExpiration(0.1);
		
		// Set Max output
		_drive.setMaxOutput(0.6);	
		
		PWM climbMotor = new PWM(0); // TODO: change PWM to Victor SP
		myClimber.setClimbMotor(climbMotor);
		myClimber.setSwitches(0);	
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {	
		
		_drive.driveCartesian(myController.getLeftJoyX(), -myController.getLeftJoyY(), myController.getRightJoyX(), 0);

		if (myController.getButtonY()) { myClimber.raiseElevator(.3); }
		if (myController.getButtonA()) { myClimber.lowerElavator(.3); }
		// TODO: raise front elevator to max
		// TODO: lower front elevator to min
		// TODO: raise front elevator to dis
		// TODO: lower front elevator to dis
		// TODO: grab block
		// TODO: eject block
		
		Timer.delay(0.005);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
		
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