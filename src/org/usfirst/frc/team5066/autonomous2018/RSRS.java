package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RSRS extends AutonControlScheme {

	public RSRS(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.verticalReverse(60.5, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(32.5, Arm.Position.SWITCH, false);
		intake.autonOuttake();
	}

}
