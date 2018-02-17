package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.*;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LLSLT extends AutonControlScheme {

	public LLSLT(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		
	}

	@Override
	public void moveAuton() {
		
		LLS lls = new LLS(super.drive, super.gyro, super.arm, super.intake);
		lls.moveAuton();
		
		
		
	}

}
