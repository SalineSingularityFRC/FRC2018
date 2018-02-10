package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;



public class RLS extends AutonControlScheme{
	
	public RLS(SingDrive drive, AHRS gyro) {
		super(drive, gyro);
	}
	
	@Override
	public void moveAuton() {
		super.vertical(0.5, 120-(super.CenterRobotLength)-(45/2));
		super.rotate(90,true);
		super.vertical(0.5,120);
		super.rotate(90,false);
		//This is where we'd want to lift the manipulator
		super.vertical(0.5,45);
		//This is where we'd want to deposit the PC
	}
}
