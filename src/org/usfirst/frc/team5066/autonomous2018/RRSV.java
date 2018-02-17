package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class RRSV extends AutonControlScheme {

	public RRSV(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		//lightning bolt to switch
		super.vertical(70-(super.CenterRobotLength/2));
		super.rotate(90,true);
		super.vertical(72.31-super.CenterRobotWidth);
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(70-(super.CenterRobotLength/2), Arm.Position.SWITCH, false);
		intake.autonOuttake();
		//pick up PC
		super.vertical(-(32.5-super.CenterRobotLength));
		super.rotate(90, true, Arm.Position.PICKUP);
		super.vertical(60.5-super.CenterRobotLength, Arm.Position.PICKUP, true);
		//lightning bolt toward vault
		super.rotate(90, true, Arm.Position.TRAVEL);//turn toward our side
		super.vertical(63.5-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, false);
		super.vertical(12);
		super.rotate(90, true);
	}

}
