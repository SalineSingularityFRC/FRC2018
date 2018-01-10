public abstract class AutonControlScheme {
	
	public static final double DistancePerRevolution = 12.56;//circumference of wheels
	
	private static SingularityDrive drive;
	private static LowGoalShooter shooter;
	private static SingularityIntake intake;
	public AutonControlScheme(SingularityDrive drive, LowGoalShooter shooter, SingularityIntake intake) {
		this.drive = drive;
		this.shooter = shooter;
		this.intake = intake;
	}
	
	public abstract void moveAuton();
	
	/**
	 * 
	 * @param verticalSpeed
	 * Sets speed of the motors.
	 * @param distance
	 * Sets distance of movement in inches.
	 * @param reverse
	 * true means travel backwards, false forwards
	 * @param acceleration
	 * The number of stages to accelerate
	 */
	public static void vertical(double verticalSpeed, double distance, boolean reverse, int acceleration) {
		
		double vertSpeed = verticalSpeed;

		if (reverse) {
			vertSpeed *= -1;
		}
		int distanceAccelerated = 0;
		
		//Slowly start motors for i(acceleration) inches
		for(int i = acceleration; i > 0; i--) {
			if (2 * (acceleration - i + 1) > distance - 2) break;
			do {
				drive.hDriveTank(vertSpeed/i, vertSpeed/i, 0.0, false, SpeedMode.NORMAL);
			} 
			while ((drive.getLeftPosition() + drive.getRightPosition()) / 2 > -2.0 / DistancePerRevolution
				&& (drive.getLeftPosition() + drive.getRightPosition()) / 2 < 2.0 / DistancePerRevolution);
			
			distanceAccelerated += 2;
			
			drive.resetAll();
		}
			
		//normal speed
		do {
			drive.hDriveTank(verticalSpeed, verticalSpeed, 0.0, false, SpeedMode.NORMAL);
		} 
		while ((drive.getLeftPosition() + drive.getRightPosition()) / 2 > -(distance - distanceAccelerated - 2) / DistancePerRevolution
			&& (drive.getLeftPosition() + drive.getRightPosition()) / 2 < (distance - distanceAccelerated - 2) / DistancePerRevolution);
		drive.resetAll();
		
		//slow down
		do {
			drive.hDriveTank(vertSpeed / 2, vertSpeed / 2, 0.0, false, SpeedMode.NORMAL);
		}
		while ((drive.getLeftPosition() + drive.getRightPosition()) / 2 > -2 / DistancePerRevolution
			&& (drive.getLeftPosition() + drive.getRightPosition()) / 2 < 2 / DistancePerRevolution);
		drive.resetAll();
		
		drive.hDriveTank(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
	}
	
	/**
	 * 
	 * @param horizontalSpeed
	 * Sets speed of strafe motors.
	 * @param distance
	 * Sets distance of movements in inches.
	 * @param left
	 * true means travel left, false right
	 * @param acceleration
	 * The number of stages of acceleration
	 */
	public static void horizontal (double horizontalSpeed, double distance, boolean left, int acceleration) {
		
		double speed = horizontalSpeed;
		if (left) {
			speed *= -1;
		}
		int distanceAccelerated = 0;
		
		//Slowly start motors for 2 * (acceleration) inches total
		for(int i = acceleration; i > 0; i--) {
			if (2 * (acceleration - i + 1) > distance - 2) break;
			do {
				drive.hDriveTank(0.0, 0.0, speed / i, false, SpeedMode.NORMAL);
			} 
			while (drive.getMiddlePosition() > -2.0 / DistancePerRevolution
				&& drive.getMiddlePosition() < 2.0 / DistancePerRevolution);
			
			distanceAccelerated += 2;
			
			drive.resetAll();
		}
			
		//normal speed
		do {
			drive.hDriveTank(0.0, 0.0, speed, false, SpeedMode.NORMAL);
		} 
		while (drive.getMiddlePosition() > -(distance - distanceAccelerated - 2) / DistancePerRevolution
			&& drive.getMiddlePosition() < (distance - distanceAccelerated - 2) / DistancePerRevolution);
		drive.resetAll();
		
		//slow down
		do {
			drive.hDriveTank(0.0, 0.0, speed / 2, false, SpeedMode.NORMAL);
		}
		while (drive.getMiddlePosition() > -2 / DistancePerRevolution
			&& drive.getMiddlePosition()  < 2 / DistancePerRevolution);
		drive.resetAll();
		
		drive.hDriveTank(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
	}
	
	/**
	 * 
	 * @param rotationSpeed
	 * Sets speed of rotating motors
	 * @param degrees
	 * Set degrees of the rotation (negative is counterclockwise)
	 * @param counterClockwise
	 * true means we are rotating left, false right
	 */
	//TODO Fix the Rotation. Do the hard math to get this method to work.
	/*
	public static void rotation(double rotationSpeed, double degrees, boolean counterClockwise) {
		if (counterClockwise) {
			do {
				drive.hDrive(0.0, 0.0, -rotationSpeed, false, SpeedMode.NORMAL);
			} while(drive.getLeftPosition() > -degrees / 20);
		}
		else{
			do {
				drive.hDrive(0.0, 0.0, rotationSpeed, false, SpeedMode.NORMAL);
			} while(drive.getMiddlePosition() < degrees / 20);
		}
		drive.hDrive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
		drive.resetAll();
	}
	*/