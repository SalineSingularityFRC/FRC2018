package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class LRS extends AutonControlScheme{

	public LRS(SingDrive drive, AHRS gyro, Arm arm) {
		super(drive, gyro, arm);
	}

	@Override
	public void moveAuton() {
		super.vertical(super.CenterRobotLength, Arm.Position.TRAVEL);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(264-super.CenterRobotWidth, Arm.Position.TRAVEL);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(168-(2*super.CenterRobotLength), Arm.Position.SWITCH);
		super.rotate(90, true, Arm.Position.SWITCH);
		//place block
	}

}