package org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftRightScale extends AutonControlScheme {

	public LeftRightScale(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(261 - super.CenterRobotLength, true, false);
		super.rotate(90, false, true);
		super.vertical(182+super.CenterRobotLengthWithArm, true, false);
		super.rotate(90, false, true);
		super.verticalReverse(38, true, false);
		super.rotate(90, true, true);
		super.verticalReverse(super.CenterRobotLength, true, false);
		intake.autonOuttake();

	}

}
