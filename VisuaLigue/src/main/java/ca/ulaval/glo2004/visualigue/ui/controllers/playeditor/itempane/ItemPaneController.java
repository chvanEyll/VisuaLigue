package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane;

import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ItemPaneController extends ControllerBase {

    private static final double ITEM_PANE_COLLAPSED_WIDTH = 0;

    @FXML private VBox rootNode;
    @FXML private PlayerCategoryListController playerCategoryListController;
    @FXML private ObjectListController objectListController;
    private boolean isCollapsed = false;

    public void init(PlayModel playModel, SceneController sceneController) {
        playerCategoryListController.init(playModel, sceneController);
        objectListController.init(playModel, sceneController);
        super.addChild(playerCategoryListController);
        super.addChild(objectListController);
    }

    public void toggleExpand() {
        if (!isCollapsed) {
            collapse();
        } else {
            expand();
        }
    }

    private void collapse() {
        PredefinedAnimations.hideRight(rootNode, ITEM_PANE_COLLAPSED_WIDTH);
        isCollapsed = true;
    }

    private void expand() {
        PredefinedAnimations.revealRight(rootNode);
        isCollapsed = false;
    }

    public Boolean isCollapsed() {
        return isCollapsed;
    }

}
