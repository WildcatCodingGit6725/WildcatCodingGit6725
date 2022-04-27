/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.CameraServer;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends IterativeRobot {
  private DifferentialDrive m_myRobot;

  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private Spark frontRight;
  private Spark frontLeft;
  private Spark rearRight;
  private Spark rearLeft;
  private Spark intakeAndRail;
  public static Joystick stick = new Joystick(0);
  public static Joystick stickJ = new Joystick(1);
private Spark hatch;
double Const = 0.85;
UsbCamera camera;
CvSink cvSink;
CvSource outputStream;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
   camera = CameraServer.getInstance().startAutomaticCapture();
   camera = CameraServer.getInstance().startAutomaticCapture();

    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    frontRight = new Spark(4);
    rearRight = new Spark(3);
    SpeedControllerGroup m_Right = new SpeedControllerGroup(frontRight, rearRight);

    frontLeft = new Spark(2);
    rearLeft = new Spark(1);
    SpeedControllerGroup m_Left = new SpeedControllerGroup(frontLeft, rearLeft);

    m_myRobot = new DifferentialDrive(m_Left,m_Right);

intakeAndRail = new Spark(6);
hatch = new Spark(5);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

    teleopInit();
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    teleopPeriodic();
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
//bumbers
    if(stick.getRawButton(6)){
      intakeAndRail.set(0.5);
     }
     else if(stick.getRawButton(5)){
      intakeAndRail.set(-0.5);
     }
  else{
    intakeAndRail.set(0);
  } 
    
  
    
    if (stick.getRawAxis(2)!=0){
     
      hatch.set(-0.1);
    }
    else if(stick.getRawAxis(3)!=0){
      hatch.set(0.1);
    }
    else{
      hatch.set(0);
    }
  
    if(stick.getRawAxis(1)!=0 ||  stick.getRawAxis(0)!=0){
      if (stick.getRawButton(4)==false) {
      m_myRobot.arcadeDrive(-stick.getRawAxis(1)*Const,stick.getRawAxis(0)*Const);
      }
      else{
        m_myRobot.arcadeDrive(-stick.getRawAxis(1),stick.getRawAxis(0));
     
    }
    }
    else{
      m_myRobot.arcadeDrive(0,0);
    }



 

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    

  }
}
