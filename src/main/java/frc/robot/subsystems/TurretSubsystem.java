package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.simulation.DutyCycleEncoderSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.system.plant.DCMotor;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    // Instantiate the hardware
    private final CANSparkMax turretMotor = new CANSparkMax(25, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final SingleJointedArmSim simTurretMotor = new SingleJointedArmSim(
        DCMotor.getNEO(1), 25, 0.06, Units.inchesToMeters(6), Units.degreesToRadians(-10000), Units.degreesToRadians(10000), 0.425 * 3 + 1, false
    );
    private final DutyCycleEncoder turretEncoder = new DutyCycleEncoder(4);
    private final DutyCycleEncoderSim simTurretEncoder = new DutyCycleEncoderSim(turretEncoder);

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
        simTurretMotor.setInput(voltage);
    }

    // In the simulation periodic method, update the simulation motor, encoder, and add values to SmartDashboard
    @Override
    public void simulationPeriodic() {
        simTurretMotor.update(0.020);
        simTurretEncoder.setDistance(Units.radiansToDegrees(simTurretMotor.getAngleRads()));
        SmartDashboard.putNumber("Motor Angle", readTurretEncoder());
    }
}