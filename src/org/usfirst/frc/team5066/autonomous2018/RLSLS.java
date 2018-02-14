package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class RLSLS extends AutonControlScheme {
	
	public RLSLS(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}
	
	@Override
	public void moveAuton() {
		super.vertical(120-(super.CenterRobotLength)-(45/2));
		super.rotate(90,true);
		super.vertical(120);
		super.rotate(90,false, Arm.Position.SWITCH);
		super.vertical(45, Arm.Position.SWITCH, false);
		intake.autonOuttake();
		super.vertical(- (32.5-super.CenterRobotWidth));
		super.rotate(90,false, Arm.Position.PICKUP);
		super.vertical(54-super.CenterRobotLength, Arm.Position.PICKUP, true);
		super.vertical(-(54-super.CenterRobotLength), Arm.Position.TRAVEL, false);
		super.rotate(90,true, Arm.Position.SWITCH);
		super.vertical(32.5-super.CenterRobotWidth, Arm.Position.SWITCH, false);
		intake.autonOuttake();
	}

}
