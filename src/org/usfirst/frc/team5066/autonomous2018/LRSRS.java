package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class LRSRS extends AutonControlScheme {

	public LRSRS(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		super.vertical(35, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(114+super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		//raise PC
		super.vertical(107-super.CenterRobotLength, Arm.Position.SWITCH, false);
		super.rotate(90, true,Arm.Position.SWITCH);
		intake.autonOuttake();
		super.vertical(- (32.5-super.CenterRobotLength));
		super.rotate(90, true);
		//Lower PC manipulator
		super.vertical(54-super.CenterRobotLength,Arm.Position.PICKUP, true);
		//Pick up PC
		super.vertical(54-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, false);
		super.vertical(32.5-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		intake.controlIntake(false, true, false, true);
	}

}