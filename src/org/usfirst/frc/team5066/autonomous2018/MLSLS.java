package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class MLSLS extends AutonControlScheme {

	public MLSLS(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		// TODO Auto-generated method stub
		super.vertical(48, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(84-super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		super.rotate(90, false,Arm.Position.TRAVEL);
		super.vertical(95-super.CenterRobotLength, Arm.Position.SWITCH, false);
		intake.autonOuttake();
		super.verticalReverse ((35.5-super.CenterRobotWidth),Arm.Position.TRAVEL, false);
		super.rotate(90, false,Arm.Position.TRAVEL);
		//Lower PC manipulator
		// If end PC is grabbed from side piramid will be pushed
		super.vertical(63-super.CenterRobotLength,Arm.Position.PICKUP, true);
		super.verticalReverse ((63-super.CenterRobotLength),Arm.Position.TRAVEL,false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(32.5-super.CenterRobotWidth,Arm.Position.TRAVEL, false);
		intake.autonOuttake();
	}
}
