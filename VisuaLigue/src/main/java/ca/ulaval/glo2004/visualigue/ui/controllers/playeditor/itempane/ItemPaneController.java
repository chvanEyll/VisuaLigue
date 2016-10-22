package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane;

import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ItemPaneController extends ControllerBase {

    private static final double ITEM_PANE_COLLAPSED_WIDTH = 58.0;

    @FXML private VBox rootNode;
    @FXML private ScrollPane scrollPane;
    @FXML private PlayerCategoryListController playerCategoryListController;
    @FXML private ObjectListController objectListController;
    private boolean isCollapsed = true;

    public void init(UUID sportUUID, SceneController sceneController) {
        playerCategoryListController.init(sportUUID, sceneController);
        objectListController.init(sportUUID, sceneController);
    }

    @FXML
    protected void onMenuToggleClicked(MouseEvent e) {
        toggleExpand();
    }

    public void toggleExpand() {
        if (!isCollapsed) {
            collapse();
        } else {
            expand();
        }
    }

    private void collapse() {
        PredefinedAnimations.regionCollapse(rootNode, ITEM_PANE_COLLAPSED_WIDTH);
        isCollapsed = true;
    }

    private void expand() {
        PredefinedAnimations.regionExpand(rootNode, scrollPane.getPrefWidth());
        isCollapsed = false;
    }

}
