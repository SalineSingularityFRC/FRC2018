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
		super.verticalReverse(32.5-super.CenterRobotLength, Arm.Position.SWITCH, false);
		super.rotate(90, false, Arm.Position.PICKUP);
		super.vertical(60.5+super.CenterRobotCorner-super.CenterRobotLength, Arm.Position.PICKUP, true);
		super.verticalReverse(60.5+super.CenterRobotCorner-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.SWITCH);
		super.vertical(32.5-super.CenterRobotLength, Arm.Position.SWITCH, false);
		intake.autonOuttake();
	}

}
