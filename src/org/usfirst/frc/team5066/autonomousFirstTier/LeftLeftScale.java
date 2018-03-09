package org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftLeftScale extends AutonControlScheme {

	//not used
	public LeftLeftScale(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(100-super.CenterRobotLength, true, false);
		super.vertical(224, true, false);
		super.rotate(90, true, true);
		super.verticalReverse(42.31 - super.CenterRobotWidth, true, false);
		intake.autonOuttake();
	}

}
