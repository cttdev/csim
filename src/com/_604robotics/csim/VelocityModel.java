package com._604robotics.csim;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.system.LinearSystem;
import edu.wpi.first.wpilibj.system.plant.DCMotor;
import edu.wpi.first.wpiutil.math.MatBuilder;
import edu.wpi.first.wpiutil.math.Nat;
import edu.wpi.first.wpiutil.math.numbers.N1;

public class VelocityModel extends StateSpaceModel {
    private LinearSystem<N1, N1, N1> m_system;

    private double m_cumulativePosition = 0.0;


    public VelocityModel(Subsystem.Velocity subsystem, LinearSystem<N1, N1, N1> system) {
        super(subsystem);
        m_system = system;
    }

    public static VelocityModel makeArm(DCMotor motor, double moi, double gearing){
        return new VelocityModel(Subsystem.Velocity.FLYWHEEL, LinearSystem.createFlywheelSystem(motor, moi, gearing));
    }

    public void reset(){
        m_system.reset();
        m_lastTimestamp = Double.NaN;
        m_cumulativePosition = 0.0;
    }

    public void reset(double initialState) {
        reset();
        m_system.setX(new MatBuilder<>(Nat.N1(), Nat.N1()).fill(initialState));
    }

    @Override
    public void calculate(double inputVoltage){
        clamp(inputVoltage, -12.0, 12.0);

        double dt = 0.0;
        if(m_enabled) {
            if (Double.isNaN(m_lastTimestamp)) {
                m_lastTimestamp = 0.0;
            } else {
                dt = Timer.getFPGATimestamp() - m_lastTimestamp;
                m_lastTimestamp = Timer.getFPGATimestamp();
            }

            m_system.setX(m_system.calculateX(m_system.getX(), new MatBuilder<>(Nat.N1(), Nat.N1()).fill(inputVoltage), dt));
        }
        m_cumulativePosition += m_system.getX(0) * dt;
    }

    @Override
    public double getPosition(){
        return m_cumulativePosition;
    }

    @Override
    public double getVelocity(){
        return m_system.getX(0);
    }
}