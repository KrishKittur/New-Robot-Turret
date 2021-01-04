package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.turret.ResetTurretCommand;
import frc.robot.commands.turret.TurretToAngleCommand;
import frc.robot.subsystems.TurretSubsystem;

public class RobotContainer {

  XboxController controller = new XboxController(0);
  TurretSubsystem turretSubsystem = new TurretSubsystem();
  

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set the default commands
    turretSubsystem.setDefaultCommand(new RunCommand(() -> turretSubsystem.setTurretMotor(0), turretSubsystem));
  }

  private void configureButtonBindings() {
    // If the 90 degree POV button is held, then set the Turret Motor at 3 volts
    new POVButton(controller, 90).whenHeld(
      new RunCommand(() -> turretSubsystem.setTurretMotor(3), turretSubsystem)
    );

    // If the 90 degree POV button is held, then set the Turret Motor at -3 volts
    new POVButton(controller, 270).whenHeld(
      new RunCommand(() -> turretSubsystem.setTurretMotor(-3), turretSubsystem)
    );

    // If the A button is pressed, then set the turret motor angle to 0 degrees
    new JoystickButton(controller, Button.kA.value).whenPressed(
      new ResetTurretCommand(turretSubsystem)
    );

    // If the B button is pressed, then set the turret motor angle to 90 degrees
    new JoystickButton(controller, Button.kB.value).whenPressed(
      new TurretToAngleCommand(turretSubsystem, 90)
    );

    // If the X button is pressed, then set the turret motor angle to -90 degrees
    new JoystickButton(controller, Button.kX.value).whenPressed(
      new TurretToAngleCommand(turretSubsystem, -90)
    );

    // If the Y button is pressed, then set the turret motor angle to 128 degrees
    new JoystickButton(controller, Button.kY.value).whenPressed(
      new TurretToAngleCommand(turretSubsystem, 138)
    );


    
    
  }

  public void getAutonomousCommand() {
    // Add the return statement for the auto command here
  }

  
}
