// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.proto.Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Handoff;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.otbIntake;
import frc.robot.subsystems.shooter;

public class RobotContainer {
  public static final CommandXboxController controller = new CommandXboxController(0);
  private final Intake intake = new Intake();
  private final Handoff handoff = new Handoff();
  private final Elevator elevator = new Elevator();
  private final shooter shooter = new shooter();
  private final frc.robot.subsystems.otbIntake OTB = new otbIntake();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    controller.a().whileTrue(new InstantCommand(() -> intake.runIntake(4)));
    controller.b().whileTrue(new InstantCommand(() -> handoff.handoffRequest(3)));
    controller.x().onTrue(new InstantCommand(() -> elevator.setPosition(0.44)));
    controller.x().onTrue(new InstantCommand(() -> elevator.setPosition(.44)));
    controller.leftTrigger().whileTrue(new InstantCommand() -> shooter.setVelocity(15, 1)); //idk what the ratio is for so i just set it to 1
    controller.y().onTrue(new InstantCommand(() -> OTB.requestSetpoint(138)));
  } 



  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
