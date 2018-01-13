package org.usfirst.frc.team5066.singularityDrive;

import org.usfirst.frc.team5066.library.SpeedMode;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FourWheelDrive extends SingDrive {

	public FourWheelDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor,
			double driveStraight, AHRS gyro, int mode) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, driveStraight, gyro);
		this.mode = mode;
	}

	public void drive(double vertical, double horizontal, double rotation, boolean squaredInputs, SpeedMode speedMode) {
		if (mode == 1) {
			tank(vertical, horizontal, squaredInputs, speedMode);
		}

		double translationVelocity = vertical, rotationVelocity = rotation;

		setVelocityMultiplierBasedOnSpeedMode(speedMode);

		// Do squared inputs if necessary
		if (squaredInputs) {
			translationVelocity *= Math.abs(vertical);
			rotationVelocity *= Math.abs(rotation);
		}

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
		m_frontLeftMotor.set(this.velocityMultiplier * ((-translationVelocity + rotationVelocity) / maximum));
		m_rearLeftMotor.set(this.velocityMultiplier * ((-translationVelocity + rotationVelocity) / maximum));
		m_frontRightMotor.set(this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_rearRightMotor.set(this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
	}

	private void tank(double left, double right, boolean squaredInputs, SpeedMode speedMode) {
		double leftVelocity = left, rightVelocity = right;

		this.setVelocityMultiplierBasedOnSpeedMode(speedMode);

		// Do squared inputs if necessary
		if (squaredInputs) {
			leftVelocity *= Math.abs(left);
			rightVelocity *= Math.abs(right);
		}
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
		m_frontLeftMotor.set(this.velocityMultiplier * leftVelocity);
		m_rearLeftMotor.set(this.velocityMultiplier * leftVelocity);
		m_frontRightMotor.set(this.velocityMultiplier * -rightVelocity);
		m_rearRightMotor.set(this.velocityMultiplier * -rightVelocity);
	}
}
