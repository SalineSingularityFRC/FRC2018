package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RightScaleOpponentLeft extends AutonControlScheme {

	public RightScaleOpponentLeft(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(super.CenterRobotCorner, Arm.Position.HIGHSCALE, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(36.825-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(super.CenterRobotCorner*2, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(36.825, Arm.Position.PICKUP, true);
		super.verticalReverse(36.825, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(192.43, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
	}

}
