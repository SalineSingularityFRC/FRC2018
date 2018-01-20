package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

public class MRS extends AutonControlScheme {

	public MRS(SingDrive drive) {
		super(drive);
	}

	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate(90, false);
		super.vertical(100.875);
		super.rotate(90, true);
		super.vertical(133-super.CenterRobotLength);
		super.rotate(90, true);
		//Drop PC
	}

}
