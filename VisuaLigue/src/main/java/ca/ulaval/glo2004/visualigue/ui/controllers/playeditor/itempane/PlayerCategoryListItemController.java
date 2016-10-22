package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.customcontrols.SvgImage;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class PlayerCategoryListItemController {

    public static final String VIEW_NAME = "/views/playeditor/itempane/player-category-list-item.fxml";

    public EventHandler<PlayerCategoryModel> onClick = new EventHandler();
    @FXML private Button rootNode;
    @FXML private SvgImage playerIcon;
    @FXML private Label abrbeviationLabel;
    private PlayerCategoryModel model;
    private Boolean selected = false;

    public void init(PlayerCategoryModel model, TeamSide teamSide) {
        this.model = model;
        if (teamSide == TeamSide.ALLIES) {
            setPlayerIconColor(model.allyPlayerColor.get());
        } else {
            setPlayerIconColor(model.opponentPlayerColor.get());
        }
        abrbeviationLabel.textProperty().bindBidirectional(model.abbreviation);
    }

    private void setPlayerIconColor(Color color) {
        playerIcon.setStyle(String.format("-fx-fill: %s", FXUtils.colorToHex(color)));
    }

    public void select() {
        rootNode.getStyleClass().add("selected");
        selected = true;
    }

    public void unselect() {
        rootNode.getStyleClass().remove("selected");
        selected = false;
    }

    @FXML
    public void onAction(ActionEvent e) {
        onClick.fire(this, model);
    }

}
