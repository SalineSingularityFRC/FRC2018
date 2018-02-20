package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftSwitchFrontVault extends AutonControlScheme {

	public LeftSwitchFrontVault(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.verticalReverse(32.5-super.CenterRobotLength, Arm.Position.SWITCH, false);
		super.rotate(90, false, Arm.Position.PICKUP);
		super.vertical(60.5-super.CenterRobotLengthWithArm, Arm.Position.PICKUP, true);
		super.verticalReverse(30, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(super.CenterFieldPortal);
		super.rotate(90, false);
		super.vertical(107.5-super.CenterRobotLengthWithArm);
		intake.autonOuttake();
	}

}
