package agh.ics.oop.darwinWorld.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Start.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Darwin World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
