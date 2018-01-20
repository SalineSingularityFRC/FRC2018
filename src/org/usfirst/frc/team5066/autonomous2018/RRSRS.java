package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class RRSRS extends AutonControlScheme {
	
	public RRSRS(SingDrive drive) {
		super(drive);
	}
	
	@Override
	public void moveAuton() {
		super.vertical(169-super.CenterRobotLength);
		super.rotate(90,true);
		//raise PC
		super.vertical(55.435-super.CenterRobotWidth);
		//Drop PC
		super.vertical(-(super.CenterRobotCorner-super.CenterRobotLength+1));
		//Lower PC manipulator
		super.rotate(90, true);
		super.vertical(60.5);
		//Lower manipulator
		super.rotate(90,false);
		super.vertical(70.25+(super.CenterRobotCorner-super.CenterRobotLength+1));//1 is placeholder value, this should be distance to cube stockpile from switch edge
		//Grab PC
		super.vertical(-54);
		super.rotate(90,false);
		//raise PC
		super.vertical(32.5-super.CenterRobotLength);
		
		//Drop PC
	}

}