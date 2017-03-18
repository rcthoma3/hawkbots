package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.AnalogInput;

public class Sensors extends IterativeRobot {
	private AnalogInput SonicSideLeft = new AnalogInput(0);
	private AnalogInput SonicCenterLeft = new AnalogInput(1);
	private AnalogInput SonicCenterRight = new AnalogInput(2);
	private AnalogInput SonicSideRight = new AnalogInput(3);

	private static final double rawToMM = 0.125;
	
	public Sensors() {
		
	}
	
	
	//Description: Read the sensor and returns the distance in mm to a wall
	//in: Nothing
	//Out: double distance in mm
	public double SonicRightSide(){
		return SonicSideRight.getValue()*rawToMM;
		
	}
	//Description: Read the sensor and returns the distance in mm to a wall
	//in: Nothing
	//Out: double distance in mm
	public double SonicLeftSide(){
		return SonicSideLeft.getValue()*rawToMM;
		
	}
	//Description: Read the sensor and returns the distance in mm to a wall
	//in: Nothing
	//Out: double distance in mm
	public double SonicRightCenter(){
		return SonicCenterRight.getValue()*rawToMM;
		
	}
	//Description: Read the sensor and returns the distance in mm to a wall
	//in: Nothing
	//Out: double distance in mm
	public double SonicLeftCenter(){
		return SonicCenterLeft.getValue()*rawToMM;
		
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
	
	//This lets side and front sensors to tell the angle of how close the wall is
	//In: nothing
	//Out: angle 
	public float angle() {
		float angle = 0;
		
		return angle;
		
	}
}
