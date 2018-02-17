package autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class LeftLeftLightningBolt extends AutonControlScheme {

	public LeftLeftLightningBolt(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
		
	}

	@Override
	public void moveAuton() {
		
		super.vertical(70.0 - 0.5 * super.CenterRobotLength, Arm.Position.TRAVEL, false);
		super.rotate(90.0, false, Arm.Position.TRAVEL);
		super.vertical(72.31, Arm.Position.TRAVEL, false);
		super.rotate(90.0, true, Arm.Position.SWITCH);
		super.vertical(70 - 0.5 * super.CenterRobotLength, Arm.Position.SWITCH, false);
		intake.autonOuttake();
		
	}

}
