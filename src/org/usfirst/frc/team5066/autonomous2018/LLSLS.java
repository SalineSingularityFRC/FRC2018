package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class LLSLS extends AutonControlScheme{

	public LLSLS(SingDrive drive) {
		super(drive);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		super.vertical(168-super.CenterRobotLength);
		//raise arm
		super.rotate(90, false);
		//place block
		super.rotate(90, false);
		//lower arm
		super.vertical(30);//don't know exactly
		super.rotate(90,true);
		super.vertical(20);//don't know exactly
		//pick up block
		super.vertical(20, -0.5);
		super.rotate(90, true);
		super.vertical(30);
		//raise arm
		super.rotate(90, false);
		//place block
	}

}
