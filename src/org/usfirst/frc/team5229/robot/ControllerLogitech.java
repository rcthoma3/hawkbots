package org.usfirst.frc.team5229.robot;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
public class ControllerLogitech {
 public static int kButtonA = 1;
 public static int kButtonB = 2;
 public static int kButtonX = 3;
 public static int kButtonY = 4;
 public static int kButtonLeftBumber = 5;
 public static int kButtonRightBumber = 6;
 public static int kButtonBack = 7;
 public static int kButtonStart = 8;
 public static int kBottomLeftStickPress = 9;
 public static int kButtonRightStickPress = 10;
 public Joystick stick = new Joystick(1);
 public Timer rumbleTimer;
 boolean aWasPressed=false;
 boolean aLast=false;
 boolean bWasPressed=false;
 boolean bLast=false;
 boolean xWasPressed=false;
 boolean xLast=false;
 boolean yWasPressed=false;
 boolean yLast=false;
 boolean startWasPressed=false;
 boolean startLast=false;
 // This method will check to see if any of the buttons have been pressed, every tick.
 public void updateController() {
  if (!aLast && getButtonA())
   aWasPressed=true;  
  else 
   aWasPressed=false;
  
  if (!bLast && getButtonB())
   bWasPressed=true;  
  else 
   bWasPressed=false;
  
  if (!xLast && getButtonX())
   xWasPressed=true;  
  else 
   xWasPressed=false;
  
  if (!yLast && getButtonY())
   yWasPressed=true;  
  else 
   yWasPressed=false;
  
  if (!startLast && getButtonStart())
   startWasPressed=true;  
  else 
   startWasPressed=false;
  
  aLast = getButtonA();
  bLast = getButtonB();
  xLast = getButtonX();
  yLast = getButtonY();
  startLast = getButtonStart();
 }
 
 // The following functions return the boolean value <button>WasPressed depending on which was called.
 public boolean aWasPressed() {
  return aWasPressed;
 }
 
 public boolean bWasPressed() {
  return bWasPressed;
 }
 public boolean xWasPressed() {
  return xWasPressed;
 }
 public boolean yWasPressed() {
  return yWasPressed;
 }
 public boolean startWasPressed() {
  return startWasPressed;
 }
 
 // The following functions all return the raw value of the respective control.
 
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
 
 //Is the start button pressed or not
 //in: nothing
 //out: is a button pressed, boolean
 public boolean getButtonStart() {
  return stick.getRawButton(kButtonStart);  
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
  return stick.getRawButton(kButtonLeftBumber);
 }
 
 //Is RB button pressed or not
 //in: nothing
 //out: is a button pressed, boolean
 public boolean getButtonRightBump() {
  return stick.getRawButton(kButtonRightBumber);
 }
 
 //This tells whether or not everything on the controller works or not by printing
 // the value of the button if it's respective button is pressed.
 public void test() {
  boolean buttonA = getButtonA();
  boolean buttonB = getButtonB();
  boolean buttonX = getButtonX();
  boolean buttonY = getButtonY();
  boolean buttonStart = getButtonStart();
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
  if (buttonA)
   System.out.println("A="+buttonA);
  if (buttonB)
   System.out.println(" B="+buttonB);
  if (buttonX)
   System.out.println(" X="+buttonX);
  if (buttonY)
   System.out.println(" Y="+buttonY);
  if (buttonStart)
   System.out.println(" Start="+buttonStart);
  if (buttonUpD)
   System.out.println("UD="+buttonUpD);
  if (buttonDownD)
   System.out.println(" DD="+buttonDownD);
  if (buttonDownD)
   System.out.println(" RD="+buttonRightD);
  if (buttonLeftD)
   System.out.println(" LD="+buttonLeftD);
  if (buttonLeftBump) {
   System.out.print(" LT="+buttonLeftTrigger); 
   System.out.print(" LJ="+buttonLeftJoy);
   System.out.print(" LX="+axisLeftJoyX);
   System.out.print(" LY="+axisLeftJoyY);  
   System.out.println(" LB="+buttonLeftBump);
  }
  if(buttonRightBump) {       
   System.out.print(" RJ="+buttonRightJoy);
   System.out.print(" RT="+buttonRightTrigger);
   System.out.print(" RX="+axisRightJoyX);
   System.out.println(" RY="+axisRightJoyY);
  }
  if (axisLeftJoyY > .2 || axisLeftJoyX > .2 ){
   System.out.println(" Left Joystick = "+ axisLeftJoyY + "   " + axisLeftJoyX);
  }
  if (axisRightJoyY > .2 || axisRightJoyX > .2 ){
   System.out.println(" Right Joystick = "+ axisRightJoyY + "   " + axisRightJoyX);
  }
  
 }
}
