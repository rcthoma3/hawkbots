package org.usfirst.frc.team5229.robot;




import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Controller extends Joystick {

	public Controller(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	public Button a;
	public Button b;
	public Button x; 
	public Button y;
	public Button start;
	public Button select;
	public Button leftBumper;
	public Button rightBumper;
	public Button leftJoyButton;
	public Button rightJoyButton;
	public Timer rumbleTimer;
	
	public void XboxController(int port) {
		
		a = new JoystickButton(this, 1);
		b = new JoystickButton(this, 2);
		x = new JoystickButton(this, 3);
		y = new JoystickButton(this, 4);
		start = new JoystickButton(this, 8);
		select = new JoystickButton(this, 7);
		leftBumper = new JoystickButton(this, 5);
		rightBumper = new JoystickButton(this, 6);
		leftJoyButton = new JoystickButton(this, 9);
		rightJoyButton = new JoystickButton(this, 10);
	}
	
	/**
	 * Gets the X axis of the right Xbox joystick.
	 * @return The X axis of the right Xbox joystick.
	 */
	public double getX2() {
		return getRawAxis(4);
	}
	
	/**
	 * Gets the Y axis of the right Xbox joystick.
	 * @return The Y axis of the right Xbox joystick.
	 */
	public double getY2() {
		return getRawAxis(5);
	}
	
	/**
	 * Gives the current Direction of the DPad.
	 * @return The Direction of the DPad. Returns null if the DPad is not pressed.
	 */
	public Direction getDPad() {
		return Direction.toDirection(getPOV(0));
	}
	
	/**
	 * Gets the value of the left trigger.
	 * @return The value of the left trigger.
	 */
	public double getLeftTrigger() {
		return getRawAxis(2);
	}
	
	/**
	 * Gets the value of the right trigger.
	 * @return The value of the right trigger.
	 */
	public double getRightTrigger() {
		return getRawAxis(3);
	}
	
	/**
	 * Makes the controller rumble.
	 * @param l The left rumble value.
	 * @param r The right rumble value.
	 */
	public void rumble(float l, float r) {
		setRumble(RumbleType.kLeftRumble, l);
		setRumble(RumbleType.kRightRumble, r);
	}
	
	/**
	 * Makes the controller rumble for X seconds.
	 * @param l The left rumble value.
	 * @param r The right rumble value.
	 * @param seconds How long the controller should rumble.
	 */
	public void rumble(float l, float r, double seconds) {
		rumble(l, r);
		rumbleTimer = new Timer(seconds, false, new TimerUser() {
			public void timer() {
				rumble(0, 0);
			}
			public void timerStop() {
				rumbleTimer = null;
			}
		}).start();		
	}

	public enum Direction {
		UP(0),
		UP_RIGHT(45),
		RIGHT(90),
		DOWN_RIGHT(135),
		DOWN(180),
		DOWN_LEFT(225),
		LEFT(270),
		UP_LEFT(315),
		
		NONE(-1);
		
		public static Direction[] allDirections = new Direction[]{Direction.UP, Direction.UP_RIGHT, Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN, Direction.DOWN_LEFT, Direction.LEFT, Direction.UP_LEFT, Direction.NONE};
		public int angle;
		
		Direction(int angle) {
			this.angle = angle;
		}			
		
		public boolean isUp() {
			return (this == Direction.UP_LEFT || this == Direction.UP || this == Direction.UP_RIGHT);
		}
		
		public boolean isRight() {
			return (this == Direction.UP_RIGHT || this == Direction.RIGHT || this == Direction.DOWN_RIGHT);
		}
		
		public boolean isDown() {
			return (this == Direction.DOWN_LEFT || this == Direction.DOWN || this == Direction.DOWN_RIGHT);
		}
		
		public boolean isLeft() {
			return (this == Direction.UP_LEFT || this == Direction.LEFT || this == Direction.DOWN_LEFT);
		}
		
		public static Direction toDirection(int angle) {
			for (Direction d : allDirections) {
				if (d.angle == angle) {
					return d;
				}
			}
			return null;
		}
	}
}
		
	

























/*



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
		
		
		
		
		
		
		
		
		
		
		rumbleb
		
	}
	
}
*/	
	

