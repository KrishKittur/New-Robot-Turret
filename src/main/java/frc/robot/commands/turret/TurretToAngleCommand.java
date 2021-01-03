package frc.robot.commands.turret;

import frc.robot.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurretToAngleCommand extends CommandBase {
    
    // Initialize the subsystems, controllers, and the controllers values
    TurretSubsystem req_subsystem;

    public TurretToAngleCommand(TurretSubsystem subsystem) {
        // Establish the commands requirements and set the setters
        req_subsystem = subsystem;
        addRequirements(subsystem);
        
    }

}
