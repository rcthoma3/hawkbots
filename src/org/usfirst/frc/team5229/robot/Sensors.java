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
	private AnalogInput SonicSideLeft = new AnalogInput(3);
	private AnalogInput SonicCenterLeft = new AnalogInput(2);
	private AnalogInput SonicCenterRight = new AnalogInput(1);
	private AnalogInput SonicSideRight = new AnalogInput(0);
	//Keeps track of rolling average
	private double average = 0;
	

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

	
	public Sensors() {}	
	
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
		System.out.print("Right Side:  "+SonicRightSide());
		System.out.print(" Right Center: "+SonicRightCenter());
		System.out.print(" Left Center: "+SonicLeftCenter());
		System.out.println(" Left Side: "+SonicLeftSide());		
	}
	//Calculating Rolling average
	public void rollingAverage(double nextValue) {
		average = average * ((5-1)/5) + nextValue/5;
	}
	//The average between the two sensors
	public double instantAverage() {
		return (SonicLeftCenter() + SonicRightCenter()) /2;
	}
	
	public double getAverage(){
		return average;
	}
	
}
