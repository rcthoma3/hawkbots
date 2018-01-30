package org.usfirst.frc.team5229.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
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
	
	MecanumDrive _drive;
	ControllerLogitech myController = new ControllerLogitech();
	
	//UsbCamera Camera1;
	//UsbCamera Camera2;
	
	Sensors myRobot = new Sensors();
	
	WPI_TalonSRX _frontLeftMotor = new WPI_TalonSRX(6); 
	WPI_TalonSRX _rearLeftMotor = new WPI_TalonSRX(8);
	WPI_TalonSRX _frontRightMotor = new WPI_TalonSRX(5);
	WPI_TalonSRX _rearRightMotor = new WPI_TalonSRX(7);
	
	double whlSize = 8; // Wheel diameter in inches
	double roboDim = 30; // Diagonal distance between wheels in inches
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		//Camera1 = CameraServer.getInstance().startAutomaticCapture();
		//Camera2 = CameraServer.getInstance().startAutomaticCapture();
		
		// Initialize to zero
		_frontLeftMotor.set(0);
		_rearLeftMotor.set(0);
		_frontRightMotor.set(0);
		_rearRightMotor.set(0);
		
		myRobot.setEncoders (_frontLeftMotor, _rearLeftMotor, _frontRightMotor,  _rearRightMotor);
		myRobot.setWheelSize(whlSize);
		myRobot.setChassisSize(roboDim);
		
	}
	

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		
		myRobot.initEncoders();
		
		String gameData = "LRL";
		//gameData = DriverStation.getInstance().getGameSpecificMessage();
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
		
		myRobot.driveFowardAuto(120);
		
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
	}


	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {	
		
		_drive.driveCartesian(myController.getLeftJoyX(), -myController.getLeftJoyY(), myController.getRightJoyX(), 0); // Found in example

		Timer.delay(0.005); // Saw this in an example
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
		
	}
}