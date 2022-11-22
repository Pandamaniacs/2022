// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
//import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PWM.PeriodMultiplier;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private XboxController driverController = new XboxController (0);
  private XboxController shootController = new XboxController (1);
  private DifferentialDrive myRobot;

  private static final int leftDeviceID = 1;
  private static final int leftFollowID = 2;
  private static final int leftFollow2ID = 3;
  private static final int rightDeviceID = 4;
  private static final int rightFollowID = 5;
  private static final int rightFollow2ID = 6;
  private static final int intake = 9;

  private CANSparkMax leftMotor;
  private CANSparkMax rightMotor;  
  private CANSparkMax leftFollow;
  private CANSparkMax rightFollow;
  private CANSparkMax leftFollow2;
  private CANSparkMax rightFollow2;
  private CANSparkMax intakes;
  private final Timer timer = new Timer();

   /* This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
   @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    CameraServer.startAutomaticCapture();
    leftMotor = new CANSparkMax(leftDeviceID, MotorType.kBrushless);
    leftFollow = new CANSparkMax(leftFollowID, MotorType.kBrushless);
    rightMotor = new CANSparkMax(rightDeviceID, MotorType.kBrushless);
    rightFollow = new CANSparkMax(rightFollowID, MotorType.kBrushless);
    leftFollow2 = new CANSparkMax(leftFollow2ID, MotorType.kBrushless);
    rightFollow2 = new CANSparkMax(rightFollow2ID, MotorType.kBrushless);
    intakes = new CANSparkMax(intake, MotorType.kBrushless);
    leftMotor.restoreFactoryDefaults();
    leftFollow.restoreFactoryDefaults();
    leftFollow2.restoreFactoryDefaults();
    rightMotor.restoreFactoryDefaults();
    rightFollow.restoreFactoryDefaults();
    rightFollow2.restoreFactoryDefaults();
    intakes.restoreFactoryDefaults();
    int dtCurrentLimit = 60;
    int intakeCurrentLimit = 30;

    leftMotor.setSmartCurrentLimit(dtCurrentLimit);
    leftFollow.setSmartCurrentLimit(dtCurrentLimit);
    leftFollow2.setSmartCurrentLimit(dtCurrentLimit);
    rightMotor.setSmartCurrentLimit(dtCurrentLimit);
    rightFollow.setSmartCurrentLimit(dtCurrentLimit);
    rightFollow2.setSmartCurrentLimit(dtCurrentLimit);
    intakes.setSmartCurrentLimit(intakeCurrentLimit);
    leftFollow.follow(leftMotor);
    leftFollow2.follow(leftMotor);
    rightFollow.follow(rightMotor);
    rightFollow2.follow(rightFollow);
/*
    leftMotor.burnFlash();
    leftFollow.burnFlash();
    //leftFollow2.burnFlash();
    rightMotor.burnFlash();
    rightFollow.burnFlash();
    //rightFollow2.burnFlash();
    //intakes.burnFlash(); */
    myRobot = new DifferentialDrive(rightMotor, leftMotor);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
/*  @Override
  public void robotPeriodic() {
 }
  */
  @Override
  public void autonomousInit() {
    timer.reset();
    timer.start();
    }
  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
     //if (normal.get()) {
        if (timer.get() < 2.5) {
       var speeds = new ChassisSpeeds(0.25, 0.0, 0.0);
      //bottom part is taking track-length (the distnce between wheels)
      DifferentialDriveKinematics kinematics = 
       new DifferentialDriveKinematics(31.0);
       DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(speeds);
        double leftVelocity = wheelSpeeds.leftMetersPerSecond;
        double rightVelocity = wheelSpeeds.rightMetersPerSecond;
     
      System.out.println("Left = " + leftVelocity + ", Right = " + rightVelocity);
      leftMotor.set(leftVelocity);
      rightMotor.set(leftVelocity * -1);
      intakes.set(1.0);
        }
    else {
        myRobot.stopMotor();
      } 
  }   
  /** This function is called once when teleop is enabled. */
 /*@Override
  public void teleopInit() {
  }
  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double leftYstick = (driverController.getLeftY());
    double rightXstick = (driverController.getRightX());

    if (driverController.getRightBumper()) {
      intakes.set(1.0);  
    } else if (driverController.getLeftBumper()) {
      intakes.set(-1.0);
    } else {
      intakes.set (0.0);
    }
    myRobot.arcadeDrive(rightXstick, leftYstick);

}
  /** This function is called once wh
   * en the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}