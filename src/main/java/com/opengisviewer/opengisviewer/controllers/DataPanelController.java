package com.opengisviewer.opengisviewer.controllers;

import com.opengisviewer.opengisviewer.utils.ResourceUtils;
import com.opengisviewer.opengisviewer.utils.XMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.extern.slf4j.Slf4j;
import org.geotools.ows.ServiceException;
import org.geotools.ows.wms.WMSCapabilities;
import org.geotools.ows.wms.WebMapServer;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.opengisviewer.opengisviewer.controllers.ApplicationTools.generateAlert;

@Slf4j
public class DataPanelController {

    @FXML
    public TextField urlTextArea;

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

            WebMapServer wms = new WebMapServer(url);
            WMSCapabilities capabilities = wms.getCapabilities();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
            } catch (Exception e) {
                log.error("lol fix-me-later");
            }
        } catch (IOException e) {
            log.error("No Valid URL provided");
            generateAlert(Alert.AlertType.ERROR, "Unable to reach URL", "Error", "No valid URL was provided.");
        } catch (ServiceException e) {
            //The server returned a ServiceException (unusual in this case)
        } catch (SAXException e) {
            generateAlert(Alert.AlertType.ERROR, "Invalid Get Capabilities Document ", "Invalid Get Capabilities", "Invalid Get Capabilities document found, please verify that your server is properly configured.");
        }
    }

}
