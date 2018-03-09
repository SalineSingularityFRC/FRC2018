package org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RightSwitchSideRightSwitchWorking extends AutonControlScheme {
	
	public RightSwitchSideRightSwitchWorking(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		// TODO Auto-generated method stub
		
		super.verticalReverse(super.CenterRobotCorner+1, true, false);
		super.rotate(90, true);
		super.vertical(56/2+super.CenterRobotLength);
		super.rotate(90, false,false);
		super.vertical((153.5/2)-(45/2)+(13/2),false,true);
		super.verticalReverse((153.5/2)-(45/2)+(13/2), true, false);
		super.rotate(90, false);
		super.vertical(56/2+super.CenterRobotLength,true, false);
		super.rotate(90, true,true);
		super.vertical(super.CenterRobotCorner);
		intake.autonOuttake();
	}


}
