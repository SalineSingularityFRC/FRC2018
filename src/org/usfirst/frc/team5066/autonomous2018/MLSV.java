package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class MLSV extends AutonControlScheme {

	public MLSV(SingDrive drive) {
		super(drive);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate(90, true);
		super.vertical(84-super.CenterRobotWidth);
		super.rotate(90, false);
		super.vertical(105-super.CenterRobotLength);
		//Drop PC
		super.vertical(- (32.5-super.CenterRobotWidth));
		super.rotate(90, false);
		//Lower PC manipulator
		super.vertical(54-super.CenterRobotLength);
		//pick up PC
		super.rotate(180, false);
		super.vertical(24-super.CenterRobotLength);
		super.rotate(90, true);
		super.vertical(63.5-super.CenterRobotLength);
	}

}
