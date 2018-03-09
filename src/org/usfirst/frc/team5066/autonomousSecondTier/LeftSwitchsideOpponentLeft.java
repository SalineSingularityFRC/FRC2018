package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftSwitchsideOpponentLeft extends AutonControlScheme {

	public LeftSwitchsideOpponentLeft(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		// TODO Auto-generated constructor stub
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
		super.verticalReverse(32.735, true, false);
		super.rotate(90, false, true);
		super.vertical(40, true, false);
		super.rotate(90, false, true);
		super.vertical(32.735, true, false);

	}

}
