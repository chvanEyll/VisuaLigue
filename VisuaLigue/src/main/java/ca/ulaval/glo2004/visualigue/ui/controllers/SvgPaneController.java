package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SvgPaneController {

    @FXML
    private Pane rootNode;

    public Pane getRootNode() {
        return rootNode;
    }

    public void setSvg(String svgFileName) {
        try {
            Node svgNode = GuiceFXMLLoader.load(getClass().getResource(svgFileName + ".fxml"));
            rootNode.getStylesheets().clear();
            rootNode.getStylesheets().add(svgFileName + ".fxml.css");
            rootNode.getChildren().add(svgNode);
        } catch (IOException ex) {
            Logger.getLogger(SvgPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
