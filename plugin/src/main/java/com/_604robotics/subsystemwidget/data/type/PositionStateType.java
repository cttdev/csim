package com._604robotics.subsystemwidget.data.type;

import com._604robotics.subsystemwidget.data.PositionState;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.Map;
import java.util.function.Function;


public final class PositionStateType extends ComplexDataType<PositionState> {

  private static final String TYPE_NAME = "PositionState";

  public static final PositionStateType Instance = new PositionStateType();

  private PositionStateType() {
    super(TYPE_NAME, PositionState.class);
  }

  @Override
  public Function<Map<String, Object>, PositionState> fromMap() {
    return map -> new PositionState(
            Maps.getOrDefault(map, "position", 0.0),
            Maps.getOrDefault(map, "velocity", 0.0),
            Maps.getOrDefault(map, "dt", 0.02)
    );
  }

  @Override
  public PositionState getDefaultValue() {
    return new PositionState(0.0, 0.0, 0.02);
  }
}
