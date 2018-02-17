package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class LLSOL extends AutonControlScheme{

	public LLSOL(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		//dog leg to switch
		super.vertical(168-super.CenterRobotLength);
		//rotating to face to drop off cube
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(super.CenterRobotWidth, Arm.Position.SWITCH, false);
		intake.autonOuttake();
		//Pick up PC
		//reverse a distance so that we can rotate without hitting side of switch
		super.verticalReverse(super.CenterRobotWidth, Arm.Position.SWITCH, false);
		super.rotate(90, false, Arm.Position.PICKUP);
		//go for PC that is second farthest out of the pyramid
		super.vertical(49-super.CenterRobotLength, Arm.Position.PICKUP, false);
		super.rotate(90, true, Arm.Position.PICKUP);
		super.vertical(60.5+super.CenterRobotCorner-super.CenterRobotLength, Arm.Position.PICKUP, true);
		//head over to left null zone
		super.verticalReverse((95.25+super.CenterRobotWidth-super.CenterRobotLength), Arm.Position.TRAVEL, false);//back up to clear switch
		super.rotate(90, true, Arm.Position.TRAVEL);//turn toward opp switch
		super.vertical(153.47-super.CenterRobotLength);//go up 12 away from edge of null zone
	}

}