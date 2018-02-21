package org.usfirst.frc.team5066.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

	private VictorSPX left;
	private VictorSPX right;
	
	private DigitalInput input;
	private boolean currentInput, lastInput;
	
	private Timer timer;
	private final double INTAKETIME = 0.25;
	private final double OVERRIDETIME = 3.0;
	
	private final double AUTONOUTTAKE = 1.0;

	private final double INSPEED = 1.0;
	private final double OUTSPEED = -1.0;
	private final double ROTATESPEED = 0.5;

	public Intake(int leftPort, int rightPort, int inputPort) {

		left = new VictorSPX(leftPort);
		right = new VictorSPX(rightPort);
		
		input = new DigitalInput(inputPort);
		
		timer = new Timer();
		
		currentInput = true;
		lastInput = true;
		
	}

	public boolean controlIntake(boolean leftIn, boolean leftOut, boolean rightIn, boolean rightOut) {
		
		currentInput = input.get();
		
		if (!currentInput) {
			timer.reset();
		}
		
		if (currentInput && !lastInput) {
			timer.reset();
			timer.start();
		}

		if (leftIn && rightOut) {
			left.set(ControlMode.PercentOutput, ROTATESPEED);
			right.set(ControlMode.PercentOutput, ROTATESPEED);
		}

		else if (rightIn && leftOut) {
			right.set(ControlMode.PercentOutput, ROTATESPEED);
			left.set(ControlMode.PercentOutput, ROTATESPEED);
		}

		else {

			if (leftIn && rightIn && timer.get() > INTAKETIME && timer.get() < OVERRIDETIME) {
				left.set(ControlMode.PercentOutput, 0.0);
				right.set(ControlMode.PercentOutput, 0.0);
			}
			
			else {

				if (leftIn) {
					left.set(ControlMode.PercentOutput, -INSPEED);

				}

				else if (leftOut) {
					left.set(ControlMode.PercentOutput, -OUTSPEED);

				}

				else {
					left.set(ControlMode.PercentOutput, 0.0);
				}

				if (rightIn) {
					right.set(ControlMode.PercentOutput, INSPEED);

				}

				else if (rightOut) {

					right.set(ControlMode.PercentOutput, OUTSPEED);
				}

				else {
					right.set(ControlMode.PercentOutput, 0.0);
				}
			}
		}
		
		lastInput = currentInput;
		return input.get();

	}
	
	public void manualIntake(double leftStick, double rightStick, double exponent){
		
		left.set(ControlMode.PercentOutput, Math.pow(leftStick, exponent));
		right.set(ControlMode.PercentOutput, Math.pow(rightStick, exponent));
	}
	
	public void printDigitalInput() {
		
		SmartDashboard.putBoolean("We have a cube: ", input.get());
	}
	public boolean getInput() {
		return input.get();
	}
	
	public void autonOuttake() {
		
		timer.reset();
		timer.start();
		
		while (timer.get() < AUTONOUTTAKE && DriverStation.getInstance().isAutonomous()) {
			right.set(ControlMode.PercentOutput, OUTSPEED);
			left.set(ControlMode.PercentOutput, OUTSPEED);
		}
		
		right.set(ControlMode.PercentOutput, 0.0);
		left.set(ControlMode.PercentOutput, 0.0);
		
		timer.reset();
		
	}

}
