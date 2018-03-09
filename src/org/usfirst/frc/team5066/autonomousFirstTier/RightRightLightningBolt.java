package org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RightRightLightningBolt extends AutonControlScheme {

	public RightRightLightningBolt(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(70-(super.CenterRobotLength/2), true, false);
		super.rotate(90, true, true);
		super.vertical(85.25-29.69+18, true, false);
		super.rotate(90, false, true);
		super.vertical(70-(super.CenterRobotLength/2), true, false);
		intake.autonOuttake();
	}

}
