package org.usfirst.frc.team5066.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

	private VictorSPX left;
	private VictorSPX right;
	private 

	private final double INSPEED = 1.0;
	private final double OUTSPEED = 1.0;
	private final double ROTATESPEED = 0.5;

	public Intake(int leftPort, int rightPort) {

		left = new VictorSPX(leftPort);
		right = new VictorSPX(rightPort);
	}

	public void controlIntake(boolean leftIn, boolean leftOut, boolean rightIn, boolean rightOut) {

		if (leftIn && rightOut) {
			left.set(ControlMode.PercentOutput, ROTATESPEED);
			right.set(ControlMode.PercentOutput, ROTATESPEED);
		}

		else if (rightIn && leftOut) {
			right.set(ControlMode.PercentOutput, ROTATESPEED);
			left.set(ControlMode.PercentOutput, ROTATESPEED);
		}

		else {

			if (leftIn) {
				left.set(ControlMode.PercentOutput, INSPEED);

			}

			else if (leftOut) {
				left.set(ControlMode.PercentOutput, OUTSPEED);

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
	
	public void manualIntake(double leftStick, double rightStick, double exponent){
		
		left.set(ControlMode.PercentOutput, Math.pow(leftStick, exponent));
		right.set(ControlMode.PercentOutput, Math.pow(rightStick, exponent));
	}
	
	public void powerCube(boolean photoSensor) {
		if (photoSensor){
			SmartDashboard.putString("Box: ", "YES! XD");
					}
		else {
			SmartDashboard.putString("Box: ", "NO! O_o");
		}
	}

}
