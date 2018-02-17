package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class RLSV extends AutonControlScheme {

	public RLSV(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		RLS rls = new RLS(super.drive, super.gyro, super.arm, super.intake);
		rls.moveAuton();
		//lightning bolt to switch
		/*super.vertical(70-(super.CenterRobotLength/2));
		super.rotate(90,true);
		super.vertical(180.31-super.CenterRobotWidth);
		super.rotate(90,false, Arm.Position.SWITCH);
		super.vertical(70-(super.CenterRobotLength/2), Arm.Position.SWITCH, false);
		intake.autonOuttake();*/
		//pick up PC
		super.vertical(- (32.5-super.CenterRobotWidth));
		super.rotate(90,false, Arm.Position.PICKUP);
		super.vertical(60.5-super.CenterRobotLength, Arm.Position.PICKUP, true);
		//go to vault
		super.vertical(-24, Arm.Position.TRAVEL, false);
		super.rotate(90, false);
		super.vertical(59.5-super.CenterRobotLength*2);
	}

}
