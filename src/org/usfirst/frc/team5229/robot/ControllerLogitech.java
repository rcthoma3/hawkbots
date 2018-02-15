package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.Joystick;

//Logitec Gamepad F310
public class ControllerLogitech 
{
	private Joystick stick;
	
	public ControllerLogitech (int joyStickIn) {
		stick = new Joystick(joyStickIn);
	}
	
	// Initializing buttons 
	private int kButtonA = 1;
	private int kButtonB = 2;
	private int kButtonX = 3;
	private int kButtonY = 4;
	private int kButtonLeftBumber = 5;// new button
	private int kButtonRightBumber = 6; // new button
	private int kButtonBack = 7; // new button
	private int kButtonStart = 8;
	private int kButtonLeftStickPress = 9; // new button
	private int kButtonRightStickPress = 10; // new button
	// Initializing axis
	private int kAxisRightJoyX = 4;
	private int kAxisRightJoyY = 5;
	private int kAxisLeftJoyX = 0;
	private int kAxisLeftJoyY = 1;
	private int kAxisTrigger = 3;
	
	

 // The following functions all return the raw value of the respective control.
 //Keep
 //Controls the X axis of the right joystick
 public double getRightJoyX() {
	 return stick.getRawAxis(kAxisRightJoyX);
 }
 
 //Controls the Y axis of the right joystick
 public double getRightJoyY() {
	 return stick.getRawAxis(kAxisRightJoyY);
 }
 
 //Controls the X axis of the left joystick
 public double getLeftJoyX() {
	 return stick.getRawAxis(kAxisLeftJoyX);
 }
 
 //Controls the Y axis of the left joystick
 public double getLeftJoyY() {
	 return stick.getRawAxis(kAxisLeftJoyY);
 }
  
 //Is the A button pressed or not
 //in: nothing
 //out: is A button pressed, boolean
 public boolean getButtonA() {
	 return stick.getRawButton(kButtonA);
 }
 
 //Is the Right Trigger button pressed or not
 //in: nothing
 //out: is a button pressed, double between 0 and -1
 public double getRightTrigger() {    
	 return stick.getRawAxis(kAxisTrigger);
 }
 
 //Is the Left Trigger button pressed or not
 //in: nothing 
 //out: is a button pressed, double between 0 and 1
 public double getLeftTrigger() {
	 return stick.getRawAxis(kAxisTrigger);
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
 /* Is the left Bumber button pressed or not
  * in: nothing
  * out: is a button pressed, boolean
  */
 public boolean getButtonLeftBumber()
 {
	 return stick.getRawButton(kButtonLeftBumber);
 }
 
 //Is RB button pressed or not
 //in: nothing
 //out: is a button pressed, boolean
 public boolean getButtonRightBumber() {
	 return stick.getRawButton(kButtonRightBumber);
 }

 
 //Is the start button pressed or not
 //in: nothing
 //out: is a button pressed, boolean
 public boolean getButtonStart() {
	 return stick.getRawButton(kButtonStart);  
 }
 
 /* Is the back button pressed or not 
  * in: nothing
  * out: is a button pressed, boolean
  */
 public boolean getButtonBack()
 {
	 return stick.getRawButton(kButtonBack);
 }
/*Is the left stick press button pressed or not
 * in: nothing
 * out: is a button pressed, boolean
 */
 public boolean getButtonLeftStickPress()
 {
	 return stick.getRawButton(kButtonLeftStickPress);
 }
 /*Is the right stick press button pressed or not
  * in: nothing 
  * out: is a button pressed, boolean
  */
 public boolean getButtonRightStickPress()
 {
	 return stick.getRawButton(kButtonRightStickPress);
 }
 
 //Is Up D pressed or not
 //in: nothing
 //out: is a button pressed, boolean
 public boolean getButtonUpD() {
	 return (stick.getPOV()>270 || stick.getPOV()<90) && stick.getPOV()>=0;
 }
 
 //Is Down D button pressed or not
 //in: nothing
 //out: is a button pressed, boolean
 public boolean getButtonDownD() {
	 return stick.getPOV()>90 && stick.getPOV()<270;  
 }
 
 //Is Right D pressed or not
 //in: nothing
 //out: is a button pressed, boolean
 public boolean getButtonRightD() {
	 return stick.getPOV()==90;
 }
 
 //Is Left D pressed or not
 //in: nothing
 //out: is a button pressed, boolean
 public boolean getButtonLeftD() {
	 return stick.getPOV()==270;
 }
 //KEEP
}
 //KEEP

