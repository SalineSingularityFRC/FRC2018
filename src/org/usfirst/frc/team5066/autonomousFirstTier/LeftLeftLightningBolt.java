package org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftLeftLightningBolt extends AutonControlScheme {

	public LeftLeftLightningBolt(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		
	}

	@Override
	public void moveAuton() {
		
		super.vertical(70.0 - 0.5 * super.CenterRobotLength, true, false);
		super.rotate(90.0, false, true);
		super.vertical(72.31, true, false);
		super.rotate(90.0, true, true);
		super.vertical(70 - 0.5 * super.CenterRobotLength, true, false);
		intake.autonOuttake();
		
	}

}
