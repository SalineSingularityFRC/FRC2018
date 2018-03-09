package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RightSwitchFrontVault extends AutonControlScheme {
	
	public RightSwitchFrontVault(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}
	@Override
	public void moveAuton() {
		super.verticalReverse(32.5, true, false);
		super.rotate(90, true, true);
		super.vertical(60.5+super.CenterFieldPortal, false, false);//162-(85.25-29.69+18) + 12+(48/2));
		super.rotate(90, true, false);
		super.vertical(107.5-super.CenterRobotLengthWithArm, false, false);
	}

}
