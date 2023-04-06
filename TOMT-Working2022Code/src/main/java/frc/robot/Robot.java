// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  private TalonSRX left = new TalonSRX(1);
  private TalonSRX right = new TalonSRX(3);

  private TalonSRX headMotorLeft = new TalonSRX(2);
  private TalonSRX headMotorRight = new TalonSRX(4);

  private Solenoid arm = new Solenoid(1, PneumaticsModuleType.CTREPCM, 5);
  private Solenoid head = new Solenoid(1, PneumaticsModuleType.CTREPCM, 6);
  private Solenoid shifter = new Solenoid(1, PneumaticsModuleType.CTREPCM, 7);

  // private Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);

  private Joystick joystick = new Joystick(0);
  private double shootSpeed;
  private double yAxis;
  private double zAxis;

  private boolean toggleState = false;
  private boolean shifterState = false;

  @Override
  public void robotInit() {
    left.setInverted(true);
    left.setNeutralMode(NeutralMode.Brake);
    right.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    yAxis = joystick.getRawAxis(1);
    zAxis = joystick.getRawAxis(0);

    left.set(ControlMode.PercentOutput, yAxis - zAxis);
    right.set(ControlMode.PercentOutput, yAxis + zAxis);

    if (joystick.getPOV() == 0) {
      shootSpeed = (joystick.getRawAxis(3) + 1) / 2;
    } else if (joystick.getRawButton(5)) {
      shootSpeed = -0.5;
    } else if (joystick.getRawButton(3)) {
      shootSpeed = 0.3;
    } else if (joystick.getRawButton(6)) {
      shootSpeed = 0.7;
    } else if (joystick.getRawButton(4)) {
      shootSpeed = 1;
    } else {
      shootSpeed = 0;
    }

    //Shooting Speed
    headMotorLeft.set(ControlMode.PercentOutput, -shootSpeed);
    headMotorRight.set(ControlMode.PercentOutput, -shootSpeed);
    SmartDashboard.putNumber("speed", -shootSpeed);
    SmartDashboard.putNumber("POV Button", joystick.getPOV());

    //Shooter piston
    if (joystick.getRawButton(1)) {
      arm.set(true);
    } else {
      arm.set(false);
    }

    //Head down and up
    if (joystick.getRawButtonPressed(2)) {
      if (!toggleState) {
        head.set(true);
        toggleState = true;
      } else {
        head.set(false);
        toggleState = false;
      }
    }

    //Shifters engage and disengage
    if (joystick.getRawButtonPressed(9)) {
      if (!shifterState) {
        shifter.set(true);
        shifterState = true;
      } else {
        shifter.set(false);
        shifterState = false;
      }
    }

  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }
}
