package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
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
	
	boolean aLast = false;
	boolean aWasPressed = false;
	boolean bLast = false;
	boolean xLast = false;
	boolean yLast = false;
	
	//Test Movement
	//RobotDrive drive = new RobotDrive(0,1,2,3); //4 motor drive
	
	Timer timer = new Timer();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	@Override
	public void robotInit() {
		//CameraServer.getInstance().startAutomaticCapture("cam0", 1);
		myRobot.init();			
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
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
	
	}


	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {	
		/*
		if (myRobot.climbing){
			myRobot.climbmotormovement(1.0);
		}
		
		myController.updateController();
		myRobot.doDriveType();
		if (myController.yWasPressed()){
			if (myRobot.climbing){
				myRobot.climbing = false;
			}
			else{
				myRobot.climbing = true;
			}
		}
		*/
		//If right trigger is fully depressed, the
		//mode is set to fine. Otherwise the mode is
		//being set to coarse.
		if (myController.stick.getRawAxis(3) >= .5){
			myRobot.setmodeFine();
		}else{
			myRobot.setmodeCoarse();
		}
			
		//When ever the A button is pressed the mode is
		//set to either arcade or tank (depending on current mode)
		if (myController.aWasPressed()) {
			System.out.println("AAAAAAA");
			if (myRobot.ismodeArcade())
				myRobot.setmodeTank();
			else
				myRobot.setmodeArcade();
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
