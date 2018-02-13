package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class LRSV extends AutonControlScheme {

	public LRSV(SingDrive drive, AHRS gyro, Arm arm) {
		super(drive, gyro, arm);
	}

	@Override
	public void moveAuton(){

		
		
		super.vertical(-(32.5-super.CenterRobotLength));
		//Lower PC manipulator
		super.rotate(90, true);
		super.vertical(54-super.CenterRobotLength);
		//Pick up PC
		super.rotate(90, true);
		super.vertical(63.5-super.CenterRobotLength);
		super.rotate(90, false);
		super.vertical(super.CenterRobotLength+12);
		super.rotate(90, true);

	}

}
