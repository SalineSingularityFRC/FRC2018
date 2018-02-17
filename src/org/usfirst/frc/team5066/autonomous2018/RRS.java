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
		//dog leg to switch
		super.vertical(166-super.CenterRobotLength);
		super.rotate(90, false, Arm.Position.SWITCH);
		super.verticalReverse(super.CenterRobotWidth, Arm.Position.SWITCH, false);
		intake.autonOuttake();
	}

}
