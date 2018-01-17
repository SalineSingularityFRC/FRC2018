package src.Autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

public class RRSRS extends AutonControlScheme {
	
	public RRSRS(SingDrive drive) {
		super(drive);
	}
	
	public void moveAuton() {
		super.vertical(0.5, 60+(super.CenterRobotLength/2));
		//This is where we�d want to lift the manipulator
		super.rotate(90,true);
		super.vertical(0.1,5);//Move forward until collision with switch walls, can be changed
		//This is where we�d want to throw the cube 
		
		//This is where we move back to receive another cube
		super.vertical(0.5,1);//1 is placeholder value, this should be half of diagonal length
		super.rotate(90,true);
		super.vertical(0.5,(super.CenterRobotLength*2));
		//Lower manipulator
		super.rotate(90,false);
		super.vertical(0.5,1);//1 is placeholder value, this should be distance to cube stockpile from switch edge
		//Grab PC
		
		//This is where we place the block onto the switch
		super.rotate(180, true);
		super.vertical(0,5,1);//1 is placeholder value, this should be distance to cube stockpile from switch edge + 
		//diagonal robot length
		super.rotate(90,true);
		super.vertical(0,5,1);//1 is placeholder value should be half the length of the switch+length of robot
		super.rotate(90,true);
		//This is where we�d want to throw the cube
		
	}

}
