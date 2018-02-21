package org.usfirst.frc.team5229.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climbing {
	
	private WPI_TalonSRX _climbMotor; 
	private boolean setMotor = false;
	private boolean initMotor = false;
	private boolean setSwitches = false;
	private Sensors topSwitch = new Sensors();
	private Sensors bottomSwitch = new Sensors();
	private boolean topSensorpressed = false;
	private boolean bottomSensorpressed = false;
	private boolean raise = false;
	private boolean lower = false;
	private double lowerSpd = 0;
	private double raiseSpd = 0;
	private int timeoutMs = 10;
	private int pidIdx = 0;
	private int peakCurrent = 39;
	private int peakCurrentDur = 0;
	private int contCurrent = 37;
	private int elevatorPos = 0;
	
	
	//Initialize switch with DIO
	//IN:DIO Switch is plugged in
	//Out:None
	public boolean setSwitches(DigitalInput topSwitchIn, DigitalInput bottomSwitchIn) {
		topSwitch.limitswitch(topSwitchIn);
		bottomSwitch.limitswitch(bottomSwitchIn);
		
		setSwitches = true;
		return setSwitches;
	}
	
	//Initialize climb motor with PWM
	//IN:PWM the motor is connected to
	//OUT:setMotor is true
	public boolean setClimbMotor (WPI_TalonSRX m_climbMotorIn) {
		_climbMotor = m_climbMotorIn;
		setMotor = true;
		return setMotor;
	}
	
	//Initialize the Elevator
		//in:nothing
		//out:initElevator
		public boolean initElevator() {
			if(!setMotor) {
				System.err.println("Error: Elevator Moter not set up yet.");
			}else {
				//Invert Motor
				_climbMotor.setInverted(true);
				_climbMotor.setSensorPhase(true);
				
				//Init Encoders
				_climbMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
				
				// Set the peak and nominal outputs, 12V means full
				_climbMotor.configNominalOutputForward(0, timeoutMs); //(double percentOut, int timeoutMs)
				_climbMotor.configNominalOutputReverse(0, timeoutMs);
				_climbMotor.configPeakOutputForward(1, timeoutMs); //(double percentOut, int timeoutMs)
				_climbMotor.configPeakOutputReverse(-1, timeoutMs);
				
				// Current Limiting
				_climbMotor.configPeakCurrentLimit(peakCurrent, timeoutMs); /* 39 A */
				_climbMotor.configPeakCurrentDuration(peakCurrentDur, timeoutMs); /* 0ms */
				_climbMotor.configContinuousCurrentLimit(contCurrent, timeoutMs); /* 37A */
				_climbMotor.enableCurrentLimit(true); /* turn it on */
				
				// Init Sensor to zero
				_climbMotor.setSelectedSensorPosition(0, pidIdx, timeoutMs); //(int sensorPos, int pidIdx, int timeoutMs)
				
				// PID controls
				//TODO: Tune these
				_climbMotor.selectProfileSlot(0, pidIdx); //(int slotIdx, int pidIdx) pidIdx should be 0
				_climbMotor.config_kF(0, 1.7, timeoutMs);     //(int slotIdx, double value, int timeoutMs)
				_climbMotor.config_kP(0, 2, timeoutMs);
				_climbMotor.config_kI(0, 0.003, timeoutMs);
				_climbMotor.config_kD(0, 150, timeoutMs);
				_climbMotor.config_IntegralZone(0, 30, timeoutMs);
				
				initMotor = true;
			}
			return initMotor;
		}
	
	//Allows the elevator to go up
	//IN:Speed of the elevator
	//OUT:None
	public void raiseElevator (double speed, boolean button) {

		if(!setSwitches) {
			System.err.println("Error: Switches not set up.");
		}else if(!setMotor){
			System.err.println("Error: Elevator moter not set up.");
		}else if(!initMotor){
			System.err.println("Error: Elevator moter not initialized");
		}else {
			topSensorpressed = topSwitch.getstate();
			System.out.println("Raise top switch " + topSensorpressed);
			elevatorPos = _climbMotor.getSelectedSensorPosition(0);
			if(!topSensorpressed) { 
				_climbMotor.set(ControlMode.Velocity, speed);
				System.out.println("I set velocity " + speed + "... yay");
				if(button) {
					raise = true;
					lower = false;
					raiseSpd = speed;
				} else {
					raise = false;
					lower = false;
					raiseSpd = 0;
					lowerSpd = 0;
				}
			}else { 
				//_climbMotor.set(ControlMode.Velocity, 0); 
				System.out.println("oh no it's set to 0 (re)");
				SmartDashboard.putBoolean("Climb Max", true); 
			}
		}
	}
	
	//Lowers the elevator allowing the bot to climb the tower
	//IN:Speed of the elevator
	//OUT:None
	public void lowerElavator (double speed, boolean button) {

		if(!setSwitches) {
			System.err.println("Error: Switches not set up.");
		}else if(!setMotor){
			System.err.println("Error: Elevator moter not set up.");
		}else if(!initMotor){
			System.err.println("Error: Elevator moter not initialized");
		}else {
			bottomSensorpressed = bottomSwitch.getstate(); 
			elevatorPos = _climbMotor.getSelectedSensorPosition(0);
			if(!bottomSensorpressed) {
				_climbMotor.set(ControlMode.Velocity, -speed);
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
			} else { 
				//_climbMotor.set(ControlMode.Velocity, 0);  
				SmartDashboard.putBoolean("Climb Min", true); 
			}
		}
	}
	
	public void checkSwitches(boolean switchOverride) {
		
		if(!setSwitches) {
			System.err.println("Error: Switches not set up.");
		}else if(!setMotor){
			System.err.println("Error: Elevator moter not set up.");
		}else if(!initMotor){
			System.err.println("Error: Elevator moter not initialized");
		}else {
			bottomSensorpressed = bottomSwitch.getstate();
			System.out.println("Bottom " + bottomSensorpressed);
			topSensorpressed = topSwitch.getstate();
			System.out.println("Top " + topSensorpressed);
			
			//TODO: Determine encoder count for max elevator position
			if(topSensorpressed) { SmartDashboard.putBoolean("Climb Max", true); raise = false; lower = false; elevatorPos = 16240; }
			if(bottomSensorpressed) { SmartDashboard.putBoolean("Climb Min", true); lower = false; raise = false; elevatorPos = 0; }

			if ((!topSensorpressed && !switchOverride) && (raise && !lower)) { 
				_climbMotor.set(ControlMode.Velocity, raiseSpd); 
				elevatorPos = _climbMotor.getSelectedSensorPosition(0); 
				SmartDashboard.putBoolean("Climb Max", false);
				SmartDashboard.putBoolean("Climb Min", false);
			}else if((!bottomSensorpressed && !switchOverride) && (lower && !raise)) { 
				_climbMotor.set(ControlMode.Velocity, -lowerSpd); 
				elevatorPos = _climbMotor.getSelectedSensorPosition(0); 
				SmartDashboard.putBoolean("Climb Max", false);
				SmartDashboard.putBoolean("Climb Min", false);
			}else {
				_climbMotor.set(ControlMode.Velocity, 0);
				System.out.println("somethings wrong... i dont feel good im set to 0");
				//_climbMotor.set(ControlMode.Position, elevatorPos);
				raise = false;
				lower = false;		
			}
		}
	}
}
	

