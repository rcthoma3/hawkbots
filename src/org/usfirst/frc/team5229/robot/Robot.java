package org.usfirst.frc.team5229.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Controller myController = new Controller();
	RobotMovement myRobot = new RobotMovement(myController);
	Sensors mySensors = new Sensors();
	auto myAuto = new auto(mySensors,myRobot);
	public static int kBasePort;
	public static int kSize640x480;
	public static int kSize320x240;
	public static int kSize160x120;
	boolean aLast = false;
	boolean aWasPressed = false;
	boolean bLast = false;
	boolean xLast = false;
	boolean yLast = false;
	UsbCamera Camera1;
	UsbCamera Camera2;
	//Test Movement
	//RobotDrive drive = new RobotDrive(0,1,2,3); //4 motor drive
	
	CANTalon _frontLeftMotor = new CANTalon(7); 
	CANTalon _rearLeftMotor = new CANTalon(5);
	CANTalon _frontRightMotor = new CANTalon(8);
	CANTalon _rearRightMotor = new CANTalon(6);
	RobotDrive _drive = new RobotDrive(_frontLeftMotor, _rearLeftMotor, _frontRightMotor, _rearRightMotor);
	
	Timer timer = new Timer();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		Camera1 = CameraServer.getInstance().startAutomaticCapture();
		Camera2 = CameraServer.getInstance().startAutomaticCapture();
		myRobot.init();
		myAuto.StartAutoTimer();
	}
	
	/**
	 * 
	 */
	

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {		
		System.out.println("Auto Init");
		timer.reset();
		timer.start();		
		myAuto.StartAutoTimer();
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//System.out.println("auto per");
		mySensors.update();
		myAuto.AutoTesting();
		
		
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {	
	
	}


	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {	
		myController.updateController();
		myRobot.tick();
		myRobot.doDriveType();
		myRobot.coveyormotorwork();
		//myRobot.ballmotorwork();
		mySensors.update();
		
		mySensors.test();
		
		//When ever the A button is pressed the mode is
		//set to either arcade or tank (depending on current mode)
		if (myController.aWasPressed()) {
			System.out.println("AAAAAAA");			
			if (myRobot.ismodeArcade())
				myRobot.setmodeTank();
			else
				myRobot.setmodeArcade();
		}
		
		if (myController.bWasPressed() && myRobot.conveyorSwitch==false) {
		//	myRobot.ConveyerOn();
		} else if (myController.bWasPressed() && myRobot.ConvayerSwitch) {
		//	myRobot.ConveyerOff();
		}
 		
		//If right trigger is fully depressed, the
		//mode is set to fine. Otherwise the mode is
		//being set to coarse.
		if (myController.xWasPressed() && myRobot.ismodeFine()==false){
			myRobot.setmodeFine();
		}else if (myController.xWasPressed() && myRobot.ismodeFine()==true){
			myRobot.setmodeCoarse();
		}
		
		if (myController.getButtonY())
			myAuto.straightenOut();
		
		if (myController.getButtonUpD() || myController.getButtonRightBump()) {
			myRobot.climbmotormovement(-1);		
			System.out.println("D UP");
		} else if (myController.getButtonDownD() || myController.getButtonLeftBump()  ) {
			//System.out.println("D DOWN");
			myRobot.climbmotormovement(-.5);
		} else {			
			myRobot.climbmotormovement(0);			
		};	
		
		if (myController.getButtonRightD()) {
			myRobot.setDoorMotorSpeed(1);
		} else if (myController.getButtonLeftD()) {
			myRobot.setDoorMotorSpeed(-1);
		} else {
			myRobot.setDoorMotorSpeed(0);
		}
		
		myController.test();
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}