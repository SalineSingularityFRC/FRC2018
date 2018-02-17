package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftScaleLeftScale extends AutonControlScheme {

	public LeftScaleLeftScale(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.verticalReverse(12, Arm.Position.HIGHSCALE, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(103, Arm.Position.TRAVEL, false);
		super.rotate(90, true);
		super.vertical(31.75);
		super.rotate(90, false, Arm.Position.PICKUP);
		super.vertical(25, Arm.Position.PICKUP, true);
		super.verticalReverse(25, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(31.75);
		super.rotate(90, false);
		super.vertical(103);
		super.rotate(90, false, Arm.Position.HIGHSCALE);
		super.vertical(12, Arm.Position.HIGHSCALE, false);
		intake.autonOuttake();
	}

}
