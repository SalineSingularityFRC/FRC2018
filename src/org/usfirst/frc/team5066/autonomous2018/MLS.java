package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

public class MLS extends AutonControlScheme {

	public MLS(SingDrive drive) {
		super(drive);
	}

	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate(90, true);
		super.vertical(76.875+super.CenterRobotWidth*2);
		super.rotate(90, false);
		super.vertical(133-super.CenterRobotLength);
		super.rotate(90, false);
		//Drop PC
	}

}
