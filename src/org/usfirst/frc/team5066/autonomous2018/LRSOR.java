package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class LRSOR extends AutonControlScheme {

	public LRSOR(SingDrive drive, AHRS gyro, Arm arm) {
		super(drive, gyro, arm);
	}

	@Override
	public void moveAuton() {
		super.vertical(35, Arm.Position.TRAVEL);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(190.15-super.CenterRobotLength, Arm.Position.TRAVEL);
		super.rotate(90, true, Arm.Position.SWITCH);
		super.vertical(105-super.CenterRobotLength, Arm.Position.SWITCH);
		//Drop PC
		super.vertical(-(32.5-super.CenterRobotLength), Arm.Position.TRAVEL);
		//Lower PC manipulator
		super.rotate(90, true, Arm.Position.PICKUP);
		super.vertical(54-super.CenterRobotLength, Arm.Position.PICKUP);
		//Pick up PC
		super.rotate(180, false, Arm.Position.TRAVEL);
		super.vertical(60.5-super.CenterRobotLength, Arm.Position.TRAVEL);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(153.47-super.CenterRobotLength, Arm.Position.SWITCH);
	}

}

