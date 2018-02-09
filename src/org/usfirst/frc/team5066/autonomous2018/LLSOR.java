package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class LLSOR extends AutonControlScheme {

	public LLSOR(SingDrive drive, ADXRS450_Gyro gyro) {
		super(drive, gyro);
	}

	@Override
	public void moveAuton() {
		super.vertical(168-super.CenterRobotLength);
		//raise arm
		super.rotate(90, false);
		//Drop PC
		super.rotate(90, false);
		//lower arm
		super.vertical(30-super.CenterRobotLength);//don't know exactly
		super.rotate(90,true);
		super.vertical(20);//don't know exactly
		//pick up block
		super.vertical(20, -0.5);
		super.rotate(90, true);
		super.vertical(48-super.CenterRobotLength);
		super.rotate(90, false);
		super.vertical(264-super.CenterRobotWidth);

	}

}