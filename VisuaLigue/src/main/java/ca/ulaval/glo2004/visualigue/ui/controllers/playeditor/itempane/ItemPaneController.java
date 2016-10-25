package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane;

import ca.ulaval.glo2004.visualigue.services.sport.SportService;
import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.ui.controllers.ViewController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javax.inject.Inject;

public class ItemPaneController extends ViewController {

    private static final double ITEM_PANE_COLLAPSED_WIDTH = 0;

    @FXML private ScrollPane rootNode;
    @FXML private PlayerCategoryListController playerCategoryListController;
    @FXML private ObjectListController objectListController;
    @Inject private SportService sportService;
    private boolean isCollapsed = false;
    private Double expandedWidth;
    private Double contentWidth;

    public void init(PlayModel playModel, SceneController sceneController) {
        playerCategoryListController.init(playModel, sceneController);
        objectListController.init(playModel, sceneController);
    }

    public void toggleExpand() {
        if (!isCollapsed) {
            collapse();
        } else {
            expand();
        }
    }

    private void collapse() {
        ((Region) rootNode.getContent()).setMinWidth(((Region) rootNode.getContent()).getWidth());
        expandedWidth = rootNode.getWidth();
        PredefinedAnimations.regionCollapse(rootNode, ITEM_PANE_COLLAPSED_WIDTH);
        isCollapsed = true;
    }

    private void expand() {
        ((Region) rootNode.getContent()).setMinWidth(-1);
        PredefinedAnimations.regionExpand(rootNode, expandedWidth);
        isCollapsed = false;
    }

}
