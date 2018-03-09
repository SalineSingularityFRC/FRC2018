package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftSwitchFrontLeftSwitch extends AutonControlScheme {

	public LeftSwitchFrontLeftSwitch(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.verticalReverse(super.CenterRobotCorner / 2, true, false);
		super.rotate(90, false, false);
		super.vertical(55.5-super.CenterRobotLengthWithArm, false, true);
		super.verticalReverse(50.5-super.CenterRobotLengthWithArm, false, false);
		super.rotate(90, true, true);
		super.vertical(32.5-super.CenterRobotLength, true, false);
		intake.autonOuttake();
	}

}
