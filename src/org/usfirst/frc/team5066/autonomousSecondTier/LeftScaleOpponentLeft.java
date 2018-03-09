package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftScaleOpponentLeft extends AutonControlScheme {

	public LeftScaleOpponentLeft(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		
	}

	@Override
	public void moveAuton() {
		/*
		super.vertical(12, Arm.Position.HIGHSCALE, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(65.75-super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		super.rotate(90, true);
		super.vertical(172.25);
		super.rotate(90, false);
		super.vertical(62.25-(super.CenterRobotLengthWithArm/2), Arm.Position.PICKUP, true);
		super.verticalReverse(62.25-(super.CenterRobotLengthWithArm/2), Arm.Position.TRAVEL, true);
		super.rotate(90, false);
		super.vertical(31.75 + super.CenterRobotLength);
		super.rotate(90, false);
		super.vertical(89.75-super.CenterRobotWidth-super.CenterRobotLength);*/
		
	}
	
}
