package com._604robotics.csim;

import edu.wpi.first.wpiutil.math.Pair;

import java.util.ArrayList;

public class ControllerManager {
    private ArrayList<Pair<Controller, NTHandler>> m_controllers = new ArrayList<Pair<Controller, NTHandler>>();

    private static ControllerManager single_instance = null;

    public ControllerManager() {}

    public static ControllerManager getInstance() {
        if (single_instance == null) single_instance = new ControllerManager();
    
        return single_instance;
    }

    public void registerController(Controller controller){
        System.out.println(controller);
        m_controllers.add(new Pair<>(controller, new NTHandler(controller)));
    }

    public void update(){
        for (Pair<Controller, NTHandler> c : m_controllers){
            NTHandler handler = c.getSecond();
            Controller controller = c.getFirst();
            StateSpaceModel model = controller.getModel();

            if(handler.getEnabled()){
                model.enable();
                model.calculate(controller.calculate(handler.getSetpoint(), model.getPosition()));
                System.out.println("Calculating" + model.getPosition());
            } else {
                controller.getModel().disable();
            }

        }
    }

}