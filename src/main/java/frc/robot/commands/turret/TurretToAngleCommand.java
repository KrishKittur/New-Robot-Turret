package frc.robot.commands.turret;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.subsystems.TurretSubsystem;

public class TurretToAngleCommand extends CommandBase {
    
    // Initialize the subsystems, controllers, and the controllers values
    PIDController turretController = new PIDController(0.1, 0, 0);
    TurretSubsystem req_subsystem;
    double desiredAngle;
    double turretSetpoint;

    public TurretToAngleCommand(TurretSubsystem subsystem, double angle) {
        // Establish the commands requirements and set the setters
        req_subsystem = subsystem;
        addRequirements(subsystem);
        desiredAngle = angle;
        turretController.setTolerance(5, 2);
    }

    // In the initialize method set the setpoint
    @Override
    public void initialize() {
        turretSetpoint = calcWhereToTurn(desiredAngle, req_subsystem.readTurretEncoder());
    }

    // In the execute method of this command move the turret based on the PID controllers readings
    @Override
    public void execute() {
        double outputPID = turretController.calculate(req_subsystem.readTurretEncoder(), turretSetpoint);
        req_subsystem.setTurretMotor(MathUtil.clamp(outputPID, -3, 3));
    }

    // If the PID controller is at its setpoint then end the command
    @Override
    public boolean isFinished() {
        return turretController.atSetpoint();
    }
    
    // In the end method of this command set the turret motor to 0
    @Override
    public void end(boolean interrupted) {
        req_subsystem.setTurretMotor(0);
    }

    // Function to return where the turret should turn, based on a given angle between the range of (-180, 180)
    static double calcWhereToTurn(double angle, double currentTurretPosition) {
        double angleToReturn = 0.0;
        if (angle > -135.0 && angle < 135.0) {
            angleToReturn = angle;
        } else {
            if (angle < 0.0) {
                angleToReturn = calcClosestTo(angle, (angle + 360), currentTurretPosition);
            } else {
                angleToReturn = calcClosestTo(angle, (angle - 360), currentTurretPosition);
            }
        }
        return angleToReturn;
    }

    // Function to calculate the closest number
    static double calcClosestTo(double num1, double num2, double closestToNum) {
        if (Math.abs(num1 - closestToNum) < Math.abs(num2 - closestToNum)) {
            return num1;
        } else {
            return num2;
        }
    }



}
