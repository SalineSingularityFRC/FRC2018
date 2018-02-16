package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class LLSLS extends AutonControlScheme{

	public LLSLS(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		//line up with side of switch
		super.vertical(168-super.CenterRobotLength, Arm.Position.SWITCH, false);
		//raise arm and release PC
		super.rotate(90, false, Arm.Position.SWITCH);
		intake.autonOuttake();
		super.vertical(-0.5, 5, Arm.Position.SWITCH, false);
		//head to PC pyramid
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(28 + 2*super.CenterRobotWidth, Arm.Position.TRAVEL, false);//don't know exactly
		super.rotate(90, true, Arm.Position.PICKUP);
		//drive forward and pick up block
		super.vertical(61, Arm.Position.PICKUP, true);//don't know exactly
		//go back to side of switch
		super.verticalReverse(61, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(30, Arm.Position.SWITCH, false);
		//lift arm and release PC
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(5, Arm.Position.SWITCH, false);
		intake.autonOuttake();
	}

}
