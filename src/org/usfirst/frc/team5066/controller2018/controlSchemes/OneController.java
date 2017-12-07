 package src.org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.controller2017.ControlScheme;
import org.usfirst.frc.team5066.controller2017.LogitechController;
import org.usfirst.frc.team5066.controller2017.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.LowGoalShooter;
import org.usfirst.frc.team5066.robot.SingularityClimber;
import org.usfirst.frc.team5066.robot.SingularityIntake;
import org.usfirst.frc.team5066.robot.SingularityLEDs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class OneController implements ControlScheme {
	
	XboxController xbox;
	LogitechController logitech;
	SpeedMode speedMode;
	boolean on, prevY, lb, prevLB;
	
	/**
	 * Constructor for OneController
	 * 
	 * @param xboxPort the channel for the xbox 
	 * 		controller (Change in Driver Station)
	 * @param logitechPort the channel for the logitech
	 * 		joy stick (Change in Driver Station). Yes, the
	 * 		logitech is for LEDs
	 */
	public OneController(int xboxPort, int logitechPort) {
		xbox = new XboxController(xboxPort);
		logitech = new LogitechController(logitechPort);
		on = false;
		prevLB = false;
		
		speedMode = SpeedMode.FAST;
	}
	
	/**
	 * Updated for a six wheel drive.
	 * xbox left Y Stick for translation, right X for rotation
	 * Includes speedMode with toggle for the leftBumper
	 * 
	 * @param sd the drive object
	 * @parem squaredInputs	true means we square joystick
	 * 			inputs, making precise control a little easier.
	 */
	@Override
	public void drive(SingularityDrive sd, boolean squaredInputs) {
		if (xbox.getLB()) lb = true;
		else lb = false;
		
		if (lb && !prevLB) {
			if (speedMode == SpeedMode.FAST) {
				speedMode = SpeedMode.SLOW;
			}
			else {
				speedMode = SpeedMode.FAST;
			}
		}
		
		sd.arcadeSixWheel(-xbox.getLS_Y(), xbox.getRS_X(), squaredInputs, speedMode);
		
		prevLB = lb;

	}
	
	/**
	 * Control the shooter with the xbox right trigger
	 * 
	 * @param lGS a lowGoalShooter object
	 */
	@Override
	public void controlShooter(LowGoalShooter lGS) {
		lGS.setSpeed(xbox.getTriggerRight() > 0.6, false);
	}
	
	/**
	 * control the climber with the xbox right bumper
	 * 
	 * @param climber a SingularityClimber object
	 */
	@Override
	public void controlClimber(SingularityClimber climber) {
		if (xbox.getRB()) climber.setSpeed(1.0);
		else climber.setSpeed(0.0);
	}
	
	/**
	 * control the intake with toggle (Y button)
	 * reverse with x button
	 * 
	 * @param intake a SingularityIntake object
	 */
	@Override
	public void controlIntake(SingularityIntake intake) {
		
		if (xbox.getYButton() && !prevY) {
			if (!on) on = true;
			else on = false;
		}
		if (xbox.getXButton()) intake.setSpeed(-1.0);
		else if (!on) intake.setSpeed(0.0);
		else intake.setSpeed(0.6);
		
		if (xbox.getYButton()) prevY = true;
		else prevY = false;
		
		//SmartDashboard.putString("DB/String 5", "Intake Speed: " + intake.getSpeed());
	}
	
	/**
	 * TODO get LEDs working
	 * 
	 * @param robotLEDs a SingularityLEDs object
	 */
	public void controlLEDs(SingularityLEDs robotLEDs){
		if(logitech.getStickX() > 0.2 && (!xbox.getYButton() || !xbox.getTrigger())){
			robotLEDs.turnBlue();
		}
		else if(logitech.getStickX() < -0.2 && (!xbox.getYButton() || !xbox.getTrigger())){
			robotLEDs.turnYellow();
		}
		else if(logitech.getTrigger()){
			robotLEDs.oscillate();
		}
		else if (xbox.getRB() && !(logitech.getTrigger())){
			robotLEDs.oscillate();
		}
		else if (xbox.getTrigger()){
			robotLEDs.flashBlue();
		}
		else if(xbox.getYButton()){
			robotLEDs.flashYellow();
		}
	}

}