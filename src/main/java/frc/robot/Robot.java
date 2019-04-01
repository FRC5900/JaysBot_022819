/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  TalonSRX RightMotor = new TalonSRX(0);
  VictorSPX LeftMotor = new VictorSPX(1);
  Timer tclock = new Timer();

  private static final int kJoystickPort = 0;
  private Joystick m_joystick;
  final double ROBOT_MAX_SPEED = 1.0;
  final double ROBOT_NORMAL_SPEED = 0.7;
  final double ROBOT_MAX_TURNSPEED = 0.45;  
  double RobotActualSpeed;

  AnalogInput TankPressure = new AnalogInput(0);

 
  final double TargetCountDown = 90.0;   // signal driver when countdown is 40 seconds.
  int countdown_counter;
  boolean climb_now = false;
  boolean pressure_ok = false;
  
  double PressureVolts;
  final double PressureMax = 0.252;
  Scurve sobj = new Scurve(RightMotor, LeftMotor);
  SystemStatus sysstat = new SystemStatus();     // This object check game clock and pressure level

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() 
  {
    SmartDashboard.putString("Mode", "Robot Init" );
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    RightMotor.configOpenloopRamp(2);
    LeftMotor.configOpenloopRamp(2);

    RightMotor.set(ControlMode.PercentOutput, 0);
    LeftMotor.set(ControlMode.PercentOutput, 0);
    m_joystick = new Joystick(kJoystickPort);

    CameraServer.getInstance().startAutomaticCapture();
    sysstat.StartPressureCheck();
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
  public void robotPeriodic() 
  {
    sysstat.Check_System_Status(); 
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
  public void autonomousInit() 
  {
    SmartDashboard.putString("Mode", "Autonomous Init" );
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    sysstat.StartGameClock();
  }


  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() 
  {
    switch (m_autoSelected) 
    {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }


  @Override
  public void teleopInit() 
  {
    SmartDashboard.putString("Mode", "TeleOp Init" );
    super.teleopInit();
  }


  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() 
  {
    /* Gamepad processing */
   // double forward = -1 * m_joystick.getY();
   // double turn = m_joystick.getX();
    double forward =  m_joystick.getRawAxis(5);
    double turn = m_joystick.getRawAxis(4);

    forward = Deadband(forward);
    turn = Deadband(turn);

    if( m_joystick.getRawButton(5))
      RobotActualSpeed = ROBOT_MAX_SPEED;
    else
      RobotActualSpeed = ROBOT_NORMAL_SPEED; 
    
    forward = forward * RobotActualSpeed;

    turn = turn * RobotActualSpeed;

    /* Arcade Drive using PercentOutput along with Arbitrary Feed Forward supplied by turn */
    if( sobj.busy() == false )
    {
      RightMotor.set(ControlMode.PercentOutput, forward,  DemandType.ArbitraryFeedForward, +turn);
      LeftMotor.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, -turn);
   
      if( m_joystick.getRawButton(4))
      {
        if( sobj.busy() == false )
          sobj.Start_Move(true, 1.0, 25);
      }

      if( m_joystick.getRawButton(1))
      {
        if( sobj.busy() == false )
          sobj.Start_Move(false, 1.0, 25);
      }
    }

    sobj.scurve_move();
   
  }


  @Override
  public void testInit() 
  {
    SmartDashboard.putString("Mode", "Test Init" );
    super.testInit();
    RightMotor.set(ControlMode.PercentOutput, 0.0,  DemandType.ArbitraryFeedForward, 0.0);
    LeftMotor.set(ControlMode.PercentOutput, 0.0, DemandType.ArbitraryFeedForward, 0.0);
  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() 
  {
    
    if( m_joystick.getRawButton(4))
    {
      if( sobj.busy() == false )
        sobj.Start_Move(true, 1.0, 25);
    }

    if( m_joystick.getRawButton(1))
    {
      if( sobj.busy() == false )
        sobj.Start_Move(false, 1.0, 25);
    }

    sobj.scurve_move();

  }


  /** Deadband 8 percent, used on the gamepad */
  double Deadband(double value) 
  {   
    if (value >= +0.1)  // Upper Deadband 
      return value;
      
    if (value <= -0.1)  // Lower Deadband
      return value;
   
    return 0;           // Outside Deadband
  }
}
