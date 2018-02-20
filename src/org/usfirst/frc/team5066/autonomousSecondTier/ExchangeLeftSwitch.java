package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class ExchangeLeftSwitch extends AutonControlScheme {

	public ExchangeLeftSwitch(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		super.vertical(super.CenterRobotLength/2, Arm.Position.PICKUP, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(80, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(42 + super.CenterRobotLength/2, Arm.Position.SWITCH, false);
		intake.autonOuttake();
	}

}
