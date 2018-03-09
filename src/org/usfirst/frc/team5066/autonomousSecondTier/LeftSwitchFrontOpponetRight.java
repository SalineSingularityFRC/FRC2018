package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftSwitchFrontOpponetRight extends AutonControlScheme {

	public LeftSwitchFrontOpponetRight(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.verticalReverse(super.CenterRobotCorner+5, true, false);
		super.rotate(90, true, true);
		super.vertical(30+super.CenterRobotWidth, true, false);
		super.rotate(90, false, true);
		super.vertical(86+super.CenterRobotCorner+super.CenterRobotLengthWithArm, true, false);
		super.rotate(90, false, true);
		super.vertical(18.5, false, false);
		super.rotate(90, false, false);
		super.vertical(25, false, true);
		super.verticalReverse(25, false, false);
		super.rotate(90, true, true);
		super.vertical(172.25+super.CenterRobotLength, true, false);
		super.rotate(90, true, true);
		super.vertical(154-super.CenterRobotLength, true, false);
	}

}
