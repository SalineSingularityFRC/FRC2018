package org.usfirst.frc.team5066.singularityDrive;

import org.usfirst.frc.team5066.library.SpeedMode;

//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class FourWheelDrive extends SingDrive {

	public FourWheelDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor, int mode) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		this.mode = mode;
	}

	public void drive(double vertical, double horizontal, double rotation, double inputExponent, SpeedMode speedMode) {
		if (mode == 1) {
			tank(vertical, horizontal, inputExponent, speedMode);
		}

		double translationVelocity = vertical, rotationVelocity = rotation;

		setVelocityMultiplierBasedOnSpeedMode(speedMode);

		// Do squared inputs if necessary
		translationVelocity *= Math.abs(Math.pow(translationVelocity, inputExponent - 1));
		rotationVelocity *= Math.abs(Math.pow(rotationVelocity, inputExponent - 1));

		// Do reverse drive when necessary. There are methods above for
		// different inputs.
		/*
		 * if (reverse) { translationVelocity = -translationVelocity;
		 * rotationVelocity = -rotationVelocity; }
		 */
		// Guard against illegal values
		double maximum = Math.max(1, Math.abs(translationVelocity) + Math.abs(rotationVelocity));

		if (velocityReduceActivated) {
			maximum *= 1 / reducedVelocity;
		}

		translationVelocity = threshold(translationVelocity);
		rotationVelocity = threshold(rotationVelocity);

		// Set the motors
		m_frontLeftMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((-translationVelocity + rotationVelocity) / maximum));
		m_rearLeftMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((-translationVelocity + rotationVelocity) / maximum));
		m_frontRightMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_rearRightMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
	}

	private void tank(double left, double right, double inputExponent, SpeedMode speedMode) {
		double leftVelocity = left, rightVelocity = right;

		this.setVelocityMultiplierBasedOnSpeedMode(speedMode);

		// Do squared inputs if necessary
		leftVelocity *= Math.abs(Math.pow(leftVelocity, inputExponent - 1));
		rightVelocity *= Math.abs(Math.pow(rightVelocity, inputExponent - 1));
		
		SmartDashboard.putNumber("Post-sqaring inputs - Left Velocity", leftVelocity);
		SmartDashboard.putNumber("Post-sqaring inputs - Right Velocity", rightVelocity);

		// Guard against illegal inputs
		leftVelocity /= Math.max(1, Math.abs(leftVelocity));
		rightVelocity /= Math.max(1, Math.abs(rightVelocity));

		SmartDashboard.putNumber("Clamped Value - Left Velocity", leftVelocity);
		SmartDashboard.putNumber("Clamped Value - Right Velocity", rightVelocity);
		if (velocityReduceActivated) {
			leftVelocity *= reducedVelocity;
			rightVelocity *= reducedVelocity;
		}

		SmartDashboard.putNumber("Reduced Velocity - Left", leftVelocity);
		SmartDashboard.putNumber("Reduced Velocity - Right", rightVelocity);

		SmartDashboard.putNumber("Reduced Velocity - Left", leftVelocity);
		SmartDashboard.putNumber("Reduced Velocity - Right", rightVelocity);

		// reverse drive for tank. :D
		/*
		 * if (reverse == 180) isreverse = true; else if (reverse == 0)
		 * isreverse = false; if (isreverse) { leftVelocity = -leftVelocity;
		 * rightVelocity = -rightVelocity; }
		 */
		leftVelocity = threshold(leftVelocity);
		rightVelocity = threshold(rightVelocity);

		// Set the motors' speeds
		m_frontLeftMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * leftVelocity);
		m_rearLeftMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * leftVelocity);
		m_frontRightMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * -rightVelocity);
		m_rearRightMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * -rightVelocity);
	}
}
