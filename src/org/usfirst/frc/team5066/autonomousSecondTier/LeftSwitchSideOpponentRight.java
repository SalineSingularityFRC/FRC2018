package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftSwitchSideOpponentRight extends AutonControlScheme {

	public LeftSwitchSideOpponentRight(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		
	}

	@Override
	public void moveAuton() {
		
		super.verticalReverse(super.CenterRobotCorner, true, false);
		super.rotate(90, true, true);
		super.vertical(63.47, true, false);
		super.rotate(90, false, true);
		super.vertical(CenterRobotWidth, false, false);
		super.rotate(90, false, false);
		super.vertical(35.47 - CenterRobotLength, false, true);
		
		super.verticalReverse(35.47 - CenterRobotLength, true, false);
		super.rotate(90.0, true, true);
		super.vertical(15 * 12 + 20, true, false);
		super.rotate(90.0, true, true);
		super.vertical(100.0, true, false);
		
	}
	
}
