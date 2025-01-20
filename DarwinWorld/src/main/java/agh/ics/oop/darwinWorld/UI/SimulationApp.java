package agh.ics.oop.darwinWorld.UI;

import agh.ics.oop.darwinWorld.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimulationApp extends Application {
    private final Config config;

    public SimulationApp(Config config) {
        this.config = config;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Simulation.fxml"));
        SimulationPresenter presenter = new SimulationPresenter();
        presenter.setConfig(config);
        loader.setController(presenter);
        Parent root = loader.load();
        primaryStage.setTitle("Darwin World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            presenter.stopSimulation();
        });

    }
}
