package src.org.usfirst.frc.team5066.controller2018;

import org.usfirst.frc.team5066.library.SingularityDrive;

import org.usfirst.frc.team5066.robot.LowGoalShooter;
//import org.usfirst.frc.team5066.robot.SingularityArm;
//import org.usfirst.frc.team5066.robot.SingularityConveyer;
import org.usfirst.frc.team5066.robot.SingularityClimber;
import org.usfirst.frc.team5066.robot.SingularityIntake;
import org.usfirst.frc.team5066.robot.SingularityLEDs;

import edu.wpi.first.wpilibj.Joystick;

public interface ControlScheme {
	//contains all control methods that will be called in Robot.java
	//such as moveElevator(), mecanumDrive(), etc.
	
	//implemented by all control scheme classes in the controlSchemes package, which take input from controller classes
	//and are called by the ControlSystem class
	
	//public void controlBallOutput(SingularityBallOutput conveyer);
	
	public void drive(SingularityDrive sd, boolean squaredInputs);
	public void controlShooter(LowGoalShooter lGS);
	public void controlClimber(SingularityClimber climber);
	public void controlIntake(SingularityIntake intake);
	public void controlLEDs(SingularityLEDs robotLEDs);

	
	//SingularityDrive sd = new SingularityDrive(1,2,3,4);
}