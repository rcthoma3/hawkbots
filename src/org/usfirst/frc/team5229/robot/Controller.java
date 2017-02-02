package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {
	public static int kButtonA = 0;
	public static int kButtonB = 1;
	public static int kButtonX = 2;
	public static int kButtonY = 3;
	public Joystick stick = new Joystick(0);
	
	

	
	
	//Controls the X axis of the right joystick
	public double getRightJoyX() {
		return stick.getRawAxis(4);
	}
	
	
	//Controls the Y axis of the right joystick
	public double getRightJoyY() {
		return stick.getRawAxis(5);
	}
	
	
	//Controls the X axis of the left joystick
	public double getLeftJoyX() {
		return stick.getRawAxis(0);
	}
	
	
	
	//Controls the Y axis of the left joystick
	public double getLeftJoyY() {
		return stick.getRawAxis(1);
	}
	
	
	
	
	//Is the A button pressed or not
	//in: nothing
	//out: is A button pressed, boolean
	public boolean getButtonA() {
		return stick.getRawButton(kButtonA);
	}
	
	

	//Is the Right Trigger button pressed or not
	//in: nothing
	//out: is a button pressed, double
	public double RightTrigger() {				
		return stick.getRawAxis(3);
	}
	
	
	//Is the Left Trigger button pressed or not
	//in: nothing 
	//out: is a button pressed, double
	public double LeftTrigger() {
		return stick.getRawAxis(2);
	}
	
	
		
	//Is the B button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean getButtonB() {
	return stick.getRawButton(kButtonB);
		
	}
	
	//Is the X button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean getButtonX() {
		return stick.getRawButton(kButtonX);
		
	}
	
	//Is the Y button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean getButtonY() {
		return stick.getRawButton(kButtonY);
		
	}
	
	//Is Up D pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean isUpDPressed() {
		boolean isPressed = false;
		
		return isPressed;
		
	}
	
	//Is Down D button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean isDownDPressed() {
		boolean isPressed = false;
		
		return isPressed;
		
	}
	
	//Is Right D pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean isRightDPressed() {
		boolean isPressed = false;
		
		return isPressed;
		
	}
	
	//Is Left D pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean isLeftDPressed() {
		boolean isPressed = false;
		
		
		return isPressed;
		
		
	}
	
	//Is the Left Trigger button pressed or not
	//in: nothing
	//out: is a button pressed, float
	public float leftTrigger() {
		float rt=0;
		return rt;
		
	}
	
	//Is the Right Joy button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean isRJoyPressed() {
		boolean isPressed = false;
		
		return isPressed;
	
	}
	
	//Is the Left Joy button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean isLJoyPressed() {
		boolean isPressed = false;
		
		return isPressed;
		
	}
	
	//Is LB button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean isLBPressed() {
		boolean isPressed = false;
		
		return isPressed;
		
	}
	
	//Is RB button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean isRBPressed() {
		boolean isPressed = false;
		
		return isPressed;
	}
	
}
	
	

