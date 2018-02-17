package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RLT extends AutonControlScheme {

	public RLT(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		
	}

	@Override
	public void moveAuton() {
		
		super.vertical(261.0 - super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90.0, false, Arm.Position.TRAVEL);
		super.verticalReverse(182.0, Arm.Position.HIGHSCALE, false);
		super.rotate(90.0, true, Arm.Position.HIGHSCALE);
		super.verticalReverse(38.0, Arm.Position.HIGHSCALE, false);
		intake.autonOuttake();
	}

}
