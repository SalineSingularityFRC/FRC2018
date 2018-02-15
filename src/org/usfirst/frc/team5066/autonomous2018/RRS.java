package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RRS extends AutonControlScheme {

	public RRS(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(166-super.CenterRobotLength);
		super.rotate(90, true, Arm.Position.SWITCH);
		super.vertical(43.81-super.CenterRobotWidth, Arm.Position.SWITCH, false);
		intake.autonOuttake();
	}

}
