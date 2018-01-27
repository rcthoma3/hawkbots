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
	
	ControllerLogitech myController = new ControllerLogitech();
	
	//UsbCamera Camera1;
	//UsbCamera Camera2;
	
	WPI_TalonSRX _frontLeftMotor = new WPI_TalonSRX(6); 
	WPI_TalonSRX _rearLeftMotor = new WPI_TalonSRX(8);
	WPI_TalonSRX _frontRightMotor = new WPI_TalonSRX(5);
	WPI_TalonSRX _rearRightMotor = new WPI_TalonSRX(7);
	
	//MecanumDrive _drive = new MecanumDrive(_frontLeftMotor, _rearLeftMotor, _frontRightMotor, _rearRightMotor);
	
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
		
		// Something to do with safety 
		//_drive.setSafetyEnabled(true);
		//_drive.setExpiration(0.1);
		
		_frontLeftMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
		_frontLeftMotor.setSensorPhase(false);
		_rearLeftMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
		_rearLeftMotor.setSensorPhase(false);
		_frontRightMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
		_frontRightMotor.setSensorPhase(true);
		_rearRightMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
		_rearRightMotor.setSensorPhase(true);
		
		/* set the peak and nominal outputs, 12V means full */
		_frontLeftMotor.configNominalOutputForward(0, 10);
		_frontLeftMotor.configNominalOutputReverse(0, 10);
		_frontLeftMotor.configPeakOutputForward(1, 10);
		_frontLeftMotor.configPeakOutputReverse(-1, 10);
        
		/* first param is the slot, second param is generally zero (for primary PID loop) */
		_frontLeftMotor.selectProfileSlot(0, 0);
		_frontLeftMotor.config_kF(0, 0, 10);
		_frontLeftMotor.config_kP(0, .5, 10);
		_frontLeftMotor.config_kI(0, 0, 10);
		_frontLeftMotor.config_kD(0, 0, 10);
	}
	

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
		
		//_drive.setMaxOutput(.6);		
	}


	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {	
		
		
		//_frontLeftMotor.set(ControlMode.Velocity, 100);
		_frontLeftMotor.set(ControlMode.Position, 100);

		//_drive.driveCartesian(myController.getLeftJoyX(), -myController.getLeftJoyY(), myController.getRightJoyX(), 0); // Found in example
		
		
		System.out.println("FrontLeftMotor Encoder" + _frontLeftMotor.getSelectedSensorPosition(0));
		//System.out.println("RearLeftMotor Encoder" + _rearLeftMotor.getSelectedSensorPosition(0));
		//System.out.println("FrontRightMotor Encoder" + _frontRightMotor.getSelectedSensorPosition(0));
		//System.out.println("RearRightMotor Encoder" + _rearRightMotor.getSelectedSensorPosition(0));

		Timer.delay(0.005); // Saw this in an example
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
		
	}
}