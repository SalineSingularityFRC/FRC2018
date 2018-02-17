package org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.controller2018.ControlScheme;
import org.usfirst.frc.team5066.controller2018.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.DrivePneumatics;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.robot.Lift;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.singularityDrive.SixWheelDrive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankDrive implements ControlScheme {
	
	XboxController logitechDrive;
	XboxController logitechSystems;
	SpeedMode speedMode;
	boolean on, prevY;
	
	boolean rBCurrent;
	boolean rBPrevious;
	boolean lBCurrent;
	boolean lBPrevious;
	
	boolean speed;
	
	boolean safetyDisabled;
	boolean leftLowLimit;
	boolean rightLowLimit;
	
	Arm.Position lastPressed;
	
	public TankDrive(int logitechDrivePort, int logitechSystemsPort) {
		logitechDrive = new XboxController(logitechDrivePort);
		logitechSystems = new XboxController(logitechSystemsPort);
		
		rBCurrent = false;
		rBPrevious = false;
		lBCurrent = false;
		lBPrevious = false;
		
		safetyDisabled = false;
		leftLowLimit = false;
		rightLowLimit = false;
		
	}
	
	@Override
	public void drive(SingDrive sd, DrivePneumatics dPneu) {

		if (logitechDrive.getRB())
			speed = true;
		else if (logitechDrive.getLB())
			speed = false;

		if (speed)
			dPneu.setForward();
		
		else
			dPneu.setReverse();
		
		((SixWheelDrive) sd).tankDrive(logitechDrive.getLS_Y(), logitechDrive.getRS_Y(), 2.5, speedMode.FAST);
		
	}

	@Override
	public void lift(Lift lift, Timer timer) {
		
		if (!safetyDisabled && logitechDrive.getPOVDown() && logitechDrive.getAButton() && logitechDrive.getLB() 
				&& logitechDrive.getRB() && (logitechDrive.getTriggerLeft() > 0.1) && (logitechDrive.getTriggerRight() > 0.1)) {
			safetyDisabled = true;
			DriverStation.reportError("SAFETY DISABLED", true);
		}
		
		//test to see if safety is on
		if (timer.get() >= 105.0 || safetyDisabled) {
			
			//release left lift until lower limit switch is pressed
			if (!leftLowLimit && lift.releaseLiftLeft(logitechSystems.getL3())) {
				leftLowLimit = true;
				DriverStation.reportError("left lower limit reached", true);
			}
			
			//release right lift until lower limit switch is pressed
			if (!rightLowLimit && lift.releaseLiftRight(logitechSystems.getR3())) {
				rightLowLimit = true;
				DriverStation.reportError("right lower limit reached", true);
			}
		}
		//lifts right lift. When reached upper limit switch, ping driver
		if (rightLowLimit && lift.controlRightLift(logitechSystems.getRS_Y())) {
			DriverStation.reportError("right upper limit reached", true);
		}
				
		//lifts left lift. When reached upper limit switch, ping driver
		if (leftLowLimit && lift.controlLeftLift(logitechSystems.getLS_Y())) {
			DriverStation.reportError("left upper limit reached", true);
		}
		
		
		//lift.resetLeft(logitechDrive.getBackButton());
		//lift.resetRight(logitechDrive.getStartButton());
		
		
	}

	@Override
	public void arm(Arm arm) {
		
		if(logitechSystems.getAButton()){
			lastPressed = Arm.Position.PICKUP;
		}
		else if (logitechSystems.getBButton()) {
			lastPressed = Arm.Position.TRAVEL;
		}
		else if (logitechSystems.getXButton()){
			lastPressed = Arm.Position.EXCHANGE;
		}
		else if (logitechSystems.getYButton()){
			lastPressed = Arm.Position.SWITCH;
		}
		else if (logitechSystems.getStartButton()){
			lastPressed = Arm.Position.START;
		}
		else if (logitechSystems.getBackButton()){
			lastPressed = Arm.Position.PORTAL;
		}
		else if (logitechSystems.getPOVLeft()){
			lastPressed = Arm.Position.LOWSCALE;
		}
		else if (logitechSystems.getPOVUp()){
			lastPressed = Arm.Position.LEVELSCALE;
		}
		else if (logitechSystems.getPOVRight()){
			lastPressed = Arm.Position.HIGHSCALE;
		}
		
		arm.setArm(lastPressed);
		
	}

	@Override
	public void intake(Intake intake) {
		
		intake.controlIntake(logitechSystems.getTriggerLeft() > 0.5, logitechSystems.getLB(), 
				logitechSystems.getTriggerRight() > 0.5, logitechSystems.getRB());
		
		intake.printDigitalInput();
		
	}
	

}
