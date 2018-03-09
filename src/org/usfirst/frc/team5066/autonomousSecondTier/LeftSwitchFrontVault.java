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
		super.verticalReverse(32.5-super.CenterRobotLength, true, false);
		super.rotate(90, false, false);
		super.vertical(60.5-super.CenterRobotLengthWithArm, false, true);
		super.verticalReverse(30, true, false);
		super.rotate(90, false, false);
		super.vertical(super.CenterFieldPortal, false, false);
		super.rotate(90, false, false);
		super.vertical(107.5-super.CenterRobotLengthWithArm, false, false);
		intake.autonOuttake();
	}

}
