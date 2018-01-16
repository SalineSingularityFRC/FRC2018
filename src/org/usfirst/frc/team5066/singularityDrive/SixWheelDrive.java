package org.usfirst.frc.team5066.singularityDrive;

import org.usfirst.frc.team5066.library.SpeedMode;

import com.ctre.CANTalon;
//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SixWheelDrive extends SingDrive{
	
	public SixWheelDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor,
			int midRightMotor, int midLeftMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, midRightMotor, midLeftMotor);
		// TODO Auto-generated constructor stub
	}

	public void drive(double vertical, double horizontal, double rotation, boolean squaredInputs, SpeedMode speedMode) {
		double translationVelocity = vertical, rotationVelocity = rotation;
		
		translationVelocity = threshold(translationVelocity);
		rotationVelocity = threshold(rotationVelocity);
		
		setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		if (squaredInputs) {
			translationVelocity *= Math.abs(vertical);
			rotationVelocity *= Math.abs(rotation);
		}
		
		// Do reverse drive when necessary. There are methods above for different inputs.
		/*if (reverse) {
			translationVelocity = -translationVelocity;
			rotationVelocity = -rotationVelocity;
		}
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
		m_leftMiddleMotor.set(this.velocityMultiplier * ((-translationVelocity + rotationVelocity) / maximum));
		m_rightMiddleMotor.set(this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_frontRightMotor.set(this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_rearRightMotor.set(this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		SmartDashboard.putNumber("rightEncoder", ((CANTalon) m_rightMiddleMotor).getEncPosition());
		SmartDashboard.putNumber("leftEncoder", ((CANTalon) m_leftMiddleMotor).getEncPosition());
	}
	
	/**
	 * 
	 * 6 wheel tank drive
	 * @param right
	 * @param squaredInputs
	 * @param speedMode
	 */
	public void tankDrive(double left, double right, boolean squaredInputs, SpeedMode speedMode) {
		double leftVelocity = left, rightVelocity = right;
		
		leftVelocity = threshold(leftVelocity);
		rightVelocity = threshold(rightVelocity);
		
		setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		if (squaredInputs) {
			leftVelocity *= Math.abs(left);
			rightVelocity *= Math.abs(right);
		}
		
		
		// Guard against illegal values
		double leftMaximum = Math.max(1, Math.abs(leftVelocity));
		double rightMaximum = Math.max(1, Math.abs(rightVelocity));

		if (velocityReduceActivated) {
			leftMaximum *= 1 / reducedVelocity;
			rightMaximum *= 1 / reducedVelocity;
		}

		leftVelocity = threshold(leftVelocity);
		rightVelocity = threshold(rightVelocity);

		// Set the motors
		m_frontLeftMotor.set(this.velocityMultiplier * ((-leftVelocity) / leftMaximum));
		m_rearLeftMotor.set(this.velocityMultiplier * ((-leftVelocity) / leftMaximum));
		m_leftMiddleMotor.set(this.velocityMultiplier * ((-leftVelocity) / leftMaximum));
		m_rightMiddleMotor.set(this.velocityMultiplier * ((rightVelocity) / rightMaximum));
		m_frontRightMotor.set(this.velocityMultiplier * ((rightVelocity) / rightMaximum));
		m_rearRightMotor.set(this.velocityMultiplier * ((rightVelocity) / rightMaximum));
		SmartDashboard.putNumber("rightEncoder", ((CANTalon) m_rightMiddleMotor).getEncPosition());
		SmartDashboard.putNumber("leftEncoder", ((CANTalon) m_leftMiddleMotor).getEncPosition());
	}
}

