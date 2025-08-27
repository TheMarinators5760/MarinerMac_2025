package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import frc.robot.Constants.DriveConstants;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants.DriveConstants; 
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class DifferentialDrivetrain extends SubsystemBase {
    private final SparkMax leftLead;
   private final SparkMax leftFollow;
    private final SparkMax rightLead;
   private final SparkMax rightFollow;

    private final DifferentialDrive drive;

    public DifferentialDrivetrain() {
        //brushed motors -> simple, less pricey. Roattes motor with input output current

        leftLead = new SparkMax(DriveConstants.KLEFT_LEADER_ID, MotorType.kBrushed);
       leftFollow = new SparkMax(DriveConstants.KLEFT_FOLLOWER_ID, MotorType.kBrushed);
        rightLead = new SparkMax(DriveConstants.KRIGHT_LEADER_ID, MotorType.kBrushed);
        rightFollow = new SparkMax(DriveConstants.KRIGHT_FOLLOWER_ID, MotorType.kBrushed);

        drive = new DifferentialDrive(leftLead, rightLead);

        //set timeout to not block robot operation upon construction
        leftLead.setCANTimeout(250);
        rightLead.setCANTimeout(250);
       rightFollow.setCANTimeout(250);
        leftFollow.setCANTimeout(250);

        //create config for motors
        //Voltage compensation helps on differing batter volatges
        // -slightly slower at full charge
        //limit helps prevent tripping breakers

        SparkMaxConfig config = new SparkMaxConfig();
        config.voltageCompensation(12);
        config.smartCurrentLimit(DriveConstants.KDRIVE_MOTOR_CURRENT_LIMIT);

        //Set config to follow leader and have follower motor
        //follow leader
        //measures to reset on controller swap and breaker trips

     config.follow(leftLead);
        leftFollow.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    config.follow(rightLead);
        rightFollow.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //Remove following, then apply config to right leader
        config.disableFollowerMode();
     rightLead.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        //set config to inverted the apply left leader.
        //postive will drive both foward
        config.inverted(true);
        leftLead.configure(config,  ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void periodic() {
    }

    //joystick control command
    public Command driveTank(DifferentialDrivetrain drivetrain, DoubleSupplier leftSpeed, DoubleSupplier rightSpeed) {
        return Commands.run(() -> drive.tankDrive(leftSpeed.getAsDouble(), rightSpeed.getAsDouble()), drivetrain);
    } 

    public void drive(CommandXboxController controller) {
        drive.tankDrive(MathUtil.clamp(controller.getLeftY(), -0.8, 0.8), MathUtil.clamp(controller.getRightY(), -0.8, 0.8));
    }

    public void setMotors(double leftSpeed, double rightSpeed) {
        leftLead.set(leftSpeed);
        rightLead.set(rightSpeed);
    }
}

