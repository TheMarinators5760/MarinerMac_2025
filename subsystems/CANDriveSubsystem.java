package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.DriveCommand;

// Class to drive the robot over CAN
public class CANDriveSubsystem extends SubsystemBase {

  private final SparkMax m_leftLeader;
  private final SparkMax m_leftFollower;
  private final SparkMax m_rightLeader;
  private final SparkMax m_rightFollower;

  private final DifferentialDrive drive;

  public CANDriveSubsystem() {
    // create brushed motors for drive
    m_leftLeader = new SparkMax(DriveConstants.leftLeader, MotorType.kBrushed);
    m_leftFollower = new SparkMax(DriveConstants.leftFollower, MotorType.kBrushed);
    m_rightLeader = new SparkMax(DriveConstants.rightLeader,MotorType.kBrushed);
    m_rightFollower = new SparkMax(DriveConstants.rightFollower, MotorType.kBrushed);

   

    // set up differential drive class
    drive = new DifferentialDrive(m_leftLeader, m_rightLeader);

    // Set can timeout. Because this project only sets parameters once on
    // construction, the timeout can be long without blocking robot operation. Code
    // which sets or gets parameters during operation may need a shorter timeout.

    
    m_leftLeader.setCANTimeout(250);
    m_rightLeader.setCANTimeout(250);
    m_leftFollower.setCANTimeout(250);
    m_rightFollower.setCANTimeout(250);

    // Create the configuration to apply to motors. Voltage compensation
    // helps the robot perform more similarly on different
    // battery voltages (at the cost of a little bit of top speed on a fully charged
    // battery). The current limit helps prevent tripping
    // breakers.
    SparkMaxConfig config = new SparkMaxConfig();
    config.voltageCompensation(12);
    config.smartCurrentLimit(DriveConstants.DRIVE_MOTOR_CURRENT_LIMIT);

    // Set configuration to follow leader and then apply it to corresponding
    // follower. Resetting in case a new controller is swapped
    // in and persisting in case of a controller reset due to breaker trip
    config.follow(m_leftLeader);
    m_leftFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    config.follow(m_rightLeader);
    m_rightFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Remove following, then apply config to right leade
   config.disableFollowerMode();
    m_rightLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // Set conifg to inverted and then apply to left leader. Set Left side inverted
    // so that postive values drive both sides forward
    config.inverted(true);
    m_leftLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
  }

// driveSubsystem.driveTank(-xSpeed.getAsDouble(), zRotation.getAsDouble());

}
