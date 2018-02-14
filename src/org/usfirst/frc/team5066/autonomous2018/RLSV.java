package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class RLSV extends AutonControlScheme {

	public RLSV(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate(90, true);
		super.vertical(190.15-super.CenterRobotLength);
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(105-super.CenterRobotLength, Arm.Position.SWITCH, false);
		intake.autonOuttake();
		super.vertical(- (32.5-super.CenterRobotWidth));
		super.rotate(90, false, Arm.Position.PICKUP);
		super.vertical(47.5-super.CenterRobotLength, Arm.Position.PICKUP, true);
		super.rotate(180, false, Arm.Position.TRAVEL);
		super.vertical(24-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, true);
		super.vertical(63.5-super.CenterRobotLength);
	}

}
