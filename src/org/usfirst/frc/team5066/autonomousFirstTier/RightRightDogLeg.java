package org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RightRightDogLeg extends AutonControlScheme {

	public RightRightDogLeg(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		//dog leg to switch
		super.vertical(166-super.CenterRobotLength);
		super.rotate(90, true, Arm.Position.SWITCH);
		super.vertical(super.CenterRobotWidth, Arm.Position.SWITCH, false);
		intake.autonOuttake();
	}

}
