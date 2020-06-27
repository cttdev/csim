package com._604robotics.csim;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;

public class NTHandler {
  private NetworkTableEntry m_enabled;
  private NetworkTableEntry m_setpoint;

  public NTHandler(Controller controller){
    // TODO: Switch to Network Tables API
    // NetworkTableInstance network = NetworkTableInstance.getDefault();
    // NetworkTable controllerTable = network.getTable("csim").getSubTable(controller.getClass().getSimpleName());
    
    SimpleWidget enabledWidget = Shuffleboard.getTab(controller.getClass().getName()).add("Enable", false);
    SimpleWidget setpointWidget = Shuffleboard.getTab(controller.getClass().getName()).add("Setpoint", 0.0);

    m_enabled = enabledWidget.getEntry();
    m_setpoint = setpointWidget.getEntry();

    SuppliedValueWidget<Double> position = Shuffleboard.getTab(controller.getClass().getName()).addNumber("Position", controller.getModel()::getPosition);
    SuppliedValueWidget<Double> velocity = Shuffleboard.getTab(controller.getClass().getName()).addNumber("Velocity", controller.getModel()::getVelocity);

    if (controller.getModel().getModelSubsystem() == StateSpaceModel.Subsystem.Position.ARM){
      SendableRegistry.add(controller.getModel(), "Arm Display");
      Shuffleboard.getTab(controller.getClass().getName()).add("Arm", controller.getModel()).withWidget("Arm Display");
    } else if (controller.getModel().getModelSubsystem() == StateSpaceModel.Subsystem.Position.ELEVATOR){
      throw new UnsupportedOperationException("Elevators are not yet supported with graphical displays!");
    } else if (controller.getModel().getModelSubsystem() == StateSpaceModel.Subsystem.Velocity.FLYWHEEL){
      throw new UnsupportedOperationException("Flywheels are not yet supported with graphical displays!");
    }
  }

  public double getSetpoint() {
    return m_setpoint.getDouble(0.0);
  }

  public boolean getEnabled(){
    return m_enabled.getBoolean(false);
  }
}
