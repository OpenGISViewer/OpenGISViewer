package com.opengisviewer.opengisviewer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OpenGISViewerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}