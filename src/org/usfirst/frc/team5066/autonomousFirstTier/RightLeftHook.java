package org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RightLeftHook extends AutonControlScheme {

	public RightLeftHook(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		// go around behind the switch to get to left switch
		super.vertical(229 + super.CenterRobotLength, true, false);
		super.rotate(90, true, true);
		// TODO check the following distance
		super.vertical(264 - super.CenterRobotWidth, true, false);
		super.rotate(90, true, true);
		// lift arm
		// TODO check the following distance
		super.vertical(45, true, false);
		// release PC
		intake.autonOuttake();
	}

}
