package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class LRSRS extends AutonControlScheme {

	public LRSRS(SingDrive drive, ADXRS450_Gyro gyro) {
		super(drive, gyro);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate(90, false);
		super.vertical(114+super.CenterRobotWidth);
		super.rotate(90, true);
		//raise PC
		super.vertical(107-super.CenterRobotLength);
		super.rotate(90, true);
		//drop PC
		super.vertical(- (32.5-super.CenterRobotLength));
		super.rotate(90, true);
		//Lower PC manipulator
		super.vertical(54-super.CenterRobotLength);
		//Pick up PC
		super.vertical(54-super.CenterRobotLength);
		super.rotate(90, false);
		super.vertical(32.5-super.CenterRobotLength);
		//Drop PC
	}

}