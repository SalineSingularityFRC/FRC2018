package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class LLS extends AutonControlScheme{

	public LLS(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		//dog leg to left switch
		super.vertical(168-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.SWITCH);
		//lift arm
		super.vertical(58.56-super.CenterRobotWidth, Arm.Position.SWITCH, false);
		intake.autonOuttake();
	}

}
