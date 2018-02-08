package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;


public class SideStraight extends AutonControlScheme {

	public SideStraight(SingDrive drive) {
		super(drive);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		
		super.rotate(90,true);
		
	}

}
