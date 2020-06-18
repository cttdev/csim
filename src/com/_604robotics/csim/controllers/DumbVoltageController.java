package com._604robotics.csim.controllers;

import com._604robotics.csim.Controller;
import com._604robotics.csim.PositionVelocityModel;

import edu.wpi.first.wpilibj.system.plant.DCMotor;

public class DumbVoltageController extends Controller {
  public DumbVoltageController(){
    super(PositionVelocityModel.makeArm(DCMotor.getFalcon500(2), 1, 10));
  }

  @Override
  public double calculate(double setpoint, double measurement){
    return (setpoint - measurement) * 0.6;
  }
}
