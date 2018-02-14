package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class MRSRS extends AutonControlScheme{

	public MRSRS(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(35.0, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(42-super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		super.rotate( 90, true, Arm.Position.SWITCH);
		super.vertical(105 - super.CenterRobotLength, Arm.Position.SWITCH, false);
		//Drop the PC
		intake.autonOuttake();
		super.vertical(32.5-super.CenterRobotLength, Arm.Position.SWITCH, false);
		super.rotate(90, true, Arm.Position.PICKUP);
		//Lower PC manipulator
		super.vertical(54-super.CenterRobotLength, Arm.Position.PICKUP, true);
		//Pick up PC
		super.vertical(-(54-super.CenterRobotLength), Arm.Position.SWITCH, false);
		super.rotate(90, false, Arm.Position.SWITCH);
		//Drop PC
		intake.autonOuttake();
		
	}
	
	
	
}
