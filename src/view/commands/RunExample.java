package view.commands;

import controller.Controller;

public class RunExample extends Command {

    private final Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute(){
        try{
            controller.allStepAll();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
