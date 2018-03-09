package org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class MiddleRightSwitchLightningBolt extends AutonControlScheme {
	
	public MiddleRightSwitchLightningBolt(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}
		
	@Override
	public void moveAuton() {
		//Lightning bolt to right switch
		super.vertical(48, true, false);
		super.rotate(90, false, true);
		super.vertical(80 - super.CenterRobotWidth, true, false);
		//rotate so robot is facing the switch
		super.rotate(90, true, true);
		super.vertical(95 - super.CenterRobotLength, true, false);
		intake.autonOuttake();

	}

}