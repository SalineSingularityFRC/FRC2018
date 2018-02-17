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
		super.vertical(221+super.CenterRobotWidth);//go out around the switch
		super.rotate(90, true);
		super.vertical(215.06-super.CenterRobotWidth+super.CenterRobotLength);//cross over the switch
		super.rotate(90, false);
		super.vertical(53);// come back around
		super.rotate(90, false);
		super.vertical(12);
		intake.autonOuttake();
	}

}
