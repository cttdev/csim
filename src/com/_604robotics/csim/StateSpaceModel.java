package com._604robotics.csim;

public abstract class StateSpaceModel {
    protected Subsystem m_subsystem;
    protected boolean m_enabled = false;

    protected double m_lastTimestamp = Double.NaN;

    protected StateSpaceModel(Subsystem subsystem) {
        m_subsystem = subsystem;
    }

    public Subsystem getSubsystem(){
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

    public boolean isEnabled(){
        return m_enabled;
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
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