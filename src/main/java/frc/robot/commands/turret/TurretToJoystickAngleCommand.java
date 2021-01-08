package frc.robot.commands.turret;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.subsystems.TurretSubsystem;

public class TurretToJoystickAngleCommand extends CommandBase {
    
    // Initialize the subsystems, controllers, and the controllers values
    PIDController turretController = new PIDController(0.1, 0, 0);
    TurretSubsystem req_subsystem;
    double turretSetpoint;
    DoubleSupplier joystickXSupplier;
    DoubleSupplier joystickYSupplier;

    public TurretToJoystickAngleCommand(TurretSubsystem subsystem, DoubleSupplier joystickX, DoubleSupplier joystickY) {
        // Establish the commands requirements and set the setters
        req_subsystem = subsystem;
        addRequirements(subsystem);
        turretController.setTolerance(5, 2);
        joystickXSupplier = joystickX;
        joystickYSupplier = joystickY;
    }

    // In the execute method of this command move the turret based on the PID controllers readings
    @Override
    public void execute() {
        double joystickAngle = calculateJoystickAngle(joystickXSupplier.getAsDouble(), joystickYSupplier.getAsDouble());
        turretSetpoint = calculateWhereToTurn(joystickAngle, req_subsystem.readTurretEncoder());
        double outputPID = turretController.calculate(req_subsystem.readTurretEncoder(), turretSetpoint);
        req_subsystem.setTurretMotor(MathUtil.clamp(outputPID, -3, 3));
    }

    // Function to return where the turret should turn, based on a given angle between the range of (-180, 180)
    static double calculateWhereToTurn(double angle, double currentTurretPosition) {
        double angleToReturn = 0.0;
        if (angle > -135.0 && angle < 135.0) {
            angleToReturn = angle;
        } else {
            if (angle < 0.0) {
                angleToReturn = calculateClosestTo(angle, (angle + 360), currentTurretPosition);
            } else {
                angleToReturn = calculateClosestTo(angle, (angle - 360), currentTurretPosition);
            }
        }
        return angleToReturn;
    }

    // Function to calculate the closest number
    static double calculateClosestTo(double num1, double num2, double closestToNum) {
        if (Math.abs(num1 - closestToNum) < Math.abs(num2 - closestToNum)) {
            return num1;
        } else {
            return num2;
        }
    }

    // Method to calculate the angle of the joystick (regardless of quadrant)
    private double calculateQuadrantAngle(double joystickX, double joystickY) {
        double absolutejoystickX = Math.abs(joystickX);
        double absolutejoystickY = Math.abs(joystickY);
        double tanOfTheta = absolutejoystickY / absolutejoystickX;
        double theta = Math.atan(tanOfTheta);
        return Units.radiansToDegrees(theta);
      }
    
    // Method to calculate which angle the joystick is at
    private double calculateJoystickAngle(double joystickX, double joystickY) {
        double returnAngle = 0;
        if (Math.abs(joystickX) > 0.2 || Math.abs(joystickY) > 0.2) {
          if (joystickX > 0.0 && joystickY < 0.0) {
            returnAngle = 90 - calculateQuadrantAngle(joystickX, joystickY);
          } else if (joystickX > 0.0 && joystickY > 0.0) {
            returnAngle = 90 + calculateQuadrantAngle(joystickX, joystickY);
          } else if (joystickX < 0.0 && joystickY > 0.0) {
            returnAngle = (90 + calculateQuadrantAngle(joystickX, joystickY)) * -1;
          } else if (joystickX < 0.0 && joystickY < 0.0) {
            returnAngle = (90 - calculateQuadrantAngle(joystickX, joystickY)) * -1;
          }
        } else {
          returnAngle = 0.0;
        }
        return returnAngle;
    }
      

}