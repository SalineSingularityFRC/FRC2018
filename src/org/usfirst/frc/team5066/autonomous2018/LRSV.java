package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class LRSV extends AutonControlScheme {

	public LRSV(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton(){
		
		LRS lrs = new LRS(super.drive, super.gyro, super.arm, super.intake);
		lrs.moveAuton();
		
		/*
		//go around behind the switch to get to right switch
		super.vertical(229 + super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90,false);
		super.vertical(264-super.CenterRobotWidth, Arm.Position.TRAVEL, false);
		super.rotate(90,false, Arm.Position.TRAVEL);
		//lift arm
		super.vertical(45, Arm.Position.SWITCH, false);
		//rotating to face backwards to drop off cube
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(super.CenterRobotLength, Arm.Position.SWITCH, false);
		//release PC
		intake.autonOuttake();
		*/
		
		super.verticalReverse(super.CenterRobotLength, Arm.Position.SWITCH, false);
		//go over to pyramid of PCs
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(41.5-super.CenterRobotLength, Arm.Position.PICKUP, true);
		super.rotate(90, false, Arm.Position.PICKUP);
		//arm is lowered, activate intake
		super.vertical(61, Arm.Position.PICKUP, true);
		//lightning bolt over to outside of exchange boundaries
		super.rotate(90, true, Arm.Position.TRAVEL);
		super.vertical(70-super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(55-super.CenterRobotWidth, Arm.Position.EXCHANGE, false);
		//rotate to line up with exchange, lower arm
		super.rotate(90, true, Arm.Position.EXCHANGE);
	}

}
