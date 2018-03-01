package org.usfirst.frc.team5066.singularityDrive;

import org.usfirst.frc.team5066.library.SpeedMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

public class MecanumDrive extends SingDrive{
	
	public double rotationMultiplier=1.0; //Good Luck
	
	public MecanumDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor, double rotationMultiplier) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		this.rotationMultiplier = rotationMultiplier;
	}

	@Override
	public void drive(double vertical, double horizontal, double rotation, double inputExponent, SpeedMode speedMode) {
		double translationVelocity, direction, maximum, rotationVelocity;
		
		// Use the Pythagorean theorem to find the speed of translation
		translationVelocity = this.velocityMultiplier * Math.sqrt(Math.pow(horizontal, 2) + Math.pow(vertical, 2));
		rotationVelocity = this.velocityMultiplier * rotation * rotationMultiplier;
		
		// Do squared inputs if necessary
		translationVelocity *= Math.abs(Math.pow(translationVelocity, inputExponent - 1));
		rotationVelocity *= Math.abs(Math.pow(rotationVelocity, inputExponent - 1));


		// Use trigonometry to find the direction of travel
		direction = Math.PI / 4 + Math.atan2(vertical, horizontal);

		// Guard against illegal inputs
		maximum = Math.max(Math.max(Math.abs(Math.sin(direction)), Math.abs(Math.cos(direction))) * translationVelocity
				+ Math.abs(rotationVelocity), 1);

		if (velocityReduceActivated) {
			maximum *= 1 / reducedVelocity;
		}

		translationVelocity = threshold(translationVelocity);
		rotationVelocity = threshold(rotationVelocity);

		// Set the motors' speeds
		m_frontLeftMotor.set(ControlMode.PercentOutput, (translationVelocity * Math.sin(direction) + rotationVelocity) / maximum);
		m_rearLeftMotor.set(ControlMode.PercentOutput, (translationVelocity * -Math.cos(direction) + rotationVelocity) / maximum);
		m_frontRightMotor.set(ControlMode.PercentOutput, (translationVelocity * Math.cos(direction) + rotationVelocity) / maximum);
		m_rearRightMotor.set(ControlMode.PercentOutput, (translationVelocity * -Math.sin(direction) + rotationVelocity) / maximum);
		
	}
	
}
