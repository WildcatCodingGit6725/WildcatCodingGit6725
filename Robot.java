/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6725.robot;

/* These are libraries that are imported into this code and the methods within the libraries are then used later on*/
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import javafx.scene.control.Toggle;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends IterativeRobot {
	private DifferentialDrive m_myRobot;
	private DifferentialDrive intake;
	private DifferentialDrive elevator;
	private Spark LeftElevator;
	private Spark RightElevator;
	private Spark frontRight;
	private Spark frontLeft;
	private Spark rearRight;
	private Spark rearLeft;
	private Spark rightIntake;
	private Spark leftIntake;
	public boolean isautondone=false;
	public static Encoder enc2 = new Encoder(1, 0, false, Encoder.EncodingType.k4X);
	public static Encoder enc1 = new Encoder(3, 2, false, Encoder.EncodingType.k4X);
	public static long t;
	public static Joystick stick = new Joystick(0);
	public static Button buttonA = new JoystickButton(stick, 1);
	public static Button buttonRB = new JoystickButton(stick, 6);
	public static Button buttonLB = new JoystickButton(stick, 5);
	
	double setTime = 4.5;
	int counter = 1;
	double startTime;
	double curTime;
	double duration;
	boolean firstCycle = true;
	
	String plateAssignment;
	char switchPlate = 'L';

	@Override
	public void robotInit() {
 
		frontRight = new Spark(0);//declaring which wheels correspond with what spark
		rearRight = new Spark(1);//declaring which wheels correspond with what spark
		SpeedControllerGroup m_Right = new SpeedControllerGroup(frontRight, rearRight);

		frontLeft = new Spark(2);//declaring which wheels correspond with what spark
		rearLeft = new Spark(3);// team only has 4 sparks, sparks 4,5,6... were not on the robot for the year 2022, but can be added in the future
		SpeedControllerGroup m_Left = new SpeedControllerGroup(frontLeft, rearLeft);
		m_myRobot = new DifferentialDrive(m_Left, m_Right);
		rightIntake = new Spark(4);
		leftIntake = new Spark(5);
		intake = new DifferentialDrive(leftIntake, rightIntake);
		RightElevator = new Spark(6);
		LeftElevator = new Spark(7);
		elevator = new DifferentialDrive(LeftElevator, RightElevator);
		
		intake.setSafetyEnabled(false);
		
	}
	
	@Override
	public void teleopPeriodic() {
		if (Robot.stick.getRawAxis(3) > 0.5) {
			leftIntake.set(-.6);
			rightIntake.set(.6);
		}
		else if (Robot.stick.getRawAxis(2) > 0.5) {
			leftIntake.set(.7);
			rightIntake.set(-.7);
			
		}
		else {
			intake.arcadeDrive(0, 0);
		}
		
		elevator.arcadeDrive(-Robot.stick.getRawAxis(5) * 1.1, 0);
		
		m_myRobot.arcadeDrive(-stick.getY(), stick.getX());
			}

	@Override
	public void autonomousInit() {

		plateAssignment = DriverStation.getInstance().getGameSpecificMessage();
		if (plateAssignment.length() > 0) {
			switchPlate = plateAssignment.charAt(0);
		}
		
		
		firstCycle = true;
		isautondone = false;
	}
	

	@Override
	public void autonomousPeriodic() {
		
	
		if (firstCycle == true) {
			 startTime = System.currentTimeMillis();
			firstCycle = false;
		}
		//if (isautondone==false) {
		curTime = System.currentTimeMillis();
		duration=(curTime-startTime)/1000;
			
		if (switchPlate == 'L') {
			
			switch (counter) {
			case 1:
				frontLeft.set(0.5);
				rearLeft.set(0.5);
				frontRight.set(-0.5);
				rearRight.set(-0.5);
				leftIntake.set(-0.2);
				rightIntake.set(0.2);
				
				if (duration >= 1) {
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
					leftIntake.set(0);
					rightIntake.set(0);
					
					counter = 2;
				}
				break;
				
			case 2:
				frontLeft.set(-0.5);
				rearLeft.set(-0.5);
				frontRight.set(-0.5);
				rearRight.set(-0.5);
				leftIntake.set(-0.2);
				rightIntake.set(0.2);
				
				if (duration >= 2) {
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
					leftIntake.set(0);
					rightIntake.set(0);
					counter = 3;
				}
				break;
				
			case 3:
				frontLeft.set(0.55);
				rearLeft.set(0.55);
				frontRight.set(-0.55);
				rearRight.set(-0.55);
				
				if (duration >= 3.5) {
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
					
					counter = 4;
				}
				break;
				
			case 4:
				frontLeft.set(0.55);
				rearLeft.set(0.55);
				frontRight.set(0.5);
				rearRight.set(0.5);
				
				if (duration >= 4.25) {
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
					
					counter = 5;
				}
				break;
			
			case 5:
				frontLeft.set(0.5);
				rearLeft.set(0.5);
				frontRight.set(-0.5);
				rearRight.set(-0.5);
				
				if (duration >= 4.8) {
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
					
					counter = 6;
				}
				break;	
				
			case 6:
				if (duration <= 6.5) {
					LeftElevator.set(0.85);
					RightElevator.set(-0.85);
				}
				
				else if (duration > 7 && duration <= 10) {
					LeftElevator.set(0.25);
					RightElevator.set(-0.25);
					leftIntake.set(0.75);
					rightIntake.set(-0.75);
				
					frontLeft.set(0.2);
					rearLeft.set(0.2);
					frontRight.set(-0.2);
					rearRight.set(-0.2);
				}
				
				else if (duration > 10) {
					leftIntake.set(0);
					rightIntake.set(0);
					
					LeftElevator.set(0.05);
					RightElevator.set(-0.05);
				
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
				}
				break;
			}
		}
		else if (switchPlate == 'R') {
			switch (counter) {
			case 1:
				frontLeft.set(0.55);
				rearLeft.set(0.55);
				frontRight.set(-0.5);
				rearRight.set(-0.5);
				leftIntake.set(-0.2);
				rightIntake.set(0.2);
				
				if (duration >= 1) {
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
					leftIntake.set(0);
					rightIntake.set(0);
					
					leftIntake.set(0);
					rightIntake.set(0);
					counter = 2;
				}
				break;
				
			case 2:
				frontLeft.set(0.55);
				rearLeft.set(0.55);
				frontRight.set(0.5);
				rearRight.set(0.5);
				leftIntake.set(-0.2);
				rightIntake.set(0.2);
				
				if (duration >= 2) {
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
					
					leftIntake.set(0);
					rightIntake.set(0);
					counter = 3;
				}
				break;
				
			case 3:
				frontLeft.set(0.525);
				rearLeft.set(0.525);
				frontRight.set(-0.525);
				rearRight.set(-0.525);
				
				if (duration >= 3.5) {
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
					
					counter = 4;
				}
				break;
				
			case 4:
				frontLeft.set(-0.5);
				rearLeft.set(-0.5);
				frontRight.set(-0.5);
				rearRight.set(-0.5);
				
				if (duration >= 4.25) {
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
					
					counter = 5;
				}
				break;
			
			case 5:
				frontLeft.set(0);
				rearLeft.set(0);
				frontRight.set(-0);
				rearRight.set(-0);
				
				if (duration >= 4.8) {
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
					
					counter = 6;
				}
				break;	
				
			case 6:
				if (duration <= 6.5) {
		LeftElevator.set(0.6);
					RightElevator.set(-0.6);
				}
				
				else if (duration > 7 && duration <= 10) {
					LeftElevator.set(0.3);
					RightElevator.set(-0.3);
					leftIntake.set(0.75);
					rightIntake.set(-0.75);
				
					frontLeft.set(0.25);
					rearLeft.set(0.25);
					frontRight.set(-0.25);
					rearRight.set(-0.25);
				}
				
				else if (duration > 10) {
					leftIntake.set(0);
					rightIntake.set(0);
					
					LeftElevator.set(0.05);
					RightElevator.set(-0.05);
				
					frontLeft.set(0);
					rearLeft.set(0);
					frontRight.set(0);
					rearRight.set(0);
				}
				break;
			}
			
		}
		else if (switchPlate == 'C') {
			System.out.println("Time : " + duration + " Till : " + setTime);
			
			if ((duration<= setTime)) {
				frontLeft.set(0.5);
				rearLeft.set(0.5);
				frontRight.set(-0.5);
				rearRight.set(-0.5);
				
			}
			else {
				frontLeft.set(0);
				frontRight.set(0);
				rearRight.set(0);
				rearLeft.set(0);
				isautondone=true;
			}
		}
		
	
	}
}
 