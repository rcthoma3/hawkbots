package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.Timer;

public class auto {
	RobotMovement robot;
	Sensors sensors;
	Timer AutoTimer = new Timer();//timer for automonous 
	public boolean AutoTesting;//start auto test funtcion
	public boolean Sensordetecting = false; //boolean for autotesting statement
	double value;
	double value2;
	static final double DISTANCE_TO_SPEED_SCALE = 1;
	static final double MAX_AUTO_SPEED=.4;
	static final double STOP_DIST=100;
	
	public auto() {
		
	}
	
	public auto (Sensors sensors, RobotMovement robot) {
		this();
		this.sensors=sensors;
		this.robot=robot;
	}
	
	public void straightenOut() {
		double turnSpeed = Math.abs(sensors.SonicLeftCenter()-sensors.SonicRightCenter());
		turnSpeed = turnSpeed / 5;
		turnSpeed = Math.max(Math.min(turnSpeed, .5), 0);
		
		if (sensors.SonicRightCenter() > sensors.SonicLeftCenter())
			robot.turnLeft(turnSpeed, 1);
		
		else if (sensors.SonicLeftCenter() > sensors.SonicRightCenter())
			robot.turnRight(turnSpeed, 1);
	}
	
	public void StartAutoTimer(){
			AutoTimer.reset();
			AutoTimer.start();
		
	}
	
	
	boolean doForward = true;
	public void AutoTesting(){
		System.out.println(AutoTimer.get());
		int dir = 1;
		
		
		double sensorAvg = sensors.getAverage();
		System.out.println("Sensor: "+ sensorAvg);
		double speed = Math.max(Math.min(sensorAvg/DISTANCE_TO_SPEED_SCALE,MAX_AUTO_SPEED),0);
		//System.out.println(sensorAvg +"/"+DISTANCE_TO_SPEED_SCALE +"=" + speed);	
		if (AutoTimer.get()<3.0) {
			//if (sensorAvg<120 && AutoTimer.get()>0.5)
			//	straightenOut();
		if (sensorAvg>STOP_DIST)
			robot.DrivefowardBackward(speed*dir);
		else 
			robot.DrivefowardBackward(0.1);
		}
		
	}
		/*
		if(AutoTimer.get() < 2.0){
			if(sensors.SonicRightCenter() > value)
				robot.DrivefowardBackward(1.0);
			else{
				robot.DrivefowardBackward(0);
				Sensordetecting = true;		
			}
			
			
		}else if(AutoTimer.get() <4.0 && Sensordetecting == true){
			//robot.DrivefowardBackward(0);
			//Align
				
		}else if(AutoTimer.get() <6.0 && Sensordetecting == true){
			if (sensors.SonicRightCenter() > value2)
			robot.DrivefowardBackward(1);
			
			AutoTesting = false;
			AutoTimer.reset();
		}*/
	}
	