package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class LLS extends AutonControlScheme{

	public LLS(SingDrive drive) {
		super(drive);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		super.vertical(168-super.CenterRobotLength);
		super.rotate(90, false);
		//lift arm
		super.vertical(58.56-super.CenterRobotWidth);
		//place block
	}

}
