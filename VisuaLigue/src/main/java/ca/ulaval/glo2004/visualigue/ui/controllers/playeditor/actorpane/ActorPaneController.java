package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorpane;

import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.javafx.ClippingUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ActorPaneController extends ControllerBase {

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
        ClippingUtils.clipToSize(rootNode);
    }

    @Override
    public void clean() {
        ClippingUtils.unclip(rootNode);
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
