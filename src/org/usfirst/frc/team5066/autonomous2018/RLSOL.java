package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class RLSOL extends AutonControlScheme {

	public RLSOL(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(70-(super.CenterRobotLength/2));
		super.rotate(90,true);
		super.vertical(170.685-super.CenterRobotWidth);
		super.rotate(90,false, Arm.Position.SWITCH);
		super.vertical(70-(super.CenterRobotLength/2), Arm.Position.SWITCH, false);
		intake.autonOuttake();
		super.vertical(- (32.5-super.CenterRobotWidth));
		super.rotate(90, false, Arm.Position.PICKUP);
		super.vertical(44.875-super.CenterRobotLength, Arm.Position.PICKUP, true);
		super.vertical(-44.875, Arm.Position.TRAVEL, false);
		super.rotate(180, false, Arm.Position.TRAVEL);
		super.vertical(50.375+super.CenterRobotWidth);
		super.rotate(90, false);
		super.vertical(240.5 - super.CenterRobotLength*2);
	}

}
