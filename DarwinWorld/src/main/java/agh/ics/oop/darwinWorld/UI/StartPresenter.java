package agh.ics.oop.darwinWorld.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartPresenter {
    public Button startButton;

    @FXML
    private void initialize() {
        startButton.setOnAction(event ->
        {
            MenuApp menuApp = new MenuApp();
            try {
                menuApp.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Stage currentStage = (Stage) startButton.getScene().getWindow();
            currentStage.close();
        });
    }
}
