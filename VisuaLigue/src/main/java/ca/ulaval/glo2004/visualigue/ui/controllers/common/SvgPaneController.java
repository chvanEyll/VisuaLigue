package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SvgPaneController {

    public static final String VIEW_NAME = "/views/common/svg-pane.fxml";

    @FXML
    private Pane rootNode;

    public Node getRootNode() {
        return rootNode;
    }

    public void init(String svgFileName) {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(svgFileName + ".fxml");
        rootNode.getStylesheets().clear();
        rootNode.getStylesheets().add(svgFileName + ".fxml.css");
        rootNode.getChildren().add(fxmlLoader.getRoot());
    }

}
