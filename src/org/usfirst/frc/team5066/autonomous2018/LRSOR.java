package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class LRSOR extends AutonControlScheme {

	public LRSOR(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		//go around behind the switch to get to right switch
		super.vertical(229 + super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90,false);
		super.vertical(264-super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		super.rotate(90,false, Arm.Position.TRAVEL);
		//lift arm
		super.vertical(45, Arm.Position.SWITCH, false);
		//rotating to face backwards to drop off cube
		super.rotate(90, true, Arm.Position.SWITCH);
		super.verticalReverse(super.CenterRobotLength, Arm.Position.SWITCH, false);
		//release PC
		intake.autonOuttake();
		super.vertical(super.CenterRobotLength, Arm.Position.SWITCH, false);
		//go over to pyramid of PCs
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(41.5-super.CenterRobotLength, Arm.Position.PICKUP, true);
		super.rotate(90, false, Arm.Position.PICKUP);
		//arm is lowered, activate intake
		super.vertical(61, Arm.Position.PICKUP, true);
		//go around the back of the switch to get to left side
		super.verticalReverse(61, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		//should get us close to null zone of right side
		super.vertical(130, Arm.Position.TRAVEL, false);
	}

}

