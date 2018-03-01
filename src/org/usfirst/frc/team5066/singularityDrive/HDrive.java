package org.usfirst.frc.team5066.singularityDrive;

import org.usfirst.frc.team5066.library.SpeedMode;

import com.ctre.phoenix.motorcontrol.ControlMode;

//Simport com.kauailabs.navx.frc.AHRS;

public class HDrive extends SingDrive {

	// mode 0 for not tank; 1 for tank

	public HDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor, int midRightMotor,
			int midLeftMotor, int mode) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, midRightMotor, midLeftMotor, 0, 0);
		this.mode=mode;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drive(double a, double b, double c, double inputExponent, SpeedMode speedMode) {
		if (mode == 0) {
			notTank(a, b, c, inputExponent, speedMode); // (vertical,horizontal,rotation)
														// mode 0
			return;
		}

		tank(a, b, c, inputExponent, speedMode); // (left,right,horizontal) mode
													// 1
	}

	public void notTank(double vertical, double horizontal, double rotation, double inputExponent,
			SpeedMode speedMode) {

		setVelocityMultiplierBasedOnSpeedMode(speedMode);

		// Do squared inputs if necessary
		vertical *= Math.abs(Math.pow(vertical, inputExponent - 1));
		rotation *= Math.abs(Math.pow(rotation, inputExponent - 1));

		// Guard against illegal values
		double mainWheelMaximum = Math.max(1, Math.abs(vertical) + Math.abs(rotation));
		double hWheelMaximum = Math.max(1, Math.abs(horizontal));

		// if (buttonPressed) {
		// mainWheelMaximum *= 1 / reducedVelocity;
		// hWheelMaximum *= 1 / reducedVelocity;
		// }

		vertical = threshold(vertical);
		horizontal = threshold(horizontal);
		rotation = threshold(rotation);
		/*
		 * if (timer.get() - )
		 * 
		 * 
		 * 
		 * 
		 * prevStrafe = horizontal;
		 */

		m_frontLeftMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((-vertical + rotation) / mainWheelMaximum));
		m_rearLeftMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((-vertical + rotation) / mainWheelMaximum));
		m_frontRightMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((vertical + rotation) / mainWheelMaximum));
		m_rearRightMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * ((vertical + rotation) / mainWheelMaximum));
		m_rightMiddleMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * (-horizontal / hWheelMaximum));
		m_leftMiddleMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * (-horizontal / hWheelMaximum));

		// for testing purposes only
		/*
		 * SmartDashboard.putString("DB/String 0", "Front Left Motor " +
		 * m_frontLeftMotor.get()); SmartDashboard.putString("DB/String 1",
		 * "Rear Left Motor " + m_rearLeftMotor.get());
		 * SmartDashboard.putString("DB/String 2", "Front Right Motor " +
		 * m_frontRightMotor.get()); SmartDashboard.putString("DB/String 3",
		 * "Rear Right Motor " + m_rearRightMotor.get());
		 * SmartDashboard.putString("DB/String 4", "Left Middle Motor " +
		 * m_leftMiddleMotor.get()); SmartDashboard.putString("DB/String 5",
		 * "Right Middle Motor " + m_rightMiddleMotor.get());
		 */

	}

	public void tank(double left, double right, double horizontal, double inputExponent, SpeedMode speedMode) {
		setVelocityMultiplierBasedOnSpeedMode(speedMode);

		// Do squared inputs if necessary
		left *= Math.abs(Math.pow(left, inputExponent - 1));
		right *= Math.abs(Math.pow(right, inputExponent - 1));

		// Guard against illegal values
		double rightWheelMaximum = Math.max(1, Math.abs(right));
		double leftWheelMaximum = Math.max(1, Math.abs(left));
		double hWheelMaximum = Math.max(1, Math.abs(horizontal));

		// if (buttonPressed) {
		// rightWheelMaximum *= 1 / reducedVelocity;
		// leftWheelMaximum *= 1 / reducedVelocity;
		// hWheelMaximum *= 1 / reducedVelocity;
		// }

		left = threshold(left);
		right = threshold(right);
		horizontal = threshold(horizontal);

		m_frontLeftMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * (-left / leftWheelMaximum));
		m_rearLeftMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * (-left / leftWheelMaximum));
		m_frontRightMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * (right / rightWheelMaximum));
		m_rearRightMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * (right / rightWheelMaximum));
		m_rightMiddleMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * (-horizontal / hWheelMaximum));
		m_leftMiddleMotor.set(ControlMode.PercentOutput, this.velocityMultiplier * (-horizontal / hWheelMaximum));

	}

	/*
	 * A method for autonomously moving straight forward, using encoders to keep
	 * on a straight path.
	 * 
	 * @param speed This is the speed to move at. Note: speed is not changed by
	 * squaredInputs or velocityMultiplier
	 */
	public void hDriveStraightEncoder(double vertical, double horizontal, double rotation, double distance) {

		this.resetAll();

		while (true) {

			double vertMax = Math.max(1, Math.abs(vertical) + Math.abs(rotation));
			double strafeMax = Math.max(1, Math.abs(horizontal));

			m_frontLeftMotor.set(ControlMode.PercentOutput, (-vertical + rotation) / (vertMax * leftOverRight()));
			m_rearLeftMotor.set(ControlMode.PercentOutput, (-vertical + rotation) / (vertMax * leftOverRight()));
			m_frontRightMotor.set(ControlMode.PercentOutput, (vertical * leftOverRight() + rotation) / vertMax);
			m_rearRightMotor.set(ControlMode.PercentOutput, (vertical * leftOverRight() + rotation) / vertMax);
			m_rightMiddleMotor.set(ControlMode.PercentOutput, -horizontal / strafeMax);
			m_leftMiddleMotor.set(ControlMode.PercentOutput, -horizontal / strafeMax);

			if (Math.abs((this.getRightPosition() + this.getLeftPosition())) / 2 > distance)
				return;
		}
	}

	public void hDriveStrafeEncoder(double horizontal, double distance) {

		this.resetAll();

		while (true) {

			double strafeMax = Math.max(1, Math.abs(horizontal));

			m_frontLeftMotor.set(ControlMode.PercentOutput, 0.0);
			m_rearLeftMotor.set(ControlMode.PercentOutput, 0.0);
			m_frontRightMotor.set(ControlMode.PercentOutput, 0.0);
			m_rearRightMotor.set(ControlMode.PercentOutput, 0.0);
			m_rightMiddleMotor.set(ControlMode.PercentOutput, -horizontal / strafeMax);
			m_leftMiddleMotor.set(ControlMode.PercentOutput, -horizontal / strafeMax);

			if (Math.abs(this.getMiddlePosition()) > distance)
				return;
		}
	}

	public void hDriveStraightConstant(double vertical, double horizontal, double rotation) {

		double vertMax = Math.max(1, Math.abs(vertical));
		double strafeMax = Math.max(1, Math.abs(horizontal));

		m_frontLeftMotor.set(ControlMode.PercentOutput, (-vertical * driveStraight + rotation) / vertMax);
		m_rearLeftMotor.set(ControlMode.PercentOutput, (-vertical * driveStraight + rotation) / vertMax);
		m_frontRightMotor.set(ControlMode.PercentOutput, (vertical + rotation) / (vertMax * driveStraight));
		m_rearRightMotor.set(ControlMode.PercentOutput, (vertical + rotation) / (vertMax * driveStraight));
		m_rightMiddleMotor.set(ControlMode.PercentOutput, -horizontal / strafeMax);
		m_leftMiddleMotor.set(ControlMode.PercentOutput, -horizontal / strafeMax);
	}
}
