package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;



public class RLS extends AutonControlScheme{
	
	public RLS(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}
	
	@Override
	public void moveAuton() {
		super.vertical(95-(super.CenterRobotLength));
		super.rotate(90,true);
		super.vertical(329.06-super.CenterRobotWidth);
		super.rotate(90,false, Arm.Position.SWITCH);
		super.vertical(45, Arm.Position.SWITCH, false);
		intake.autonOuttake();
	}
}
