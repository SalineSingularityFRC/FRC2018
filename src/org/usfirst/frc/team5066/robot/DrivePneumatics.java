package org.usfirst.frc.team5066.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
/**
 * Class to control pneumatic gear box
 * references the DoubleSolenoid from WPI
 * 
 * @author camtr
 *
 */

public class DrivePneumatics {
	
	DoubleSolenoid doubleSolenoid;
	
	public DrivePneumatics(int forwardChannel, int reverseChannel) {
		doubleSolenoid = new DoubleSolenoid(forwardChannel, reverseChannel);
	}
	
	public void setForward() {
		doubleSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void setReverse() {
		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setOff() {
		doubleSolenoid.set(DoubleSolenoid.Value.kOff);
	}
	
}
