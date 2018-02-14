package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class LLSOR extends AutonControlScheme {

	public LLSOR(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(168-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		//raise arm
		super.rotate(90, false, Arm.Position.SWITCH);
		//Drop PC
		intake.autonOuttake();
		super.rotate(90, false, Arm.Position.SWITCH);
		//lower arm
		super.vertical(30-super.CenterRobotLength, Arm.Position.TRAVEL, false);//don't know exactly
		super.rotate(90,true, Arm.Position.PICKUP);
		//pick up b block
		super.vertical(20, Arm.Position.PICKUP, true);//don't know exactly
		super.vertical(-0.5, 20, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(48-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(264-super.CenterRobotWidth, Arm.Position.SWITCH, false);

	}

}