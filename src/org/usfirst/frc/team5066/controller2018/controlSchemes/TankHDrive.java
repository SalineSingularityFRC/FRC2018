package src.org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.LogitechController;
import org.usfirst.frc.team5066.controller2017.SingularityBallOutput;
import org.usfirst.frc.team5066.controller2017.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityClimber;
import org.usfirst.frc.team5066.robot.SingularityIntake;
import org.usfirst.frc.team5066.robot.SingularityLEDs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankHDrive implements ControlScheme {
	
	XboxController xbox;
	LogitechController logitech;
	SpeedMode speedMode;
	boolean on, prevY;
	
	/**
	 * Constructor for TankHDrive
	 * 
	 * @param xboxPort the channel for the xbox 
	 * 		controller (Change in Driver Station)
	 * @param logitechPort the channel for the logitech
	 * 		joy stick (Change in Driver Station)
	 */
	public TankHDrive(int xboxPort, int logitechPort) {
		xbox = new XboxController(xboxPort);
		logitech = new LogitechController(logitechPort);
		
	}
	
	/**
	 * Driven with tank drive from an xbox controller.
	 * Includes speedMode with bumpers
	 * 
	 * @param sd the drive object
	 * @parem squaredInputs	true means we square joystick
	 * 			inputs, making precise control a little easier.
	 */
	@Override
	public void drive(SingularityDrive sd, boolean squaredInputs) {
		
		//set speedMode
		if(xbox.getLB()) {
			speedMode = SpeedMode.SLOW;
		} else if(xbox.getRB()) {
			speedMode = SpeedMode.FAST;
		} else {
			speedMode = SpeedMode.NORMAL;
		}
		
		sd.hDriveTank(xbox.getLS_Y(), xbox.getRS_Y(), horizontalMax(xbox.getLS_X(), xbox.getRS_X()), squaredInputs, speedMode);
		
	}
	
	/**
	 * Control the shooter with the logitech trigger
	 * 
	 * @param lGS a lowGoalShooter object
	 */
	@Override
	public void controlShooter(LowGoalShooter lGS){
		lGS.setSpeed(logitech.getTrigger(), false);
	}
	
	/**
	 * control the climber with the logitech x axis
	 * 
	 * @param climber a SingularityClimber object
	 */
	public void controlClimber(SingularityClimber climber){
		climber.setSpeed(logitech.getStickX());
	}
	
	/**
	 * control the intake with toggle (Y button)
	 * reverse with x button
	 * 
	 * @param intake a SingularityIntake object
	 */
	public void controlIntake(SingularityIntake intake){
		if (xbox.getYButton() && !prevY) on = on ? false : true;
		if (!on) intake.setSpeed(0.0);
		else if (xbox.getXButton()) intake.setSpeed(-1.0);
		else intake.setSpeed(1.0);
	}
	
	/**
	 * 
	 * @param lS a value (in this case, the left Stick X value)
	 * @param rS a value (in this case, the right Stick X Value)
	 * @return the one farthest from zero
	 */
	private double horizontalMax(double lS, double rS) {
		
		if (lS < 0 && rS < 0) 
			return Math.min(lS, rS);
		return Math.max(lS,  rS);
		
	}

	/**
	 * TODO get LEDs working
	 * 
	 * @param robotLEDs a SingularityLEDs object
	 */
	@Override
	public void controlLEDs(SingularityLEDs robotLEDs) {
		// TODO Auto-generated method stub
		
	}
	

}
