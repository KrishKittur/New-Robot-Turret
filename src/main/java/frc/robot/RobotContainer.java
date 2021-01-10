package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.turret.TurretToAngleCommand;
import frc.robot.commands.turret.TurretToVisionAngleCommand;
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
    new POVButton(controller, 90).whenPressed(
      new TurretToAngleCommand(turretSubsystem, -90)
    );

    new POVButton(controller, 180).whenPressed(
      new TurretToAngleCommand(turretSubsystem, -180)
    );

    new POVButton(controller, 270).whenPressed(
      new TurretToAngleCommand(turretSubsystem, 90)
    );

    new POVButton(controller, 0).whenPressed(
      new TurretToAngleCommand(turretSubsystem, 0)
    );

    new JoystickButton(controller, Button.kA.value).whenPressed(
      new TurretToVisionAngleCommand(turretSubsystem)
    );

  }

  public void getAutonomousCommand() {
    // Add the return statement for the auto command here
  }

  
}
