package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arm {

	private TalonSRX talonMotor;
	//for new arm code
	private final double upDistance = 0;
	private final double downDistance = 1000;
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
		//talonMotor.setNeutralMode(NeutralMode.Brake);
		
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
	
	
	//Method to control new arm
	public void setArmNew(boolean armPosSwitch, double speed) {
		if (armPosSwitch){
			if(Math.abs(Math.abs(initialEncoderPosition - getArmEncoderPos()) - upDistance) > 50) {
				talonMotor.set(ControlMode.PercentOutput, speed);
			}
			else
				talonMotor.set(ControlMode.PercentOutput, 0.0);
		}
		
		else{
			if(Math.abs(Math.abs(initialEncoderPosition - getArmEncoderPos()) - downDistance) > 50){
				talonMotor.set(ControlMode.PercentOutput, -speed);
			}
			else
				talonMotor.set(ControlMode.PercentOutput, 0.0);
		}
		
		
		/*
		//switch direction of motor if going for pickup/vault
		if (armPosSwitch) {
			this.speed=speed;
		}
		else {
			this.speed=-speed;
		}
		
		if (Math.abs(initialEncoderPosition - getArmEncoderPos()) < armDistance) {
			talonMotor.set(ControlMode.PercentOutput, this.speed);
		}
		else {
			talonMotor.set(ControlMode.PercentOutput, 0.0);
		}
		*/
	}
	
	/*
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
	*/
	public void testEncoderValue(double speed){
		this.talonMotor.set(ControlMode.PercentOutput, speed);
		System.out.println("Arm Encoder: " + this.talonMotor.getSensorCollection().getPulseWidthPosition());
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
	
	public double getArmEncoderPos() {
		return this.talonMotor.getSensorCollection().getPulseWidthPosition();
	}

}

