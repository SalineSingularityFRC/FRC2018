package org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftLeftDogLeg extends AutonControlScheme {

	public LeftLeftDogLeg(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		
	}

	@Override
	public void moveAuton() {
		
		// dog leg to left switch
		super.vertical(168 - super.CenterRobotLength, true, false);
		// rotate so robot is facing the switch
		super.rotate(90, false, true);
		// lift arm
		super.vertical(super.CenterRobotWidth, true, false);
		intake.autonOuttake();
		
	}

}
