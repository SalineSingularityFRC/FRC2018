package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class LRSRS extends AutonControlScheme {

	public LRSRS(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		//Get to the right switch by going around the back
		super.vertical(229 + super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90,false);
		super.vertical(264-super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		super.rotate(90,false, Arm.Position.TRAVEL);
		super.vertical(45, Arm.Position.SWITCH, false);
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(5, Arm.Position.SWITCH, false);
		//release PC onto switch and reverse
		intake.autonOuttake();
		super.verticalReverse(5, Arm.Position.SWITCH, false);
		//go over to pyramid of PCs
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(41.5-super.CenterRobotLength, Arm.Position.PICKUP, true);
		super.rotate(90, false, Arm.Position.PICKUP);
		//arm is lowered, activate intake
		super.vertical(61, Arm.Position.PICKUP, true);
		super.verticalReverse(61, Arm.Position.TRAVEL, false);
		super.rotate(90, false);
		super.vertical(32.5-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.SWITCH);
		intake.autonOuttake();
	}

}