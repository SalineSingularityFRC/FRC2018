package Autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class LRS extends AutonControlScheme{

	public LRS(SingDrive drive) {
		super(drive);
	}

	@Override
	public void moveAuton() {
		super.vertical(super.CenterRobotLength);
		super.rotate(90, false);
		super.vertical(264-super.CenterRobotWidth);
		super.rotate(90, true);
		super.vertical(168-(2*super.CenterRobotLength));
		//lift arm
		super.rotate(90, true);
		//place block
	}

}
