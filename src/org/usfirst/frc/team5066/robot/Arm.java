package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arm {

	private TalonSRX talonMotor;
	
	private final double LOWERLIMIT = 10;
	private final double UPPERLIMIT = 100;
	
	public enum Position{
		PICKUP,
		SWITCH,
		PORTAL,
		HIGHSCALE,
		LEVELSCALE,
		LOWSCALE,
		EXCHANGE,
		START,
		TRAVEL
	}
	
	double speedConstant;
	double speed;
	
	private int initialEncoderPosition;
	
	
	public Arm(int tal, double s, int forwardChannel, int reverseChannel) {
		talonMotor = new TalonSRX (tal);
		
		this.setIntitialEncoderPosition();
		/*
		//figure out if this is the right feedback device
		talonMotor.configSelectedFeedbackSensor(FeedbackDevice.PulseWidthEncodedPosition, 0, 10);
		
		//Figure out what these should be
		talonMotor.setSensorPhase(true);
		talonMotor.setInverted(false);
		
		// Set relevant frame periods to be at least as fast as periodic rate 
		talonMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 10);
		talonMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);

		// set the peak and nominal outputs
		talonMotor.configNominalOutputForward(0, 10);
		talonMotor.configNominalOutputReverse(0, 10);
		talonMotor.configPeakOutputForward(1, 10);
		talonMotor.configPeakOutputReverse(-1, 10);

		// set closed loop gains in slot0 - see documentation 
		talonMotor.selectProfileSlot(0, 0);
		talonMotor.config_kF(0, 0.2, 10);
		talonMotor.config_kP(0, 0.2, 10);
		talonMotor.config_kI(0, 0, 10);
		talonMotor.config_kD(0, 0, 10);
		// set acceleration and vcruise velocity - see documentation 
		talonMotor.configMotionCruiseVelocity(50, 10);
		talonMotor.configMotionAcceleration(20, 10);
		// zero the sensor 
		talonMotor.setSelectedSensorPosition(0, 0, 10);
		
		*/
		
		speedConstant = s;
		}
	
	public void controlArm (double armStick, double exponent) {
		/*
		if (talonMotor.getSensorCollection().getQuadraturePosition() < LOWERLIMIT && armStick < 0) {
			speed = 0.0;
		}
		else if (talonMotor.getSensorCollection().getQuadraturePosition() > UPPERLIMIT && armStick > 0) {
			
			speed = 0.0;
		}
		else {
			*/
			speed = armStick;
			speed *= Math.abs(Math.pow(speed, exponent - 1));
			speed *= speedConstant;
		//}
		
		talonMotor.set(ControlMode.PercentOutput, speed);
		System.out.println("        Arm:" + (talonMotor.getSensorCollection().getPulseWidthPosition()  - initialEncoderPosition));
	}
	
	public void setArm(Position setTo) {
		if(setTo == Position.PICKUP) {
			talonMotor.set(ControlMode.Position, degreesToPosition(0) - initialEncoderPosition);
		}
		else if(setTo == Position.SWITCH || setTo == Position.PORTAL) {
			talonMotor.set(ControlMode.Position, degreesToPosition(45) - initialEncoderPosition);
		}
		else if(setTo == Position.HIGHSCALE) {
			talonMotor.set(ControlMode.Position, degreesToPosition(135) - initialEncoderPosition);
		}
		else if(setTo == Position.LEVELSCALE) {
			talonMotor.set(ControlMode.Position, degreesToPosition(135) - initialEncoderPosition);
		}
		else if(setTo == Position.LOWSCALE) {
			talonMotor.set(ControlMode.Position, degreesToPosition(135) - initialEncoderPosition);
		}
		else if(setTo == Position.EXCHANGE) {
			talonMotor.set(ControlMode.Position, degreesToPosition(2) - initialEncoderPosition);
		}
		else if (setTo == Position.START){
			talonMotor.set(ControlMode.Position, degreesToPosition(0) - initialEncoderPosition);
		}
		else {
			//comment
			talonMotor.set(ControlMode.Position, degreesToPosition(60) - initialEncoderPosition);
		}
		
		System.out.println("Arm:" + (talonMotor.getSensorCollection().getPulseWidthPosition()  - initialEncoderPosition));
		
	}
	
	public void resetEncoder() {
		this.talonMotor.getSensorCollection().setPulseWidthPosition(0, 0);
	}
	
	public void setIntitialEncoderPosition() {
		initialEncoderPosition = this.talonMotor.getSensorCollection().getPulseWidthPosition();
	}
	
	public double degreesToPosition(int degrees) {	
		return degrees * 40.45;
	}

}

