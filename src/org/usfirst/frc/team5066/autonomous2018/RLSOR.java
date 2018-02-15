package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class RLSOR extends AutonControlScheme {

	public RLSOR(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(70-(super.CenterRobotLength/2));
		super.rotate(90,true);
		super.vertical(170.685-super.CenterRobotWidth);
		super.rotate(90,false, Arm.Position.SWITCH);
		super.vertical(70-(super.CenterRobotLength/2), Arm.Position.SWITCH, false);
		intake.autonOuttake();
		super.vertical(- (32.5-super.CenterRobotWidth));
		super.rotate(90, false, Arm.Position.PICKUP);
		super.vertical(127.125-super.CenterRobotLength, Arm.Position.PICKUP, true);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(114+ super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		super.rotate(90, true);
		super.vertical(177.5+ super.CenterRobotWidth*2);
		super.rotate(90, false);
		super.vertical(100.47-super.CenterRobotWidth);
	}

}
