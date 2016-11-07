package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorpane;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.PlayerIcon;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class PlayerCategoryListItemController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/actorpane/player-category-list-item.fxml";

    public EventHandler<PlayerCategoryModel> onClick = new EventHandler();
    @FXML private ExtendedButton rootNode;
    @FXML private PlayerIcon playerIcon;
    @FXML private Label abbreviationLabel;
    @FXML private Tooltip tooltip;
    private PlayerCategoryModel model;

    public void init(PlayerCategoryModel model, TeamSide teamSide) {
        this.model = model;
        if (teamSide == TeamSide.ALLIES) {
            playerIcon.setColor(model.allyPlayerColor.get());
        } else {
            playerIcon.setColor(model.opponentPlayerColor.get());
        }
        abbreviationLabel.textProperty().bind(model.abbreviation);
        tooltip.textProperty().bind(model.name);
    }

    public void select() {
        rootNode.setSelected(true);
    }

    public void unselect() {
        rootNode.setSelected(false);
    }

    public Boolean isSelected() {
        return rootNode.isSelected();
    }

    @FXML
    protected void onAction(ActionEvent e) {
        onClick.fire(this, model);
    }

}
