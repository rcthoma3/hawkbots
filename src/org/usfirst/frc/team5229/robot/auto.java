package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.Timer;

public class auto {
	RobotMovement robot = new RobotMovement();
	Sensors sensors = new Sensors();
	Timer AutoTimer = new Timer();//timer for automonous 
	public boolean AutoTesting;//start auto test funtcion
	public boolean Sensordetecting = false; //boolean for autotesting statement
	double value;
	double value2;
	double ScaleVal = 2;
	
	
	public void straightenOut() {
		
		if (sensors.SonicRightCenter() > sensors.SonicLeftCenter())
			robot.turnLeft(.25, 1);
		
		else if (sensors.SonicLeftCenter() > sensors.SonicRightCenter())
			robot.turnRight(.25, 1);
	}
	
	public boolean StartAutoTimer(){
		if(AutoTesting = true){
			AutoTimer.reset();
			AutoTimer.start();
		}
		return true;
	}
	
	
	
	public void AutoTesting(){
		
		if (AutoTimer.get() < 4.0)
		robot.DrivefowardBackward(Math.max(Math.min((sensors.SonicLeftCenter() + sensors.SonicRightCenter() / 2)/ScaleVal,1),0));
		
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
	