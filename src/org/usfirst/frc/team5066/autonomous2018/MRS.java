package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class MRS extends AutonControlScheme {

	public MRS(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(48, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(66-super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		//rotate so robot is facing the switch
		super.rotate( 90, true, Arm.Position.SWITCH);
		super.vertical(105 - super.CenterRobotLength, Arm.Position.SWITCH, false);
		//Drop the PC
		intake.autonOuttake();
		super.verticalReverse(32.5-super.CenterRobotLength, Arm.Position.SWITCH, false);
		super.rotate(90, true, Arm.Position.PICKUP);
		//Lower PC manipulator
		super.vertical(60.5-super.CenterRobotLength, Arm.Position.PICKUP, true);
	}

}
