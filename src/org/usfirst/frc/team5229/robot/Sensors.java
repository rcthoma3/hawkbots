package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors {
	Encoder LeftFrontEnc = new Encoder(0, 1, false, Encoder.EncodingType.k4X );//Encoder for left front motor
	Encoder LeftRearEnc = new Encoder(2, 3, false, Encoder.EncodingType.k4X);//Encoder for left rear motor
	Encoder RightFrontEnc = new Encoder(4, 5, false, Encoder.EncodingType.k4X);//Encoder for right front motor
	Encoder RightRearEnc = new Encoder(6, 7, false , Encoder.EncodingType.k4X);//Encoder for right rear motor
	SpeedController D;//Speed Controller for left front motor
	SpeedController E;//Speed Controller for left rear motor
	SpeedController F;//Speed Controller for right front motor
	SpeedController G;//Speed Controller for right rear motor
	MecanumDrive drive = new MecanumDrive(D, E, F, G);//Drive used for Sensors
	double encoderDistanceReading;//Tells the amount of distance a robot travels
	public static final double Wheel_Diameter = 8;//Diameter of a wheel
	public static final double Pulse_Per_Revolution = 4096;//Number of pulses per revolution
	public static final double Fudge_Factor = 1.0; //The fudge factor used to fudge distance per pulse
	public double distancePerPulse;
    
	//Gets robot position
	//in:distance
	//out:Nothing Yet
	public void getPosition (double distance) {
		
		//use sensor to figure out what starting position we are in
		//relay information if needed
		
	}
	
	//Set Distance Per Pulse
	//in:nothing
	//out:nothing
	public void SetDistancePerPulse() {
		distancePerPulse = Math.PI * Wheel_Diameter / Pulse_Per_Revolution * Fudge_Factor; //The distance per pulse
		LeftFrontEnc.setDistancePerPulse(distancePerPulse);
	}
	
	
	//Make a robot move forward during autonomous
	//in:Distance, Speed
	//out:nothing
	public void DriveFowardAuto(int Distance, double speed) {
		encoderDistanceReading = LeftFrontEnc.getDistance();
		SmartDashboard.putNumber("encoder reading", encoderDistanceReading);
		drive.driveCartesian(0, speed, 0);
		if(encoderDistanceReading > Distance) {
			drive.driveCartesian(0, 0, 0);
		}
	}
	//Tests Encoders
	//in:Nothing
	//out:Nothing
	public void EncoderTest() {
		LeftFrontEnc.reset();
	}
	

}
