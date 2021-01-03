package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.turret.TurretToAngleCommand;
import frc.robot.subsystems.TurretSubsystem;

public class RobotContainer {

  XboxController controller = new XboxController(0);
  TurretSubsystem turretSubsystem = new TurretSubsystem();
  

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set the default commands
  
  }

  private void configureButtonBindings() {
    new JoystickButton(controller, Button.kA.value).whenHeld(
      new TurretToAngleCommand(turretSubsystem, () -> 0.0)
    );
    
  }


  public void getAutonomousCommand() {
    // Add the return statement for the auto command here
  }

  
}
