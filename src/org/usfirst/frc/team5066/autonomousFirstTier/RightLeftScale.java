package org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RightLeftScale extends AutonControlScheme {
	
	public RightLeftScale(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);

	@Override
	public void moveAuton() {		
		super.vertical(120+(45/2)+56+13+super.CenterRobotLength);
		super.rotate(90,true);
		super.vertical(132-29.69+90);
		super.rotate(90, true);
		super.verticalReverse(299.65-(120+(45/2)+56+13+super.CenterRobotLength), Arm.Position.LEVELSCALE, false);
		intake.autonOuttake();
	}

}
