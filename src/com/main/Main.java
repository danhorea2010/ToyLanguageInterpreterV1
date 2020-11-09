package com.main;

import controller.Controller;
import repository.IRepository;
import repository.StateRepository;
import view.UI;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        // For log file
        System.out.print("Enter log file: ");
        Scanner scanner = new Scanner(System.in);
        String logFile = scanner.nextLine();

        IRepository repository = new StateRepository(logFile);
        Controller controller = new Controller(repository);

        controller.setDisplayTag(true);

        UI ui = new UI(controller);
        ui.run();

    }


}
