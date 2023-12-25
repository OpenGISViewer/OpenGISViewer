package com.opengisviewer.opengisviewer.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
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

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            //TODO: need to convert/get proper get capabilities XSDs for all wms services.... likelly need to loop through all of them and validate against, or smartly find version.... but need to do this efficiently.... interesting problem.
            boolean isValidGetCapabilities = true; // XMLUtils.validateXMLSchema(ResourceUtils.getResourceFileStream() ,con.getInputStream());
            if(isValidGetCapabilities){
                try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))){
                    String inputLine;
                    StringBuffer content = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }

                }
            } else {
                generateAlert(Alert.AlertType.ERROR, "Invalid Get Capabilities Document ", "Invalid Get Capabilities", "Invalid Get Capabilities document found, please verify that your server is properly configured.");
            }


        } catch (IOException ex) { // handles no characters, or malformed URLs
            log.error("No Valid URL provided");
            generateAlert(Alert.AlertType.ERROR, "Unable to reach URL", "Error", "No valid URL was provided.");
        }

    }

}
