package src.org.usfirst.frc.team5066.autonomousSecondTier;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

public class RightScaleRightScale extends AutonControlScheme{
	
	public RightScaleRightScale(SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		super(drive, gyro, arm, intake);
}

	@Override
	public void moveAuton() {
		super.vertical(36.825-super.CenterRobotLength, Arm.Position.HIGHSCALE, false);
		super.vertical(36.825, Arm.Position.PICKUP, true);
		super.verticalReverse(73.65, Arm.Position.HIGHSCALE, false);
		intake.autonOuttake();
	}
	
}
