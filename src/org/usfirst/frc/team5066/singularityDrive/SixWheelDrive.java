package org.usfirst.frc.team5066.singularityDrive;

import org.usfirst.frc.team5066.library.SpeedMode;

import com.ctre.CANTalon;
//import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SixWheelDrive extends SingDrive{
	
	double leftVelocity;
	double rightVelocity;
	
	public SixWheelDrive(int leftVictor1, int leftVictor2, int leftVictor3, int leftTalon,
			int rightVictor1, int rightVictor2, int rightVictor3, int rightTalon) {
		super(leftVictor1, leftVictor2, leftVictor3, leftTalon,
				rightVictor1, rightVictor2, rightVictor3, rightTalon);
		
	}
	
	//TODO gradual acceleration

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
		m_leftTalon.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_rightTalon.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		
		m_leftVictor1.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_leftVictor2.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_leftVictor3.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		
		m_rightVictor1.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_rightVictor2.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_rightVictor3.set(ControlMode.PercentOutput, this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		
		//SmartDashboard.putNumber("rightEncoder", m_rightMiddleMotor.getSensorCollection().getQuadraturePosition());
		//SmartDashboard.putNumber("leftEncoder", m_leftMiddleMotor.getSensorCollection().getQuadraturePosition());
	}
	
	/**
	 * 
	 * 6 wheel tank drive
	 * @param right
	 * @param squaredInputs
	 * @param speedMode
	 */
	public void tankDrive(double left, double right, double inputExponent, SpeedMode speedMode) {
		leftVelocity = left;
		rightVelocity = right;
		
		leftVelocity = threshold(leftVelocity);
		rightVelocity = threshold(rightVelocity);
		
		//setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		leftVelocity *= Math.abs(Math.pow(leftVelocity, inputExponent - 1));
		rightVelocity *= Math.abs(Math.pow(rightVelocity, inputExponent - 1));
		
		
		// Guard against illegal values
		double leftMaximum = Math.max(1, Math.abs(leftVelocity));
		double rightMaximum = Math.max(1, Math.abs(rightVelocity));
		
		/*
		if (velocityReduceActivated) {
			leftMaximum *= 1 / reducedVelocity;
			rightMaximum *= 1 / reducedVelocity;
		}
		 */
		
		// Set the motors
		//In the past, We've had this.velocityMultiplier instead of this.getSendableSpeed()
		
		m_leftTalon.set(ControlMode.PercentOutput, this.getSendableSpeed() * ((leftVelocity) / leftMaximum));
	    m_leftVictor1.set(ControlMode.PercentOutput, this.getSendableSpeed() * ((leftVelocity) / leftMaximum));
	    m_leftVictor2.set(ControlMode.PercentOutput, this.getSendableSpeed() * ((leftVelocity) / leftMaximum));
	    m_leftVictor3.set(ControlMode.PercentOutput, this.getSendableSpeed() * ((leftVelocity) / leftMaximum));
	    
	    m_rightTalon.set(ControlMode.PercentOutput, this.getSendableSpeed() * ((rightVelocity) / rightMaximum));
		m_rightVictor1.set(ControlMode.PercentOutput, this.getSendableSpeed() * -((rightVelocity) / rightMaximum));
		m_rightVictor2.set(ControlMode.PercentOutput, this.getSendableSpeed() * -((rightVelocity) / rightMaximum));
		m_rightVictor3.set(ControlMode.PercentOutput, this.getSendableSpeed() * -((rightVelocity) / rightMaximum));
		
		//SmartDashboard.putNumber("left speed", leftVelocity);
		//SmartDashboard.putNumber("right speed", rightVelocity);
	
	}
}

