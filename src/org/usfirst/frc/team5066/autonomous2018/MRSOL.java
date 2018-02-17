package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class MRSOL extends AutonControlScheme {

	public MRSOL(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(48, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(84-super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		//rotate so robot is facing
		super.rotate(90, true, Arm.Position.SWITCH);
		//raise PC
		super.vertical(95-super.CenterRobotLength, Arm.Position.SWITCH, false);
		intake.autonOuttake();
		super.verticalReverse((32.5-super.CenterRobotLength), Arm.Position.SWITCH, false);
		//Lower PC manipulator
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(54-super.CenterRobotLength, Arm.Position.PICKUP, true);
		//Pick up PC
		super.rotate(90, true, Arm.Position.PICKUP);
		super.vertical(super.CenterRobotLength-13, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(60.5+super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(261.5, Arm.Position.TRAVEL, false);
	}

}