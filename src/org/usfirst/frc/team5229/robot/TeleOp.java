package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class TeleOp {

	public void mechInit(MecanumDrive _drive) {
		_drive.setMaxOutput(.6);
	}
	/* function to keep robot from curving when driving forward
	 * function to act as gateway between controller and any items the controller is activating if needed
	 * function to set robot to starting and/or ending configuration using a button on the controller
	 * function that activates the controller when auton ends so there is no chance of the controller interfering with auton
	 * function to disable any blocks black on the movement range of robot pieces to keep robot within allowed height/length/width/etc
	 * function that allows you to press a button on the controller and let go while the part activated keeps moving to allow for more things to be done at once if needed. EX: press a button versus hold for an evelvator to go up or down
	 * function that disables controller at end of teleop for a set amount of time so there's no chance of continuing to move the robot after time is up
	 * function to override auton and skip to teleop for testing or any other purpose using the controller or something else
	 * any other functions that fit best inside the TeleOp class */
}
