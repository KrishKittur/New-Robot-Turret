package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    // Instantiate the hardware
    private final CANSparkMax turretMotor = new CANSparkMax(25, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final DutyCycleEncoder turretEncoder = new DutyCycleEncoder(4);

    public TurretSubsystem() {
        // Set the setters
        turretMotor.setSmartCurrentLimit(25);
        turretEncoder.setDistancePerRotation(360.0/1.0);
    }

    // Method to read the turret encoders values
    public double readTurretEncoder() {
        return turretEncoder.getDistance() - 119.0;
    }

    // Method to set the turret motors speed 
    public void setTurretMotor(double voltage) {
        turretMotor.setVoltage(voltage);
    }
}