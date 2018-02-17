package org.usfirst.frc.team5066.autonomous2018;
import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LRT extends AutonControlScheme {

	public LRT(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		super.vertical(261 - super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(182, Arm.Position.HIGHSCALE, false);
		super.rotate(90, false, Arm.Position.HIGHSCALE);
		super.verticalReverse(38, Arm.Position.HIGHSCALE, false);
		intake.autonOuttake();
	}

}
