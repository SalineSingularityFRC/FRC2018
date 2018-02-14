package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class RRSOR extends AutonControlScheme {

	public RRSOR(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate(90,true);
		super.vertical(73.56-super.CenterRobotWidth);
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(105-super.CenterRobotLength, Arm.Position.SWITCH, false);
		//Drop PC
		super.vertical(-(32.5-super.CenterRobotLength), Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.PICKUP);
		super.vertical(54-super.CenterRobotLength, Arm.Position.PICKUP, true);
		super.rotate(180, false, Arm.Position.TRAVEL);
		super.vertical(60.5-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, true);
		super.vertical(153.47-super.CenterRobotLength);
	}

}

