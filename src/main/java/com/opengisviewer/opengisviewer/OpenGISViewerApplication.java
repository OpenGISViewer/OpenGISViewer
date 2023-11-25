package com.opengisviewer.opengisviewer;

import com.opengisviewer.opengisviewer.utils.DesktopTools;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OpenGISViewerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OpenGISViewerApplication.class.getResource("open-gis-viewer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), DesktopTools.getDimensionsOfPrimaryDisplay()[0], DesktopTools.getDimensionsOfPrimaryDisplay()[1]);
        stage.setTitle("OpenGISViewer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}