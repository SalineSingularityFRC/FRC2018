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
		super.vertical(35);
		super.rotate(90, true);
		super.vertical(221.06-super.CenterRobotWidth+super.CenterRobotLength);
		super.rotate(90, false);
		super.vertical(133-super.CenterRobotLength);
		super.rotate(90, false);
		//raise PC
		super.vertical(12);
		//Drop PC
		super.vertical(- (32.5-super.CenterRobotWidth));
		super.rotate(90, false);
		//Lower PC manipulator
		super.vertical(54-super.CenterRobotLength);
		//pick up PC
		super.vertical(-54);
		super.rotate(90, true);
		super.vertical(201-super.CenterRobotWidth);
		
	}

}
