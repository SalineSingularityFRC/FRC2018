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
		super.verticalReverse(super.CenterRobotCorner, Arm.Position.SWITCH, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(63.47, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(CenterRobotWidth, Arm.Position.PICKUP, false);
		super.rotate(90, false, Arm.Position.PICKUP);
		super.vertical(35.47 - CenterRobotLength, Arm.Position.PICKUP, true);
		super.verticalReverse(32.735, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(40, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(32.735, Arm.Position.TRAVEL,false);

	}

}
