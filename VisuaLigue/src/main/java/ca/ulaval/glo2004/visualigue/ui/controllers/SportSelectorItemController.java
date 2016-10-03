package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.domain.Sport;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class SportSelectorItemController {

    public static final String SVG_VIEW_NAME = "/views/svg-pane.fxml";

    @FXML
    private VBox rootNode;
    private Sport sport;
    public EventHandler<Sport> onClick = new EventHandler();

    public VBox getRootNode() {
        return rootNode;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
        setSportImage(sport.getBuiltInIconFileName());
    }

    private void setSportImage(String sportImageFileName) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.createLoader(getClass().getResource(SVG_VIEW_NAME));
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(SvgPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SvgPaneController controller = (SvgPaneController) fxmlLoader.getController();
        controller.setSvg(sportImageFileName);
        rootNode.getChildren().add(0, controller.getRootNode());
    }

    @FXML
    private void onClick() {
        onClick.fire(this, sport);
    }

}
