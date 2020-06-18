package com._604robotics.csim;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.system.LinearSystem;
import edu.wpi.first.wpilibj.system.plant.DCMotor;
import edu.wpi.first.wpiutil.math.MatBuilder;
import edu.wpi.first.wpiutil.math.Nat;
import edu.wpi.first.wpiutil.math.Pair;
import edu.wpi.first.wpiutil.math.numbers.N1;
import edu.wpi.first.wpiutil.math.numbers.N2;

public class PositionVelocityModel extends StateSpaceModel {
    private LinearSystem<N2, N1, N1> m_system;


    public PositionVelocityModel(Subsystem.Position subsystem, LinearSystem<N2, N1, N1> system) {
        super(subsystem);
        m_system = system;
    }

    public static PositionVelocityModel makeArm(DCMotor motor, double moi, double gearing){
        return new PositionVelocityModel(Subsystem.Position.ARM, LinearSystem.createSingleJointedArmSystem(motor, moi, gearing));
    }

    public static PositionVelocityModel makeElevator(DCMotor motor, double mass, double drumRadius, double gearing){
        return new PositionVelocityModel(Subsystem.Position.ELEVATOR, LinearSystem.createElevatorSystem(motor, mass, drumRadius, gearing));
    }

    public void reset(){
        m_system.reset();
        m_lastTimestamp = Double.NaN;
    }

    public void reset(Pair<Double, Double> initialState) {
        reset();
        m_system.setX(new MatBuilder<>(Nat.N2(), Nat.N1()).fill(initialState.getFirst(), initialState.getSecond()));
    }

    @Override
    public void calculate(double inputVoltage){
        clamp(inputVoltage, -12.0, 12.0);

        double dt = 0.0;
        if(m_enabled) {
            if (Double.isNaN(m_lastTimestamp)) {
                m_lastTimestamp = Timer.getFPGATimestamp();
            } else {
                dt = Timer.getFPGATimestamp() - m_lastTimestamp;
                m_lastTimestamp = Timer.getFPGATimestamp();
            }

            m_system.setX(m_system.calculateX(m_system.getX(), new MatBuilder<>(Nat.N1(), Nat.N1()).fill(inputVoltage), dt));
        }
    }
    

    @Override
    public double getPosition(){
        return m_system.getX(0);
    }

    @Override
    public double getVelocity(){
        return m_system.getX(1);
    }
}