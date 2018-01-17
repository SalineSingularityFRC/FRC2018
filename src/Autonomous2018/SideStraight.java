package Autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

public class SideStraight extends AutonControlScheme {

	public SideStraight(SingDrive drive) {
		super(drive);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void MoveAuton() {
		super.vertical(0.5, 60);
		// TODO Auto-generated method stub
		
	}

}
