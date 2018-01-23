package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

public class RRSOL extends AutonControlScheme {

	public RRSOL(SingDrive drive) {
		super(drive);
	}

	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate(90, true);
		super.vertical(54.4);
		super.rotate(90, false);
		//raise PC
		super.vertical(105-super.CenterRobotLength);
		//Drop PC
		super.vertical(-32.5);
		//Lower PC
		super.rotate(90, true);
		super.vertical(41.5-super.CenterRobotLength);
		//Pick up PC
		super.rotate(180, true);
		super.vertical(88.75+super.CenterRobotWidth);
		super.rotate(90, true);
		super.vertical(96.75+super.CenterRobotLength/2);
		super.rotate(90, true);
		super.vertical(177.75 +super.CenterRobotWidth*2);
		super.rotate(90, true);
	}

}
