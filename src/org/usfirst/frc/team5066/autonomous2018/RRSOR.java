package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class RRSOR extends AutonControlScheme {

	public RRSOR(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		//dog leg to switch
		super.vertical(166-super.CenterRobotLength);
		super.rotate(90, false, Arm.Position.SWITCH);
		super.verticalReverse(super.CenterRobotWidth, Arm.Position.SWITCH, false);
		intake.autonOuttake();
		//pick up PC
		super.vertical(super.CenterRobotWidth, Arm.Position.SWITCH, false);
		super.rotate(90, false, Arm.Position.PICKUP);
		//Pick up PC
		//reverse a distance so that we can rotate without hitting side of switch
		super.vertical(super.CenterRobotWidth, Arm.Position.SWITCH, false);
		super.rotate(90, true, Arm.Position.PICKUP);
		//go for PC that is second farthest out of the pyramid
		super.vertical(49-super.CenterRobotLength, Arm.Position.PICKUP, false);
		super.rotate(90, false, Arm.Position.PICKUP);
		super.vertical(60.5-super.CenterRobotLength, Arm.Position.PICKUP, true);
		//go to null zone
		super.vertical(-(95.25+super.CenterRobotWidth-super.CenterRobotLength), Arm.Position.TRAVEL, false);//back up to clear switch
		super.rotate(90, false);//turn toward opp switch
		super.vertical(153.47-super.CenterRobotLength);//go up 12 away from edge of null zone
	}

}

