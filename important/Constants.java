package frc.robot;

public class Constants {
    public static final class DriveConstants {
        public static final int KLEFT_LEADER_ID = 2;
        public static final int KLEFT_FOLLOWER_ID = 3;
        public static final int KRIGHT_LEADER_ID = 4;
        public static final int KRIGHT_FOLLOWER_ID = 5;
    
        public static final int KDRIVE_MOTOR_CURRENT_LIMIT = 60;
      }
    
      public static final class CoralReleaseConstants {
        public static final int KROLLER_MOTOR_ID = 6;
        public static final int KROLLER_MOTOR_CURRENT_LIMIT = 60;
        public static final double KROLLER_MOTOR_VOLTAGE_COMP = 10;
        public static final double KROLLER_EJECT_VALUE = 0.44;
      }
    
      public static final class OperatorConstants {
        public static final int KDRIVER_CONTROLLER_PORT = 0;
        public static final int KOPERATOR_CONTROLLER_PORT = 1;
      }

      public static final class AutonomousConstants {
        public static final String KDEFAULT_AUTONOMOUS = "Default";
        public static final String KCUSTOM_AUTONMOUS = "My Auton";
      }
}
