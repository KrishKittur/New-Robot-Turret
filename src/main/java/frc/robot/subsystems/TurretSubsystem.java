package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPipelineResult;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    // Instantiate the hardware
    private final CANSparkMax turretMotor = new CANSparkMax(25, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final DutyCycleEncoder turretEncoder = new DutyCycleEncoder(4);
    private final PhotonCamera camera = new PhotonCamera("Bigboy");

    public TurretSubsystem() {
        // Set the setters
        turretMotor.setSmartCurrentLimit(25);
        turretEncoder.setDistancePerRotation(360.0/1.0);
    }

    // Method to read the turret encoders values
    public double readTurretEncoder() {
        return turretEncoder.getDistance() - 131.0;
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