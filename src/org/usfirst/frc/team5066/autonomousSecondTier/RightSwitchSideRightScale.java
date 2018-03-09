package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RightSwitchSideRightScale extends AutonControlScheme {
	
	public RightSwitchSideRightScale(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}
	
	@Override
	public void moveAuton() {
		/*
		super.verticalReverse(super.CenterRobotCorner,Arm.Position.SWITCH,false);
		super.rotate(90,false);
		super.vertical((52/2)+2+13+super.CenterRobotWidth);
		super.rotate(90, true, Arm.Position.PICKUP);
		super.vertical(super.CenterRobotLength);
		super.rotate(90, true);
		super.vertical(13,Arm.Position.PICKUP,true);
		super.verticalReverse(super.CenterRobotCorner, Arm.Position.PICKUP, false);
		super.rotate(90, true);
		super.vertical(13);
		super.rotate(90, false,Arm.Position.LEVELSCALE);
		super.verticalReverse(299.65-(261.47+13+super.CenterRobotCorner), Arm.Position.LEVELSCALE,false);
		intake.autonOuttake();
		*/
	
	}
}
