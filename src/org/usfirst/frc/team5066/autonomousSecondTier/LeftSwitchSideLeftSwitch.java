package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftSwitchSideLeftSwitch extends AutonControlScheme {

	public LeftSwitchSideLeftSwitch(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		super.verticalReverse(super.CenterRobotCorner, true, false);
		super.rotate(90, true, true);
		super.vertical(93.47, true, false);
		super.rotate(90, false, true);
		super.vertical(CenterRobotWidth, false, false);
		super.rotate(90, false, false);
		super.vertical(65.47 - CenterRobotLength, false, true);
		arm.setArmNew(true, 0.25);
		super.vertical(13, true, false);
		intake.autonOuttake();
	}

}
