//Functions of a robot container

/* - Define subsystems and commands 
 * - Binds commands to events such as button presses
 * - Select autnoumous when running
 * - Generates code for opertor interface and links them to commands
 * - defines commands for subsysytems when no other commands are running
 * -bluk of robot declared here
 *  -Subsystems, commands, trigger mappings
 */

 package frc.robot;

 import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
 //import edu.wpi.first.wpilibj2.command.button.Trigger;
 
 import frc.robot.Constants.OperatorConstants;
 import frc.robot.Constants.CoralReleaseConstants;
 import frc.robot.commands.Autonomous;
 
 import frc.robot.subsystems.DifferentialDrivetrain;
 import frc.robot.subsystems.CoralRelease;
 
 
 public class RobotContainer {
     
     //subsystems
     private final DifferentialDrivetrain drivetrain = new DifferentialDrivetrain();
     private final CoralRelease coralRelease = new CoralRelease();
 
     //controller: Driver
     private final CommandXboxController driverController = new CommandXboxController(
         OperatorConstants.KDRIVER_CONTROLLER_PORT
     );
 
     //controller: operator
     private final CommandXboxController operatorController= new CommandXboxController(
         OperatorConstants.KOPERATOR_CONTROLLER_PORT
     );
 
     //Auton chooser
     private final SendableChooser<Command> autoChooser = new SendableChooser<>();
 
     //Container: Subsystems, OI Devices, and commands
 
     public RobotContainer() {
         configureBindings();
 
         //set auton options on dashboard: TODO: add other autons with autoChooser.addOption
         autoChooser.setDefaultOption("exampleAuto", Autonomous.moveforward(drivetrain));
         autoChooser.addOption("Do Nothing", Autonomous.doAbsolutelyNothing(drivetrain));
        SmartDashboard.putData("Auto choices", autoChooser);

        // SmartDashboard.putData("Run Motors", new InstantCommand(() -> drivetrain.setMotors(0.2, -0.2), drivetrain));
     }
 
     //bind triggers to actions
     private void configureBindings() {
         //ex: Set A button to 
        //  driverController.a().whileTrue(coralRelease.runRoller(coralRelease, () -> CoralReleaseConstants.KROLLER_EJECT_VALUE, () -> 0));
 
         //Set default commad fro drivetrain to Command provided by factory
         //with joystick axes on driver controller
         //Y axis is inverted so pushing towards driver moves it forward
         //TODO: ask driver what they prefer
 
         drivetrain.setDefaultCommand(
             drivetrain.driveTank(
                 drivetrain, () -> -driverController.getLeftY(), () -> -driverController.getRightY()));
        
 
        //  Set default for coral deposit with Oerator controller
         coralRelease.setDefaultCommand(
             coralRelease.runRoller(coralRelease, () -> driverController.getRightTriggerAxis(), () -> driverController.getLeftTriggerAxis()));
     }
 
     //Used to pass auton commands to the main {@link Robot} class.
     //return command to run auton
     public Command getAutonomousCommand() {
         return autoChooser.getSelected();
     }
 
 
 }
