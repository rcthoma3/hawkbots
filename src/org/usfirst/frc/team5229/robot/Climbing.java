package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.VictorSP;


public class Climbing {
	
	private VictorSP m_climbMotor; 
	private boolean setMotor = false;
	private Sensors topSwitch = new Sensors();
	private Sensors bottomSwitch = new Sensors();
	
	//Initialize switch with DIO
	//IN:DIO Switch is plugged in
	//Out:None
	public void setSwitches(int dio) {
		topSwitch.limitswitch(dio);
		bottomSwitch.limitswitch(dio);
	}
	
	//Initialize climb motor with PWM
	//IN:PWM the motor is connected to
	//OUT:setMotor is true
	public boolean setClimbMotor (VictorSP m_climbMotorIn) {
		m_climbMotor = m_climbMotorIn;
		setMotor = true;
		return setMotor;
	}
	
	//Allows the elevator to go up
	//IN:Speed of the elevator
	//OUT:None
	public void raiseElevator (double speed) {
		//Raises elevator to hook bar 
		//Spins motor forward (Speed = +)
		
		boolean sensorpressed = topSwitch.getstate(); 
		
		if (!setMotor) {
			System.out.println("ERROR: Climb Motor Not Initiated!");
		}		
		else {
			if(!sensorpressed) { 
				m_climbMotor.setSpeed(speed);
			} else { m_climbMotor.setSpeed(0); }
		}
	}
	
	//Lowers the elevator allowing the bot to climb the tower
	//IN:Speed of the elevator
	//OUT:None
	public void lowerElavator (double speed) {
		//pull up robot using hook attached to bar
		//Spins motor backwards (Speed = -)
		boolean sensorpressed = bottomSwitch.getstate(); 
		if (!setMotor) {
			System.out.println("ERROR: Climb Motor Not Initiated!");
		}
		else {
			if(!sensorpressed) {
				m_climbMotor.setSpeed(-speed);
			} else { m_climbMotor.setSpeed(0); }
		}
	}
}
	

