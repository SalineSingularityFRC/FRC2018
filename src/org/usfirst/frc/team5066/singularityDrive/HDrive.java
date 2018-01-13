package org.usfirst.frc.team5066.singularityDrive;

import org.usfirst.frc.team5066.library.SpeedMode;

//Simport com.kauailabs.navx.frc.AHRS;

public class HDrive extends SingDrive {

	// mode 0 for not tank; 1 for tank

	public HDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor, int midRightMotor,
			int midLeftMotor, int mode) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, midRightMotor, midLeftMotor);
		this.mode=mode;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drive(double a, double b, double c, boolean squaredInputs, SpeedMode speedMode) {
		if (mode == 0) {
			notTank(a, b, c, squaredInputs, speedMode); // (vertical,horizontal,rotation)
														// mode 0
			return;
		}

		tank(a, b, c, squaredInputs, speedMode); // (left,right,horizontal) mode
													// 1
	}

	public void notTank(double vertical, double horizontal, double rotation, boolean squaredInputs,
			SpeedMode speedMode) {

		setVelocityMultiplierBasedOnSpeedMode(speedMode);

		// Do squared inputs if necessary
		if (squaredInputs) {
			vertical *= Math.abs(vertical);
			rotation *= Math.abs(rotation);
			horizontal *= Math.abs(horizontal);
		}

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

		m_frontLeftMotor.set(this.velocityMultiplier * ((-vertical + rotation) / mainWheelMaximum));
		m_rearLeftMotor.set(this.velocityMultiplier * ((-vertical + rotation) / mainWheelMaximum));
		m_frontRightMotor.set(this.velocityMultiplier * ((vertical + rotation) / mainWheelMaximum));
		m_rearRightMotor.set(this.velocityMultiplier * ((vertical + rotation) / mainWheelMaximum));
		m_rightMiddleMotor.set(this.velocityMultiplier * (-horizontal / hWheelMaximum));
		m_leftMiddleMotor.set(this.velocityMultiplier * (-horizontal / hWheelMaximum));

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

	public void tank(double left, double right, double horizontal, boolean squaredInputs, SpeedMode speedMode) {
		setVelocityMultiplierBasedOnSpeedMode(speedMode);

		// Do squared inputs if necessary
		if (squaredInputs) {
			left *= Math.abs(left);
			right *= Math.abs(right);
			horizontal *= Math.abs(horizontal);
		}

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

		m_frontLeftMotor.set(this.velocityMultiplier * (-left / leftWheelMaximum));
		m_rearLeftMotor.set(this.velocityMultiplier * (-left / leftWheelMaximum));
		m_frontRightMotor.set(this.velocityMultiplier * (right / rightWheelMaximum));
		m_rearRightMotor.set(this.velocityMultiplier * (right / rightWheelMaximum));
		m_rightMiddleMotor.set(this.velocityMultiplier * (-horizontal / hWheelMaximum));
		m_leftMiddleMotor.set(this.velocityMultiplier * (-horizontal / hWheelMaximum));

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

			m_frontLeftMotor.set((-vertical + rotation) / (vertMax * leftOverRight()));
			m_rearLeftMotor.set((-vertical + rotation) / (vertMax * leftOverRight()));
			m_frontRightMotor.set((vertical * leftOverRight() + rotation) / vertMax);
			m_rearRightMotor.set((vertical * leftOverRight() + rotation) / vertMax);
			m_rightMiddleMotor.set(-horizontal / strafeMax);
			m_leftMiddleMotor.set(-horizontal / strafeMax);

			if (Math.abs((this.getRightPosition() + this.getLeftPosition())) / 2 > distance)
				return;
		}
	}

	public void hDriveStrafeEncoder(double horizontal, double distance) {

		this.resetAll();

		while (true) {

			double strafeMax = Math.max(1, Math.abs(horizontal));

			m_frontLeftMotor.set(0.0);
			m_rearLeftMotor.set(0.0);
			m_frontRightMotor.set(0.0);
			m_rearRightMotor.set(0.0);
			m_rightMiddleMotor.set(-horizontal / strafeMax);
			m_leftMiddleMotor.set(-horizontal / strafeMax);

			if (Math.abs(this.getMiddlePosition()) > distance)
				return;
		}
	}

	public void hDriveStraightConstant(double vertical, double horizontal, double rotation) {

		double vertMax = Math.max(1, Math.abs(vertical));
		double strafeMax = Math.max(1, Math.abs(horizontal));

		m_frontLeftMotor.set((-vertical * driveStraight + rotation) / vertMax);
		m_rearLeftMotor.set((-vertical * driveStraight + rotation) / vertMax);
		m_frontRightMotor.set((vertical + rotation) / (vertMax * driveStraight));
		m_rearRightMotor.set((vertical + rotation) / (vertMax * driveStraight));
		m_rightMiddleMotor.set(-horizontal / strafeMax);
		m_leftMiddleMotor.set(-horizontal / strafeMax);
	}
}
