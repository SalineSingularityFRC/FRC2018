package org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class MiddleLeftLightningBolt extends AutonControlScheme {

	public MiddleLeftLightningBolt(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		super.vertical(48, true, false);
		super.rotate(90, true, true);
		super.vertical(72 - super.CenterRobotWidth, true, false);
		//rotate so robot is facing switch
		super.rotate(90, false, true);
		super.vertical(95 - (super.CenterRobotLength), true, false);
		intake.autonOuttake();
	}

}
