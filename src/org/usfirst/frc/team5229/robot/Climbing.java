package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;



public class Climbing {
	
	private WPI_TalonSRX m_climbMotor; 
	private boolean setMotor = false;
	private Sensors topSwitch = new Sensors();
	private Sensors bottomSwitch = new Sensors();
	private boolean topSensorpressed = false;
	private boolean bottomSensorpressed = false;
	private boolean raise = false;
	private boolean lower = false;
	private double lowerSpd = 0;
	private double raiseSpd = 0;
	
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
	public boolean setClimbMotor (WPI_TalonSRX m_climbMotorIn) {
		m_climbMotor = m_climbMotorIn;
		setMotor = true;
		return setMotor;
	}
	
	//Allows the elevator to go up
	//IN:Speed of the elevator
	//OUT:None
	public void raiseElevator (double speed, boolean button) {
		
		topSensorpressed = topSwitch.getstate(); 

		if (!setMotor) {
			System.out.println("ERROR: Climb Motor Not Initiated!");
		}		
		else {
			if(!topSensorpressed) { 
				m_climbMotor.set(ControlMode.Velocity, speed);
				if(button) {
					raise = true;
					lower = false;
					raiseSpd = speed;
				}
				else {
    				raise = false;
    				lower = false;
    				raiseSpd = 0;
    				lowerSpd = 0;
    			}
			} else { m_climbMotor.set(ControlMode.Velocity, 0); }
		}
	}
	
	//Lowers the elevator allowing the bot to climb the tower
	//IN:Speed of the elevator
	//OUT:None
	public void lowerElavator (double speed, boolean button) {

		bottomSensorpressed = bottomSwitch.getstate(); 
		
		if (!setMotor) {
			System.out.println("ERROR: Climb Motor Not Initiated!");
		}
		else {
			if(!bottomSensorpressed) {
				m_climbMotor.set(ControlMode.Velocity, -speed);
				if(button) {
					lower = true;
					raise = false;
					lowerSpd = speed;
				}
				else {
    				raise = false;
    				lower = false;
    				raiseSpd = 0;
    				lowerSpd = 0;
    			}
			} else { m_climbMotor.set(ControlMode.Velocity, 0); }
		}
	}
	
	public void checkSwitches(boolean switchOverride) {
		
		bottomSensorpressed = bottomSwitch.getstate(); 
		topSensorpressed = topSwitch.getstate();
		
		if ((topSensorpressed || switchOverride) && raise) {
			m_climbMotor.set(ControlMode.Velocity, 0);
			raise = false;
		}
		else if(!lower && raise){ m_climbMotor.set(ControlMode.Velocity, raiseSpd); }
		
		if ((bottomSensorpressed || switchOverride) && lower) {
			m_climbMotor.set(ControlMode.Velocity, 0);
			lower = false;
		}
		else if(!raise && lower) {  m_climbMotor.set(ControlMode.Velocity, -lowerSpd); }
	}
}
	

