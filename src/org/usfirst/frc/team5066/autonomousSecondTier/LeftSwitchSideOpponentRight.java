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
		
		super.verticalReverse(super.CenterRobotCorner, Arm.Position.SWITCH, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(63.47, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(CenterRobotWidth, Arm.Position.PICKUP, false);
		super.rotate(90, false, Arm.Position.PICKUP);
		super.vertical(35.47 - CenterRobotLength, Arm.Position.PICKUP, true);
		
		super.verticalReverse(35.47 - CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90.0, true, Arm.Position.TRAVEL);
		super.vertical(15 * 12 + 20, Arm.Position.TRAVEL, false);
		super.rotate(90.0, true, Arm.Position.TRAVEL);
		super.vertical(100.0, Arm.Position.TRAVEL, false);
		
	}
	
}
