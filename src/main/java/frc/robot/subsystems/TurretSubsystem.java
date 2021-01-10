package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPipelineResult;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.SimVisionSystem;
import org.photonvision.SimVisionTarget;

public class TurretSubsystem extends SubsystemBase {

    // Instantiate the hardware
    private final CANSparkMax turretMotor = new CANSparkMax(25, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final DutyCycleEncoder turretEncoder = new DutyCycleEncoder(4);
    private final PhotonCamera camera = new PhotonCamera("Bigboy");

    String camName = "Bigboy";
    double cameDiagFOV = 75.0; // degrees
    double camPitch = 0.0; // degrees
    Transform2d cameraToRobot = new Transform2d(); // meters
    double camHeightOffGround = 0.85; // meters
    double maxLEDRange = 20; // meters
    int camResolutionHeight = 640; // pixels
    int camResolutionWidth = 480; // pixels
    double minTargetArea = 10; // square pixels

    SimVisionSystem simVision = RobotBase.isSimulation() ? new SimVisionSystem(camName,
        cameDiagFOV,
        camPitch,
        cameraToRobot,
        camHeightOffGround,
        maxLEDRange,
        camResolutionWidth,
        camResolutionHeight,
        minTargetArea) : null;
    Field2d field = new Field2d();

    public TurretSubsystem() {
        // Set the setters
        turretMotor.setSmartCurrentLimit(25);
        turretEncoder.setDistancePerRotation(360.0);

        if(RobotBase.isSimulation()) {
            var targetPose = new Pose2d(new Translation2d(15,3), new Rotation2d()); // meters
            double targetHeightAboveGround = 2.3; // meters
            double targetWidth = 0.54; // meters
            double targetHeight = 0.25; // meters

            var newTgt = new SimVisionTarget(targetPose,
                targetHeightAboveGround,
                targetWidth,
                targetHeight);

            simVision.addSimVisionTarget(newTgt);
            field.setRobotPose(new Pose2d(2, 2, new Rotation2d()));
            field.getObject("goal").setPose(targetPose);
            SmartDashboard.putData("Field", field);
        }
    }

    @Override
    public void simulationPeriodic() {
        simVision.processFrame(field.getRobotPose());

        // Sketchy print we can remove
        var tgt = camera.getLatestResult();
        if(tgt.hasTargets()) {
            System.out.println(tgt.getBestTarget().getYaw());
        }
    }

    // Method to read the turret encoders values
    public double readTurretEncoder() {
        return turretEncoder.getDistance() - 119.0;
    }

    // Method to get the current yaw that vision in sending out
    public double getTurretYaw() {
        PhotonPipelineResult result = camera.getLatestResult();
        if (result.hasTargets()) {
            return -result.getBestTarget().getYaw();
        } else {
            return 0.0;
        }
    }

    // Method to set the turret motors speed 
    public void setTurretMotor(double voltage) {
        turretMotor.setVoltage(voltage);
    }

    // Add turret's encoder readings to SmartDashboard
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Turret Angle", readTurretEncoder());
    }
}