package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class MLSLS extends AutonControlScheme {

	public MLSLS(SingDrive drive, AHRS gyro) {
		super(drive, gyro);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		// TODO Auto-generated method stub
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
		super.vertical(-(54-super.CenterRobotLength));
		super.rotate(90, true);
		super.vertical(32.5-super.CenterRobotWidth);
	}
}
