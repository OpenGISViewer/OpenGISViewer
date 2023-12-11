package com.opengisviewer.opengisviewer.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;

import static com.opengisviewer.opengisviewer.controllers.ApplicationTools.generateAlert;

@Slf4j
public class DataPanelController {

    @FXML
    TextArea urlTextArea;
    @FXML
    Button findUrlButton;
    @FXML
    TreeView dataTree;

    @FXML
    public void getUrl() {
        String urlString = urlTextArea.getText();

        try {
            URL url = new URL(urlString);
            // TODO: need to validate return from getting this URL get Capabilites request/ just service request returns proper capabilities document

        } catch (MalformedURLException ex) { // handles no characters, or malformed URLs
            log.error("No Valid URL provided");
            generateAlert(Alert.AlertType.ERROR, "Unable to reach URL", "Error", "No valid URL was provided.");
        }

    }

}
