package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Timer;


public class SideStraight extends AutonControlScheme {

	public SideStraight(SingDrive drive, AHRS gyro) {
		super(drive, gyro);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		
		super.vertical(140.0);;
		
	}

}
