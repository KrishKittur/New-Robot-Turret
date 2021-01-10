package frc.robot.commands.turret;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.subsystems.TurretSubsystem;

public class TurretToVisionAngleCommand extends CommandBase {
    
    // Initialize the subsystems, controllers, and the controllers values
    PIDController turretController = new PIDController(0.1, 0, 0.001);
    TurretSubsystem req_subsystem;
    double turretSetpoint;

    public TurretToVisionAngleCommand(TurretSubsystem subsystem) {
        // Establish the commands requirements and set the setters
        req_subsystem = subsystem;
        addRequirements(subsystem);
        turretController.setTolerance(5, 2);
    }

    // In the initalize method set the setpoint
    @Override
    public void initialize() {
        turretSetpoint = req_subsystem.readTurretEncoder() + req_subsystem.getTurretYaw();
    }

    // In the execute method set the turret motor based on the controllers readings
    @Override
    public void execute() {
        double outputPID = turretController.calculate(req_subsystem.readTurretEncoder(), turretSetpoint);
        req_subsystem.setTurretMotor(MathUtil.clamp(outputPID, -5, 5));
        System.out.println(outputPID + ", " + turretSetpoint);
    }

    // In the end method of this command set the turret motor to 0
    @Override
    public void end(boolean interrupted) {
        req_subsystem.setTurretMotor(0);
    }

    // Function to covert any angle to an angle for the turret to turn to 
    static double calcWhereToTurn(double angle, double turretPosition) {

        double angle360 = Math.abs(angle % 360);
        if (angle < 0.0) {
            if (angle360 != 0.0) {
                angle360 = 360 - angle360;
            }
        }

        double finalAngle;
        if (angle360 > 180) {
            finalAngle = -360 + angle360;
        } else {
            finalAngle = angle360;
        }
        
        if (Math.abs(angle) == 180) {
            finalAngle = angle;
        }

        return calcWhereToTurnLogic(finalAngle, turretPosition);
    }

    // Function to return where the turret should turn, based on a given angle between the range of (-180, 180)
    static double calcWhereToTurnLogic(double angle, double currentTurretPosition) {
        double angleToReturn = 0.0;
        if (angle > -135.0 && angle < 135.0) {
            angleToReturn = angle;
        }
        else {
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
