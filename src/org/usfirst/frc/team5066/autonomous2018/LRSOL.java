package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class LRSOL extends AutonControlScheme {

	public LRSOL(SingDrive drive, AHRS gyro, Arm arm) {
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
		super.vertical(-32.5, Arm.Position.SWITCH);
		//Lower PC
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(41.5-super.CenterRobotLength, Arm.Position.PICKUP);
		//Pick up PC
		super.rotate(180, true, Arm.Position.PICKUP);
		super.vertical(88.75+super.CenterRobotWidth, Arm.Position.TRAVEL);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(96.75+super.CenterRobotLength/2, Arm.Position.TRAVEL);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(177.75 +super.CenterRobotWidth*2, Arm.Position.TRAVEL);
		super.rotate(90, true, Arm.Position.SWITCH);
	}

}
