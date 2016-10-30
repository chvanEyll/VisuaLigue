package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.SvgImage;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

public class PlayerCategoryListItemController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/itempane/player-category-list-item.fxml";

    public EventHandler<PlayerCategoryModel> onClick = new EventHandler();
    @FXML private ExtendedButton rootNode;
    @FXML private SvgImage playerIcon;
    @FXML private Label abbreviationLabel;
    @FXML private Tooltip tooltip;
    private PlayerCategoryModel model;

    public void init(PlayerCategoryModel model, TeamSide teamSide) {
        this.model = model;
        if (teamSide == TeamSide.ALLIES) {
            setPlayerIconColor(model.allyPlayerColor.get());
        } else {
            setPlayerIconColor(model.opponentPlayerColor.get());
        }
        abbreviationLabel.textProperty().bind(model.abbreviation);
        tooltip.textProperty().bind(model.name);
    }

    private void setPlayerIconColor(Color color) {
        playerIcon.lookup("#innerCircle").setStyle(String.format("-fx-fill: %s", FXUtils.colorToHex(color)));
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
