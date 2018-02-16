package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class RRSOL extends AutonControlScheme {

	public RRSOL(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		//lightning bolt to switch
		super.vertical(70-(super.CenterRobotLength/2));
		super.rotate(90, true);
		super.vertical(72.31-super.CenterRobotWidth);
		super.rotate(90, false, Arm.Position.SWITCH);
		super.vertical(70-(super.CenterRobotLength/2), Arm.Position.SWITCH, false);
		intake.autonOuttake();
		//pick up PC
		super.vertical(-32.5);
		super.rotate(90, true, Arm.Position.PICKUP);
		super.vertical(60.5-super.CenterRobotLength, Arm.Position.PICKUP, true);
		//head towards opponet left switch
		super.vertical(-(60.5-super.CenterRobotLength), Arm.Position.TRAVEL, true);//go back to previous position
		super.rotate(180, true, Arm.Position.TRAVEL);//rotate 180
		super.vertical(40.25+super.CenterRobotWidth);//clear the edge of switch
		super.rotate(90, true);//turn toward opp side
		super.vertical(113.5+super.CenterRobotLength/2);//go forward to clear edge of switch
		super.rotate(90, true);//turn toward left side
		super.vertical(177.75 +super.CenterRobotWidth*2);//cross over the switch
		super.rotate(90, true);//turn toward opp side
		super.vertical(127);//go a foot away from the edge of the null zone
	}

}
