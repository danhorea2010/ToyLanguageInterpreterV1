package com.main;

import controller.GuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view.fxml"));
            //AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view.fxml"));
            AnchorPane root = fxmlLoader.load();

            Scene scene = new Scene(root, 1200, 1000);

            GuiController controller = fxmlLoader.getController();
            controller.setMainStage(stage);

            stage.setTitle("Toy Language Interpreter");
            stage.setScene(scene);
            stage.show();


        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        launch(args);

    }

}
