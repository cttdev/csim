package com._604robotics.csim;

public abstract class Controller {
    protected double m_dt = 0.02;

    protected StateSpaceModel m_model;

    public Controller(StateSpaceModel model) {
        m_model = model;
    }

    public abstract double calculate(double setpoint, double measurement);

    public StateSpaceModel getModel(){
        return m_model;
    }
}