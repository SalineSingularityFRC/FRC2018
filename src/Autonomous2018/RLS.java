package src.Autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import edu.wpi.first.wpilibj.SPI.Port;



public class RLS extends AutonControlScheme{
	
	public RLS(SingDrive drive, Port gyroPort) {
		super(drive,gyroPort);
	}
	
	@Override
	public void moveAuton() {
		super.vertical(0.5, 120-(super.CenterRobotLenght)-(45/2));
		super.rotate(90,true);
		super.vertical(0.5,120);
		super.rotate(90,false);
		//This is where we´d want to lift the manipulator
		super.vertical(0.5,45);
		//This is where we´d want to deposit the PC
	}
}
