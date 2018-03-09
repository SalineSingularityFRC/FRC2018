package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftSwitchSideRightScale extends AutonControlScheme {

	public LeftSwitchSideRightScale(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		/*
		super.verticalReverse(super.CenterRobotCorner, true, false);
		super.rotate(90, true, true);
		super.vertical(93.47, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(CenterRobotWidth, Arm.Position.PICKUP, false);
		super.rotate(90, false, Arm.Position.PICKUP);
		super.vertical(65.47 - CenterRobotLength, Arm.Position.PICKUP, true);
		super.verticalReverse(65.47 - CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, true);
		super.vertical(143, Arm.Position.HIGHSCALE, false);
		super.rotate(90, true);
		super.vertical(38.28, Arm.Position.HIGHSCALE, false);
		intake.autonOuttake();*/
	}

}
