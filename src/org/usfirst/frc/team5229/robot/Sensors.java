package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
public class Sensors extends IterativeRobot {
	private AnalogInput SonicSideLeft = new AnalogInput(0);
	private AnalogInput SonicCenterLeft = new AnalogInput(1);
	private AnalogInput SonicCenterRight = new AnalogInput(2);
	private AnalogInput SonicSideRight = new AnalogInput(3);

	//Distance in inches the robot wants to stay from object,
	private static final double kHoldDistance = 12.0;
	//Max distance in inches we expect the robot to see
	private static final double kMaxDistance = 24.0;
	//factor to convert sensor value to distance  in inches
	private static final double kValueToInches = 0.125;
	//Proportional speed constant
	private static final double kP = 0.05;
	//Integral speed constant 
	private static final double kI = 0.018;
	//Derivative speed constant
	private static final double kD = 1.5;
	
	private static final int kLeftMotorPort = 0;
	private static final int kRightMotorPort = 1;
	private static final int kUltrasonicPort = 0;
	
	private AnalogInput ultrasonic = new AnalogInput(kUltrasonicPort);
	private RobotDrive myRobot = new RobotDrive(kLeftMotorPort, kRightMotorPort);
	private PIDController pidController = new PIDController(kP, kI, kD, ultrasonic, new MyPidOutput());
	
	public Sensors() {
		
	}
	
	@Override
	public void teleopInit() {
		//Set expected range to 0-24 inches; e.g. at from object go
		//full forward, at 0 inches from object go full backwards
		pidController.setInputRange(0, kMaxDistance * kValueToInches);
		//Set setpoint  of the pidController
		pidController.setSetpoint(kHoldDistance * kValueToInches);
		pidController.enable(); //Begin PID control
	}
	
	private class MyPidOutput implements PIDOutput {
		@Override
		public void pidWrite(double output) {
			myRobot.drive(output, 0);
		}
	}
	
	public void teleopPeriodic() {
		//Sensor returns a value from 0-4095 that is scaled to inches
		double currentDistance = ultrasonic.getValue() * kValueToInches;
		//Convert distance error to a motor speed
		double currentSpeed = (kHoldDistance - currentDistance) * kP;
		myRobot.drive(currentSpeed, 0);
	}
	
	//Description: Read the sensor and returns the distance in mm to a wall
	//in: Nothing
	//Out: double distance in mm
	public double SonicRightSide(){
		return SonicSideRight.getValue()*.125;
		
	}
	//Description: Read the sensor and returns the distance in mm to a wall
	//in: Nothing
	//Out: double distance in mm
	public double SonicLeftSide(){
		return SonicSideLeft.getValue()*.125;
		
	}
	//Description: Read the sensor and returns the distance in mm to a wall
	//in: Nothing
	//Out: double distance in mm
	public double SonicRightCenter(){
		return SonicCenterRight.getValue()*.125;
		
	}
	//Description: Read the sensor and returns the distance in mm to a wall
	//in: Nothing
	//Out: double distance in mm
	public double SonicLeftCenter(){
		return SonicCenterLeft.getValue()*.125;
		
	}
	//Description: Post the distance that the sensor is close to the wall in mm
	//In: nothing
	//Out: distance in mm
	public void test(){
		System.out.print(SonicRightSide());
		System.out.print(" "+SonicLeftCenter());
		System.out.print(" "+SonicRightCenter());
		System.out.println(" "+SonicLeftSide());		
	}
	
	//Color helps tell the robot what team it is on
	//In: nothing
	//Out: Color as int
	public int ColorSensors() {
		int color = 0;
		//Color in code goes here
		return color;
	}
	//This lets side and front sensors to tell the angle of how close the wall is
	//In: nothing
	//Out: angle 
	public float angle() {
		float angle = 0;
		
		return angle;
		
	}
}
