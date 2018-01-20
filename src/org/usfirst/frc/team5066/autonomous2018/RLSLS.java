package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class RLSLS extends AutonControlScheme {
	
	public RLSLS(SingDrive drive) {
		super(drive);
	}
	
	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate(90,true);
		super.vertical(153.75);
		super.rotate(90,false);
		//Raise PC
		super.vertical(105-super.CenterRobotLength);
		//Drop PC
		super.vertical(-(32.5-super.CenterRobotLength));
		super.rotate(90,false);
		super.vertical(47.5-super.CenterRobotLength);
		//Pick up PC
		super.vertical(-(47.5-super.CenterRobotLength));
		super.rotate(90,true);
		super.vertical(32.5-super.CenterRobotLength);
		//Drop PC
	}

}
