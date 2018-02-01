package org.usfirst.frc.team5229.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Autonomous {
	String gameData;
	int startpos;
	SendableChooser<Integer> autoChooser;

	
	
	public String getGameMsg(){
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		return gameData;
	} 
	
	public int getPosition() {
		return startpos; 
	}
	
	
	
	
	
		
	
	
	
	
	
	/*if (StartPosition = "Left") 
	 
	if (OurSwitchSide = "L")
			
	else if (OurSwitchSide = "R")
	
	
else if (StartPosition = "Center")
	if  (OurSwitchSide = "L")
	
	else if (OurSwitchSide = "R")
else if (StartPosition = "Right")
	if (OurSwitchSide = "L")
	
	else if (OurSwitchSide = "R")
	
(Cross line and deliver to switch or scale if switch is on the opposite side)
if (StartPosition = "Left")
	if (OurSwitchSide = "R" && ScaleSide = "R")
	
	else if (OurSwitchSide = "R" && ScaleSide = "L")
	
	else if (OurSwitchSide = "L")
	
else if (StartPosition = "Center")
	if (OurSwitchSide = "L")
	
	else if (OurSwitchSide = "R")
	
else if (StartPosition = "Left")
	if (OurSwitchSide = "L" && ScaleSide = "L")
	
	else if (OurSwitchSide = "L" && ScaleSide = "R")
	
	else if (OurSwitchSide = "R") 
(Crossing line and delivering to Switch if Switch is handled by the center)
if (StartPosition = "Left")
	if (ScaleSide = "R" && OurSwitchSide = "R")
	
	else if (ScaleSide = "R" && OurSwitchSide = "L")
	
	else if (ScaleSide = "L")
	
else if (StartPosition = "Center")
	
	if (OurSwitchSide = "L")
	
	else if (OurSwitchSide = "R") 
else if (StartPosition = "Right")
	
	if (ScaleSide = "L" && OurSwitchSide = "L")
	
	else if (ScaleSide = "L" && OurSwitchSide = "R")
	
	else if (ScaleSide = "R")*/
	 

}
