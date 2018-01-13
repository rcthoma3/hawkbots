package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors {
	Encoder LeftFrontEnc = new Encoder(0, 1, false, Encoder.EncodingType.k4X );
	Encoder LeftRearEnc = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	Encoder RightFrontEnc = new Encoder(4, 5, false, Encoder.EncodingType.k4X);
	Encoder RightRearEnc = new Encoder(6, 7, false , Encoder.EncodingType.k4X);
	SpeedController D;
	SpeedController E;
	SpeedController F;
	SpeedController G;
	MecanumDrive drive = new MecanumDrive(D, E, F, G);
	double encoderDistanceReading;
	//Gets robot position
	public void getPosition (double distance) {
		
		//use sensor to figure out what starting position we are in
		//relay information if needed
		
	}
	
	public void DriveFowardAuto(int Distance, int Speed) {
		LeftFrontEnc.reset();
		encoderDistanceReading = LeftFrontEnc.getDistance();
		SmartDashboard.putNumber("encoder reading", encoderDistanceReading);
		drive.driveCartesian(0, Speed, 0);
		if(encoderDistanceReading > Distance) {
			drive.driveCartesian(0, 0, 0);
		}
	    
	}
	

}
