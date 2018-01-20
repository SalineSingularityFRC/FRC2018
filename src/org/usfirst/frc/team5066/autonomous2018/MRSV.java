package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class MRSV extends AutonControlScheme {

	public MRSV(SingDrive drive) {
		super(drive);
	}

	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate( 90, false);
		super.vertical(42-super.CenterRobotWidth);
		super.rotate( 90, true);
		//raise PC
		super.vertical(105-super.CenterRobotLength);
		//Drop the PC
		super.vertical(-(32.5-super.CenterRobotLength));
		//Lower PC manipulator
		super.rotate(90, true);
		super.vertical(54-super.CenterRobotLength);
		//Pick up PC
		super.rotate(90, true);
		super.vertical(63.5-super.CenterRobotLength);
		super.rotate(90, false);
		super.vertical(super.CenterRobotLength+12);
		super.rotate(90, true);
	}

}
