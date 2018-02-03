package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.VictorSP;


public class Climbing {
	
	private VictorSP m_climbMotor; 
	private boolean setMotor = false;
	private Sensors topSwitch = new Sensors();
	private Sensors bottomSwitch = new Sensors();
	private boolean topSensorpressed = false;
	private boolean bottomSensorpressed = false;
	private boolean raise = false;
	private boolean lower = false;
	
	//Initialize switch with DIO
	//IN:DIO Switch is plugged in
	//Out:None
	public void setSwitches(DigitalInput topSwitchIn, DigitalInput bottomSwitchIn) {
		topSwitch.limitswitch(topSwitchIn);
		bottomSwitch.limitswitch(bottomSwitchIn);
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
		
		//topSensorpressed = topSwitch.getstate(); 

		if (!setMotor) {
			System.out.println("ERROR: Climb Motor Not Initiated!");
		}		
		else {
			if(!topSensorpressed) { 
				m_climbMotor.setSpeed(speed);
				raise = true;
			} else { m_climbMotor.setSpeed(0); }
		}
	}
	
	//Lowers the elevator allowing the bot to climb the tower
	//IN:Speed of the elevator
	//OUT:None
	public void lowerElavator (double speed) {
		//pull up robot using hook attached to bar
		//Spins motor backwards (Speed = -)
		//bottomSensorpressed = bottomSwitch.getstate(); 
		if (!setMotor) {
			System.out.println("ERROR: Climb Motor Not Initiated!");
		}
		else {
			if(!bottomSensorpressed) {
				m_climbMotor.setSpeed(-speed);
				lower = true;
			} else { m_climbMotor.setSpeed(0); }
		}
	}
	public void checkSwitches(boolean switchOverride) {
		System.out.println(topSensorpressed);
		
		bottomSensorpressed = bottomSwitch.getstate(); 
		topSensorpressed = topSwitch.getstate();
		
		if ((topSensorpressed || switchOverride) && raise) {
			m_climbMotor.setSpeed(0);
			raise = false;
		}
		else if(!lower && raise){m_climbMotor.setSpeed(0.3);}
		
		if ((bottomSensorpressed || switchOverride) && lower) {
			 m_climbMotor.setSpeed(0);
			 lower = false;
		}
		else if(!raise && lower) {m_climbMotor.setSpeed(-0.3);}
	}
}
	

