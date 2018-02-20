package org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class Exchange extends AutonControlScheme {

	public Exchange(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.verticalReverse(super.CenterRobotLengthWithArm, Arm.Position.START, false);
		super.verticalReverse(12, Arm.Position.EXCHANGE, false);
		super.vertical(12+super.CenterRobotLengthWithArm, Arm.Position.EXCHANGE, false);
		intake.autonOuttake();
		super.verticalReverse(50.1-super.CenterRobotLengthWithArm, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(30);//THIS NEEDS TO BE CHANGED
		super.rotate(90, true, Arm.Position.PICKUP);
		super.vertical(50.1-super.CenterRobotLengthWithArm/2, Arm.Position.PICKUP, true);
	}

}
