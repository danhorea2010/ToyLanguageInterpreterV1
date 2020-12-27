package com.main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//
//public class Main {
//
//    public static void main(String[] args) {
//
//        // For log file
////        System.out.print("Enter log file: ");
////        Scanner scanner = new Scanner(System.in);
////        String logFile = scanner.nextLine();
//
//        var logFile = "log.txt";
//
//        // Make sure logfile is empty before starting
//        PrintWriter file = null;
//        try {
//            file = new PrintWriter(logFile);
//            file.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        IRepository repository = new StateRepository(logFile);
//        Controller controller  = new Controller(repository);
//
//        controller.setDisplayTag(true);
//
//        TextMenu textMenu = new TextMenu(controller);
//        textMenu.show();
//        //UI ui = new UI(controller);
//        //ui.run();
//
//    }
//
//
//}

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.PINK);

        stage.setTitle("F");

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }

}
