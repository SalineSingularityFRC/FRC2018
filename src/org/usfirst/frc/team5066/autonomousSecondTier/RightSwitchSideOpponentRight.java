package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RightSwitchSideOpponentRight extends AutonControlScheme {

	public RightSwitchSideOpponentRight(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		
	}

	@Override
	public void moveAuton() {
		
		super.verticalReverse(super.CenterRobotCorner + 10, Arm.Position.SWITCH, false);
		super.rotate(90.0, false, Arm.Position.PICKUP);
		super.vertical(30 + super.CenterRobotLength + 23, Arm.Position.PICKUP, false);
		super.rotate(90.0, true, Arm.Position.PICKUP);
		super.vertical(super.CenterRobotCorner + 10 + 13, Arm.Position.PICKUP, false);
		super.rotate(90.0, true, Arm.Position.PICKUP);
		super.vertical(23 - super.CenterRobotLengthWithArm + super.CenterRobotLength,
				Arm.Position.PICKUP, true);
		super.verticalReverse(super.CenterRobotCorner, Arm.Position.TRAVEL, false);
		super.rotate(90.0, true, Arm.Position.TRAVEL);
		super.vertical(super.CenterRobotCorner + 40, Arm.Position.TRAVEL, false);
		super.rotate(90.0, true, Arm.Position.TRAVEL);
		super.vertical(100.0, Arm.Position.TRAVEL, false);
		
		
	}
	
	
	
}
