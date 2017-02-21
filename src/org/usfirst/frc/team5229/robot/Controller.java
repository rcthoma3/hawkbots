package org.usfirst.frc.team5229.robot;




import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Controller {
	public static int kButtonA = 0;
	public static int kButtonB = 1;
	public static int kButtonX = 2;
	public static int kButtonY = 3;
	public Joystick stick = new Joystick(0);
	public Timer rumbleTimer;
	
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
	public boolean getButtonUpD() {
		boolean isPressed = false;
		
		return isPressed;
		
	}
	
	//Is Down D button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean getButtonDownD() {
		boolean isPressed = false;
		
		return isPressed;
		
	}
	
	//Is Right D pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean getButtonRightD() {
		boolean isPressed = false;
		
		return isPressed;
		
	}
	
	//Is Left D pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean getButtonLeftD() {
		boolean isPressed = false;
		
		
		return isPressed;
		
		
	}
	
	//Is the Right Trigger button pressed or not
	//in:nothing
	//out: is a button pressed, float
	public int getRightTrigger() {
		int rt=0;
		return rt;
	}
	
	
	
	
	//Is the Left Trigger button pressed or not
	//in: nothing
	//out: is a button pressed, float
	public int getLeftTrigger() {
		int rt=0;
		return rt;
		
	}
	
	//Is the Right Joy button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean getButtonRightJoy() {
		boolean isPressed = false;
		
		return isPressed;
	
	}
	
	//Is the Left Joy button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean getButtonLeftJoy() {
		boolean isPressed = false;
		
		return isPressed;
		
	}
	
	//Is LB button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean getButtonLeftBump() {
		boolean isPressed = false;
		
		return isPressed;
		
	}
	
	//Is RB button pressed or not
	//in: nothing
	//out: is a button pressed, boolean
	public boolean getButtonRightBump() {
		boolean isPressed = false;
		
		return isPressed;
		
	}
	//This tells whether or not everything on the controller works or not
	public void test() {
		boolean buttonA = getButtonA();
		boolean buttonB = getButtonB();
		boolean buttonX = getButtonX();
		boolean buttonY = getButtonY();
		boolean buttonUpD = getButtonUpD();
		boolean buttonDownD = getButtonDownD();
		boolean buttonRightD = getButtonRightD();
		boolean buttonLeftD = getButtonLeftD();
		boolean buttonRightJoy = getButtonRightJoy();
		boolean buttonLeftJoy = getButtonLeftJoy();
		boolean buttonLeftBump = getButtonLeftBump();
		boolean buttonRightBump = getButtonRightBump();
		int buttonLeftTrigger = getLeftTrigger();
		int buttonRightTrigger = getRightTrigger();
		double axisRightJoyX = getRightJoyX();
		double axisRightJoyY = getRightJoyY();
		double axisLeftJoyX = getLeftJoyX();
		double axisLeftJoyY = getLeftJoyY();
		System.out.print("A="+buttonA);
		System.out.print("B="+buttonB);
		System.out.print("X="+buttonX);
		System.out.print("Y="+buttonY);
		System.out.print("UpD="+buttonUpD);
		System.out.print("DownD="+buttonDownD);
		System.out.print("RightD="+buttonRightD);
		System.out.print("LeftD="+buttonLeftD);
		System.out.print("RightJoy="+buttonRightJoy);
		System.out.print("LeftJoy="+buttonLeftJoy);
		System.out.print("LeftBump="+buttonLeftBump);
		System.out.print("RightBump="+buttonRightBump);
		System.out.print("LeftTrigger="+buttonLeftTrigger);
		System.out.print("RightTrigger="+buttonRightTrigger);
		System.out.print("RightX="+axisRightJoyX);
		System.out.print("RightY="+axisRightJoyY);
		System.out.print("LeftX="+axisLeftJoyX);
		System.out.print("LeftY="+axisLeftJoyY);
	}
}
	

