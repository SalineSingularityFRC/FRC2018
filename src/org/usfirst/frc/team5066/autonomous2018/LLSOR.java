package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class LLSOR extends AutonControlScheme {

	public LLSOR(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		
		LLS lls = new LLS(super.drive, super.gyro, super.arm, super.intake);
		lls.moveAuton();
		
		/*
		//dog leg to switch
		super.vertical(168-super.CenterRobotLength);
		//rotating to face to drop off cube
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(super.CenterRobotWidth, Arm.Position.SWITCH, false);
		intake.autonOuttake();
		*/
		
		//Pick up PC
		//reverse a distance so that we can rotate without hitting side of switch
		super.verticalReverse(super.CenterRobotWidth, Arm.Position.SWITCH, false);
		super.rotate(90, true, Arm.Position.PICKUP);
		//go for PC that is second farthest out of the pyramid
		super.vertical(49-super.CenterRobotLength, Arm.Position.PICKUP, false);
		super.rotate(90, true, Arm.Position.PICKUP);
		super.vertical(60.5+super.CenterRobotCorner-super.CenterRobotLength, Arm.Position.PICKUP, true);
		//Head behind the switch to get to right switch
		
		//TODO What is happening here
		
		super.verticalReverse(60.5, Arm.Position.TRAVEL, false);//back up to previous position
		super.rotate(180, false, Arm.Position.TRAVEL);//do a 180 //Why????
		super.vertical(34.75+super.CenterRobotWidth);//clear the switch
		super.rotate(90, false);//turn toward opp side
		//go around the back of switch
		super.vertical(110, Arm.Position.TRAVEL, false); 
		super.rotate(90, false, Arm.Position.TRAVEL);
		super.vertical(240.5);//go all the way across the field to get to OR
	}

}