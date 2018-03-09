package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class ExchangeRightSwitch extends AutonControlScheme{

	public ExchangeRightSwitch(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		super.vertical(super.CenterRobotLength/2, false, false);
		super.rotate(90, false, true);
		super.vertical(80, true, false);
		super.rotate(90, true, true);
		super.vertical(42 + super.CenterRobotLength/2, true, false);
		intake.autonOuttake();
		
	}

}
