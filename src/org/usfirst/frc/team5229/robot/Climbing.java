package org.usfirst.frc.team5229.robot;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Joystick;
import java.util.Scanner;

public class Climbing {
	
	public void Climb (double distance){
		
	}
	Scanner Climber = new Scanner(System.in); 
	
	public void raiseElevator (double speed, PWM m_climbmotor) {
		//Raises elevator to hook bar 
		//Spins motor forward (Speed = +)
		boolean sensorpressed = true;
		while (sensorpressed) {
			m_climbmotor.setSpeed(speed);
		}
		
	}
	
	//Raises Robot
	//Input of distance in motor rotations
	public void lowerElavator (double speed, PWM m_climbmotor) {
		//pull up robot using hook attached to bar
		//Spins motor backwards (Speed = -)
		boolean sensorpressed = true; 
		while(sensorpressed) {
			m_climbmotor.setSpeed(speed);
		}
		
		
	}
	
}

