package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class MRSV extends AutonControlScheme {

	public MRSV(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(35, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(42-super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		super.rotate( 90, true, Arm.Position.SWITCH);
		//raise PC
		super.vertical(105-super.CenterRobotLength, Arm.Position.SWITCH, false);
		//Drop the PC
		intake.autonOuttake();
		super.verticalReverse((32.5-super.CenterRobotLength), Arm.Position.SWITCH, false);
		//Lower PC manipulator
		super.rotate(90, true, Arm.Position.PICKUP);
		super.vertical(54-super.CenterRobotLength, Arm.Position.PICKUP, true);
		//Pick up PC
		super.rotate(90, true, Arm.Position.EXCHANGE);
		super.vertical(63.5-super.CenterRobotLength, Arm.Position.EXCHANGE, false);
		super.rotate(90, false, Arm.Position.EXCHANGE);
		super.vertical(super.CenterRobotLength+12, Arm.Position.EXCHANGE, false);
		super.rotate(90, true, Arm.Position.EXCHANGE);
	}

}