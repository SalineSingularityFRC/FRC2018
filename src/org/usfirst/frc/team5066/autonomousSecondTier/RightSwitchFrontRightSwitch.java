package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RightSwitchFrontRightSwitch extends AutonControlScheme {
	
	public RightSwitchFrontRightSwitch(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}
	
	@Override
	public void moveAuton() {
		// TODO Auto-generated method stub
		super.verticalReverse(super.CenterRobotLengthWithArm+1,Arm.Position.SWITCH, false);
		super.rotate(90, true,Arm.Position.PICKUP);
		super.vertical((153.5/2)-18-(45/2)+(13/2),Arm.Position.PICKUP, true);
		super.verticalReverse((153.5/2)-18-(45/2)+(13/2),Arm.Position.SWITCH, false);
		super.rotate(90, false);
		super.vertical(super.CenterRobotLengthWithArm+1);
		intake.autonOuttake();
	}

}
