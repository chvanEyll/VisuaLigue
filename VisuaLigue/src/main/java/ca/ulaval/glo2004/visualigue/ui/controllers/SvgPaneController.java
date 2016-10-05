package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SvgPaneController {

    public static final String VIEW_NAME = "/views/svg-pane.fxml";

    @FXML
    private Pane rootNode;

    public Node getRootNode() {
        return rootNode;
    }

    public void init(String svgFileName) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(svgFileName + ".fxml");
        rootNode.getStylesheets().clear();
        rootNode.getStylesheets().add(svgFileName + ".fxml.css");
        rootNode.getChildren().add(fxmlLoader.getRoot());
    }

}
