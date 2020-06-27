package com._604robotics.subsystemwidget;

import com._604robotics.subsystemwidget.data.PositionState;
import com._604robotics.subsystemwidget.data.type.PositionStateType;
import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.data.types.NumberType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

import com._604robotics.subsystemwidget.widget.ArmDisplayWidget;

import java.util.List;
import java.util.Map;

/**
 * A plugin that provides a graphical display of robot subsystem positions.
 */
@Description(
    group = "com._604robotics",
    name = "csim-plugin",
    version = "2020.1.0",
    summary = "A plugin that provides a graphical display of robot subsystem positions."
)
public final class CSimPlugin extends Plugin {

  @Override
  public List<DataType> getDataTypes() {
    return List.of(
            PositionStateType.Instance
    );
  }

  @Override
  public List<ComponentType> getComponents() {
    return List.of(
        WidgetType.forAnnotatedWidget(ArmDisplayWidget.class)
    );
  }

  @Override
  public Map<DataType, ComponentType> getDefaultComponents() {
    return Map.of(
            PositionStateType.Instance, WidgetType.forAnnotatedWidget(ArmDisplayWidget.class)
    );
  }
}
