package Autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class MLSOL extends AutonControlScheme {

	public MLSOL(SingDrive drive, Port gyroPort) {
		super(drive, gyroPort);
	}

	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate(90, true);
		super.vertical(84-super.CenterRobotWidth);
		super.rotate(90, false);
		super.vertical(105-super.CenterRobotLength);
		//Drop PC
		super.vertical(- (32.5-super.CenterRobotWidth));
		super.rotate(90, false);
		//Lower PC manipulator
		super.vertical(54-super.CenterRobotLength);
		//pick up PC
		super.vertical(-54);
		super.rotate(90, true);
		super.vertical(201-super.CenterRobotWidth);
	}

}
