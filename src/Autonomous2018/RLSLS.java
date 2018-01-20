package src.Autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class RLSLS extends AutonControlScheme {
	
	public RLSLS(SingDrive drive, Port gyroPort) {
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
		
		//This is where the robot grabs a new PC
		super.vertical(0.5,-22.5);
		super.rotate(90,false);
		super.vertical(0.5,1);//1 is placeholder value, value should be distance to PC
		//This is where we´d want to pick up the cube
		
		//This is where we´d have the robot deposit its new PC onto the switch
		super.vertical(0.5, -1);//1 is placeholder value, same as line 27
		super.rotate(90,true);
		//This is where we´d want to deposit the PC
	}

}
