package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class LLSOR extends AutonControlScheme {

	public LLSOR(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(168-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		//raise arm
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(5, Arm.Position.SWITCH, false);
		//release PC
		intake.autonOuttake();
		super.vertical(-0.5, 5, Arm.Position.TRAVEL, false);
		//head over to pyramid of PCs
		super.rotate(90, false, Arm.Position.SWITCH);
		//lower arm
		super.vertical(28-2*super.CenterRobotWidth, Arm.Position.TRAVEL, false);//don't know exactly
		super.rotate(90,true, Arm.Position.PICKUP);
		//pick up block
		super.vertical(61, Arm.Position.PICKUP, true);
		//Head behind the switch to get to right switch
		super.verticalReverse(61, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(50-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		//raise arm
		super.vertical(264-super.CenterRobotWidth, Arm.Position.SWITCH, false);
		super.rotate(90, false, Arm.Position.SWITCH);
		//release PC
		intake.autonOuttake();
	}

}