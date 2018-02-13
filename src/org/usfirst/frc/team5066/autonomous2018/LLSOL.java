package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class LLSOL extends AutonControlScheme{

	public LLSOL(SingDrive drive, AHRS gyro, Arm arm) {
		super(drive, gyro, arm);
	}

	@Override
	public void moveAuton() {
		super.vertical(168-super.CenterRobotLength, Arm.Position.TRAVEL);
		//raise arm
		super.rotate(90, false, Arm.Position.SWITCH);
		//Drop PC
		super.rotate(90, false, Arm.Position.SWITCH);
		//lower arm
		super.vertical(30-super.CenterRobotLength, Arm.Position.TRAVEL);//don't know exactly
		super.rotate(90,true, Arm.Position.TRAVEL);
		super.vertical(20, Arm.Position.TRAVEL);//don't know exactly
		//pick up block
		super.vertical(-0.5, 20, Arm.Position.TRAVEL);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(120-super.CenterRobotLength, Arm.Position.SWITCH);
		
	}

}