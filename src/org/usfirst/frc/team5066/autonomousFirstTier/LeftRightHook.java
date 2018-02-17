package src.org.usfirst.frc.team5066.autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftRightHook extends AutonControlScheme{

	public LeftRightHook(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		//go around behind the switch to get to right switch
		super.vertical(229 + super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		//TODO check the following distance
		super.vertical(264-super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		super.rotate(90,false, Arm.Position.SWITCH);
		//lift arm
		//TODO check the following distance
		super.vertical(45, Arm.Position.SWITCH, false);
		//release PC
		intake.autonOuttake();
	}

}
