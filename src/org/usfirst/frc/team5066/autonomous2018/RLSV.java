package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class RLSV extends AutonControlScheme {

	public RLSV(SingDrive drive, AHRS gyro) {
		super(drive, gyro);
	}

	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate(90, true);
		super.vertical(190.15-super.CenterRobotLength);
		super.rotate(90, false);
		super.vertical(105-super.CenterRobotLength);
		//Drop PC
		super.vertical(- (32.5-super.CenterRobotWidth));
		//Lower PC manipulator
		super.rotate(90, false);
		super.vertical(47.5-super.CenterRobotLength);
		//pick up PC
		super.rotate(180, false);
		super.vertical(24-super.CenterRobotLength);
		super.rotate(90, true);
		super.vertical(63.5-super.CenterRobotLength);
	}

}
