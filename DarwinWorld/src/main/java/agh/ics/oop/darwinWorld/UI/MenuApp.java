package agh.ics.oop.darwinWorld.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
        BorderPane rootNode = loader.load();

        primaryStage.setTitle("Menu");
        primaryStage.setScene(new Scene(rootNode));
        primaryStage.show();

    }
}