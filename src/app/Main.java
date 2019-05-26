package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login_window.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
        primaryStage.setOnHiding(e -> startMainProgramm());

    }

    private void startMainProgramm() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("main_window.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage mainWindow = new Stage();
        mainWindow.setTitle("Book Store");
        mainWindow.setScene(new Scene(root, 1200, 800));
        mainWindow.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
