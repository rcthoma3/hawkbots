package org.usfirst.frc.team5229.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	ControllerLogitech myController = new ControllerLogitech();
	//RobotMovement myRobot = new RobotMovement(myController);
	//Sensors mySensors = new Sensors();
	//auto myAuto = new auto(mySensors,myRobot);
	//public static int kBasePort;
	//public static int kSize640x480;
	//public static int kSize320x240;
	//public static int kSize160x120;
	//boolean aLast = false;
	//boolean aWasPressed = false;
	//boolean bLast = false;
	//boolean xLast = false;
	//boolean yLast = false;
	UsbCamera Camera1;
	UsbCamera Camera2;
	
	//Timer timer = new Timer();
	//https://mililanirobotics.gitbooks.io/frc-electrical-bible/content/Drive_Code/custom_program_mecanum_drive.html
	
	/*
	 * IMPORTANT UPDATES 1/11/2018
	 * Updated FRC Plugins, due to deprecated RobotDrive functionality.
	 * MecanumDrive functions replaced RobotDrive.
	 * CANTalon temporarily replaced by Talon (CANTalon isn't recognized properly for the updated library)
	 * Talon libraries may need to be updated (Check their documentation)
	 * Main branch has been moved to Master2018
	 * ControllerLogitech has been updated on previous Master branch, needs to be merged into Master2018.
	 * For compiling with the new libraries, Roborio needs to be reimaged. This can be a TODO for Wednesday, 1/17/2018
	 */
	
	Talon _frontLeftMotor = new Talon(7); 
	Talon _rearLeftMotor = new Talon(5);
	Talon _frontRightMotor = new Talon(8);
	Talon _rearRightMotor = new Talon(6);
	
	//public Joystick stick = new Joystick(0);
	MecanumDrive _drive = new MecanumDrive(_frontLeftMotor, _rearLeftMotor, _frontRightMotor, _rearRightMotor);
	
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		Camera1 = CameraServer.getInstance().startAutomaticCapture();
		Camera2 = CameraServer.getInstance().startAutomaticCapture();
		
		//_frontLeftMotor.changeControlMode(TalonControlMode.Voltage); 
		//_rearLeftMotor.changeControlMode(TalonControlMode.Voltage);
		//_frontRightMotor.changeControlMode(TalonControlMode.Voltage);
		//_rearRightMotor.changeControlMode(TalonControlMode.Voltage);
		
		// One side will be inverted, but may not be left
		_frontLeftMotor.setInverted(true);
		_rearLeftMotor.setInverted(true);
		
		// Initialize to zero
		_frontLeftMotor.set(0);
		_rearLeftMotor.set(0);
		_frontRightMotor.set(0);
		_rearRightMotor.set(0);
		
		// Something to do with safety 
		_drive.setSafetyEnabled(true);
		_drive.setExpiration(0.1);
	}
	
	/**
	 * 
	 */
	

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'L')
		{
			//Put left auto code here
		} else {
			//Put right auto code here
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {	
		
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {	
		_drive.setMaxOutput(.6);
	}


	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {	
		
		_drive.driveCartesian(myController.getLeftJoyX(), myController.getLeftJoyY(), -myController.getRightJoyX(), 0); // Found in example
		//_drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.TwistZ(), 0); // Found in example FRC
		//_drive.mecanumDrive_Cartesian(stick.getX(GenericHID.Hand.kLeft), stick.getY(GenericHID.Hand.kLeft), stick.getX(GenericHID.Hand.kRight), 0); // My Idea
		Timer.delay(0.005); // Saw this in an example
		
		// Possible Improvements
		/*
		double leftYjoystick = stick.getY(GenericHID.Hand.kLeft);
		double leftXjoystick = stick.getX(GenericHID.Hand.kLeft);
		//double rightYjoystick = stick.getY(GenericHID.Hand.kRight);
		double rightXjoystick = stick.getX(GenericHID.Hand.kRight);
		
		double lfPower = (-leftXjoystick + leftYjoystick + rightXjoystick)/3;
		double lbPower = (leftXjoystick + leftYjoystick + rightXjoystick)/3;
		double rfPower = (leftXjoystick + leftYjoystick + rightXjoystick)/3;
		double rbPower = (-leftXjoystick + leftYjoystick + rightXjoystick)/3;
		
		_frontLeftMotor.set(12.0 * lfPower);
		_rearLeftMotor.set(12.0 * rfPower);
		_frontRightMotor.set(12.0 * lbPower);
		_rearRightMotor.set(12.0 * rbPower);
		*/

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}