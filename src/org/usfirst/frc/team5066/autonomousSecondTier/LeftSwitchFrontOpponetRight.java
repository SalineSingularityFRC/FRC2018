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
		super.verticalReverse(super.CenterRobotCorner+5, Arm.Position.SWITCH, false);
		super.rotate(90, true,Arm.Position.TRAVEL);
		super.vertical(30+super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		super.rotate(90, false);
		super.vertical(86+super.CenterRobotCorner+super.CenterRobotLengthWithArm);
		super.rotate(90, false);
		super.vertical(18.5);
		super.rotate(90, false, Arm.Position.PICKUP);
		super.vertical(25, Arm.Position.PICKUP, true);
		super.verticalReverse(25, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(172.25+super.CenterRobotLength);
		super.rotate(90, true);
		super.vertical(154-super.CenterRobotLength);
	}

}
