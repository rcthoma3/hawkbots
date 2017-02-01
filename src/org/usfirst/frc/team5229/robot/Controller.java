package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {
	public static float kButtonA = 1;
	public static float kButtonB = 2;
	public static float kButtonX = 3;
	public Joystick stick = new Joystick(0);
	
	public boolean getRawButton(float kButtonA2){
		return false;
	}
	public boolean getRawButton1(float kButtonB2){
		return false;
	}
	public boolean getRawButton2(float kButtonX2){
		return false;
	}

	//Is the A button pressed or not
	//in: nothing
	//out: is A button pressed, boolean
	public boolean getButtonA() {
		return getRawButton(kButtonA);
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
	return getRawButton1(kButtonB);
		
	}
	
	//Is the X button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean getButtonX() {
		return getRawButton2(kButtonX);
		
	}
	
	//Is the Y button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean isYPressed() {
		boolean isPressed = false;
		
		return isPressed;
		
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
	
	

