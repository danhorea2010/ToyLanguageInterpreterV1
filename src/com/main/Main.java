package com.main;

import controller.Controller;
import repository.IRepository;
import repository.StateRepository;
import view.UI;


public class Main {

    public static void main(String[] args) {

        IRepository repository = new StateRepository();
        Controller controller = new Controller((repository));
        UI ui = new UI(controller);
        ui.run();

    }


}
