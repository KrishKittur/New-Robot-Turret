package frc.robot.commands.turret;

import frc.robot.subsystems.TurretSubsystem;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;

public class TurretToAngleCommand extends CommandBase {
    
    // Initialize the subsystems, controllers, and the controllers values
    PIDController turretController = new PIDController(0.1, 0, 0);
    TurretSubsystem req_subsystem;
    double turretSetpoint;

    public TurretToAngleCommand(TurretSubsystem subsystem, DoubleSupplier angle) {
        // Establish the commands requirements and set the setters
        req_subsystem = subsystem;
        addRequirements(subsystem);
        turretSetpoint = angle.getAsDouble() + 119.0;
        turretController.setTolerance(5, 2);
    }

    // In the execute function, set the turret according to the PID controllers readings
    @Override
    public void execute() {
        double outputPID = turretController.calculate(req_subsystem.readTurretEncoder(), turretSetpoint);
        req_subsystem.setTurretMotor(MathUtil.clamp(outputPID, -3, 3));
        System.out.println(req_subsystem.readTurretEncoder() + ", " + turretSetpoint + ", " + turretController.getPositionError() + ", " + MathUtil.clamp(outputPID, -3, 3));
    }

    // If the turret controller is at its setpoint then stop the command
    @Override
    public boolean isFinished() {
        return turretController.atSetpoint();
    }

    // In the end method turn the turret motor off
    @Override
    public void end(boolean interrupted) {
        req_subsystem.setTurretMotor(0);
    }

}
