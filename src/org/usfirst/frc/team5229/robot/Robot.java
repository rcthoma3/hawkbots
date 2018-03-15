
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
	int pos;

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
		
		myAutonRobot.setAutoChooser();
		
		topClimbSwitch = new DigitalInput(topClimbPort);
		bottomClimbSwitch = new DigitalInput(bottomClimbPort);
		topElevatorSwitch = new DigitalInput(topElevatorPort);
		bottomElevatorSwitch = new DigitalInput(bottomElevatorPort);
		grabSwitch = new DigitalInput(grabSwitchPort);
		
		myClimber.setClimbMotor(_climbMotor);
		myClimber.initElevator();
		myClimber.setSwitches(topClimbSwitch, bottomClimbSwitch);
		myElevator.setElevator(_elevatorMotor);
		myElevator.initElevator();
		myElevator.setSwitches(topElevatorSwitch, bottomElevatorSwitch, grabSwitch);
		myElevator.setGrabMotors(_leftClawMotor, _rightClawMotor);
		
		myRobot.setEncoders (_frontLeftMotor, _rearLeftMotor, _frontRightMotor,  _rearRightMotor);
		myRobot.initEncoders();

		pos = myAutonRobot.getPositoin();
	}
	

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		
		myRobot.setWheelSize(whlSize);
		myRobot.setChassisSize(roboDim);	
		myRobot.setOverride(false);
		myRobot.setGyro(gyro);
		myAutonRobot.setSensor(myRobot);
		myAutonRobot.setElevator(myElevator);

		String gameMsg = myAutonRobot.getGameMsg();
		
		SmartDashboard.putString("Game message", gameMsg);
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
		
		// For Testing - Remove in final code
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
		
		//if (forward) {System.out.println("Going Forward"); forward = !myRobot.driveFowardAuto(240); System.out.println("Done Going Forward"); myRobot.stopRobot(); .delay(0.010);}
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
	}

	/**
	 * This function is called periodically during operator control 
	 */
	@Override
	public void teleopPeriodic() {
	    //TODO: Change all myController to myDriveController
		_drive.driveCartesian(myDriveController.getLeftJoyX(), -myDriveController.getLeftJoyY(), myDriveController.getRightJoyX(), 0);	
		
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
}
