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
	
	Thread visionThread;
	@Override
	public void robotInit() {
		visionThread = new Thread(() -> {
			// Get UsbCamera from camera server
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			//Set up resolution
			camera.setResolution(640, 480);
			//Get a CvSink. This will capture Mats from camera
		CvSink cvSink = CameraServer.getInstance().getVideo();
		//Setup a CvSource. This will send images back to the dashboard
		CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 640, 480);
		//Mats are very memory expensive. Let's reuse this Mat.
		Mat mat = new Mat();
		// This cannot be 'true'. The program will never exit if it is. This
					// lets the robot stop this thread when restarting robot code or
					// deploying.
		while (!Thread.interrupted()) {
			// Tell the CvSink to grab a frame from the camera and put it
			// in the source mat.  If there is an error notify the output.
			if (cvSink.grabFrame(mat) == 0) {
				//Send the output the error
			outputStream.notifyError(cvSink.getError());
			//Skip the rest of the current iteration
			continue;
			}
			//Put a rectangle on the image
			Imgproc.rectangle(mat, new Point(100,100), new Point(400, 400), new Scalar(255, 255, 255), 5);
			//Give out[ut stream a new image to display
			outputStream.putFrame(mat);
		}
	});
		visionThread.setDaemon(true);
		visionThread.start();
	}
public Sensors() {}
	//Provides the distance between the robot and
	//a wall in front of it in inches
	//In: nothing
	//Out: distance as float
	public float FrontSensors() {
		float distance = 0;
		//Code to find distance goes here
		
		return distance;
		
	}
	//Provides distance between the robot and the wall from the side
	//in inches
	//In: nothing
	//Out: distance as float.
	
	public float SideSensors() {
		float distance = 0;
		//Distance goes here
		return distance;
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
	//Lets sensors be able to tell if a wall is too close on one side
	//In: nothing
	//Out: distance/angle to wall
	public float sensors() {
		float distance = 0;
		return distance;
	}
}
