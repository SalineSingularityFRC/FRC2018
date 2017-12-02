package org.usfirst.frc.team5066.library;

public class SingularityPropertyNotFoundException extends Throwable{
	String propertyName;
	
	public SingularityPropertyNotFoundException(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
}
