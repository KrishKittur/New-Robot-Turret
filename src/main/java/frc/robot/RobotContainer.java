package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.turret.ResetTurretCommand;
import frc.robot.commands.turret.TurretToAngleCommand;
import frc.robot.commands.turret.TurretToJoystickAngleCommand;
import frc.robot.subsystems.TurretSubsystem;

public class RobotContainer {

  XboxController controller = new XboxController(0);
  TurretSubsystem turretSubsystem = new TurretSubsystem();
  

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set the default commands
    turretSubsystem.setDefaultCommand(
      new TurretToJoystickAngleCommand(turretSubsystem, () -> controller.getX(Hand.kLeft), () -> controller.getY(Hand.kLeft))
    );
  }

  private void configureButtonBindings() {
  }

  public void getAutonomousCommand() {
    // Add the return statement for the auto command here
  }

  
}
