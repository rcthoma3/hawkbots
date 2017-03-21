package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.Timer;

public class auto {
	RobotMovement robot;
	Sensors sensors;
	Timer AutoTimer = new Timer();//timer for automonous 
	public boolean AutoTesting;//start auto test funtcion
	public boolean Sensordetecting = false; //boolean for autotesting statement
	double value = 60;
	double value2 = 35;
	
	public auto() {
		
	}
	
	public auto(Sensors sensors,RobotMovement robot) {
		this();
		this.sensors=sensors;
		this.robot=robot;
	}
	
	public void straightenOut() {
		
		if (sensors.SonicRightCenter() > sensors.SonicLeftCenter())
			robot.turnLeft(.1, 1);
		
		else if (sensors.SonicLeftCenter() > sensors.SonicRightCenter())
			robot.turnRight(.1, 1);
	}
	
	public boolean StartAutoTimer(){
		if(AutoTesting = true){
			AutoTimer.reset();
			AutoTimer.start();
		}
		return true;
	}
	
	
	
	public void AutoTesting(){
		System.out.println("Running auto code");
		if(AutoTimer.get() < 2.0){
			
			if(((sensors.SonicLeftCenter() + sensors.SonicRightCenter())/2) > value)
				robot.DrivefowardBackward(.25);
			else{
				robot.DrivefowardBackward(0);
				Sensordetecting = true;		
			}
			
			
		}else if(AutoTimer.get() <4.0 && Sensordetecting == true){
			//robot.DrivefowardBackward(0);
			straightenOut();
				
		}else if(AutoTimer.get() <6.0 && Sensordetecting == true){
			if (((sensors.SonicLeftCenter() + sensors.SonicRightCenter())/2) > value2)
			robot.DrivefowardBackward(.25);
			
			AutoTesting = false;
			//AutoTimer.reset();
			
			

		}
	}
}	