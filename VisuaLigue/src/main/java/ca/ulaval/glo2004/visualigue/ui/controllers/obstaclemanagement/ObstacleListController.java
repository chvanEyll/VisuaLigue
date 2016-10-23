package ca.ulaval.glo2004.visualigue.ui.controllers.obstaclemanagement;

import ca.ulaval.glo2004.visualigue.services.obstacle.ObstacleService;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.EditableListController;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ListItemController;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

public class ObstacleListController extends EditableListController {

    public static final String VIEW_NAME = "/views/obstaclemanagement/obstacle-list.fxml";
    @Inject private ObstacleService obstacleService;

    @FXML
    protected void onObstacleCreationLinkButtonClicked(MouseEvent e) {
        newItem();
    }

    @Override
    protected void deleteItem(ListItemController listItemController, ModelBase model) {
        obstacleService.deleteObstacle(model.getUUID());
        super.deleteItem(listItemController, model);
    }

}
