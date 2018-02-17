package autonomousFirstTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

public class RightRightScale extends AutonControlScheme {

	public RightRightScale(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
	}

	@Override
	public void moveAuton() {
		super.vertical(324-super.CenterRobotLength);//go to the null zone
		super.rotate(90, true, Arm.Position.HIGHSCALE);//turn toward center
		super.vertical(36.31-super.CenterRobotWidth, Arm.Position.HIGHSCALE, false);//position to be in front of switch
		intake.autonOuttake();
	}

}
