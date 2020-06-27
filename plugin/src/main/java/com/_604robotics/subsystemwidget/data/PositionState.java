package com._604robotics.subsystemwidget.data;

import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.Map;

public final class PositionState extends ComplexData<PositionState> {

  private final double position;
  private final double velocity;

  private final double dt;


  public PositionState(double position, double velocity, double dt) {
    this.position = position;
    this.velocity = velocity;
    this.dt = dt;
  }

  public double getPosition() {
    return position;
  }

  public double getVelocity() {
    return velocity;
  }

  public double getDt() {
    return dt;
  }

  @Override
  public String toHumanReadableString() {
    return "Position: " + position +
            " Velocity: " + velocity +
            " dt: " + dt;
  }

  @Override
  public Map<String, Object> asMap() {
    return Map.of("position", position, "velocity", velocity, "dt", dt);
  }
}