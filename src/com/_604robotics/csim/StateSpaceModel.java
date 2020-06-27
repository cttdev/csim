package com._604robotics.csim;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;

public abstract class StateSpaceModel implements Sendable, AutoCloseable {
    protected Subsystem m_subsystem;
    protected boolean m_enabled = false;

    protected double m_dt;

    protected double m_lastTimestamp = Double.NaN;

    protected StateSpaceModel(Subsystem subsystem) {
        m_subsystem = subsystem;
    }

    public Subsystem getModelSubsystem(){
        return m_subsystem;
    }

    public void enable(){
        m_enabled = true;
    }

    public void disable(){
        m_enabled = false;
        m_lastTimestamp = Double.NaN;
    }

    public abstract void calculate(double inputVoltage);

    public abstract double getPosition();

    public abstract double getVelocity();

    public abstract void reset();

    public double getDt(){
        return m_dt;
    }

    public boolean isEnabled(){
        return m_enabled;
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    @Override
    public void initSendable(SendableBuilder builder) {
      builder.setSmartDashboardType("PositionState");
      builder.addDoubleProperty("position", this::getPosition, null);
      builder.addDoubleProperty("velocity", this::getVelocity, null);
      builder.addDoubleProperty("dt", this::getDt, null);
    }

    @Override
    public void close(){
        SendableRegistry.remove(this);
        reset();
    }

    // Marker interface smells but, how to do compile time check
    public static interface Subsystem{
        enum Position implements Subsystem{
            ARM,
            ELEVATOR
        }

        enum Velocity implements Subsystem{
            FLYWHEEL
        }
    }

}