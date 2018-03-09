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
		
		super.verticalReverse(super.CenterRobotCorner + 10, true, false);
		super.rotate(90.0, false, false);
		super.vertical(30 + super.CenterRobotLength + 23, false, false);
		super.rotate(90.0, true, false);
		super.vertical(super.CenterRobotCorner + 10 + 13, false, false);
		super.rotate(90.0, true, false);
		super.vertical(23 - super.CenterRobotLengthWithArm + super.CenterRobotLength,
				false, true);
		super.verticalReverse(super.CenterRobotCorner, true, false);
		super.rotate(90.0, true, true);
		super.vertical(super.CenterRobotCorner + 40, true, false);
		super.rotate(90.0, true, true);
		super.vertical(100.0, true, false);
		
		
	}
	
	
	
}
