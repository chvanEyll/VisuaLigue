package ca.ulaval.glo2004.visualigue.ui.controllers.obstaclemanagement;

import ca.ulaval.glo2004.visualigue.services.obstacle.ObstacleService;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist.EditableListController;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist.ListItemController;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import javax.inject.Inject;

public class ObstacleListController extends EditableListController {

    public static final String VIEW_NAME = "/views/obstaclemanagement/obstacle-list.fxml";
    @Inject private ObstacleService obstacleService;

    @Override
    protected void deleteItem(ListItemController listItemController, ModelBase model) {
        obstacleService.deleteObstacle(model.getUUID());
        super.deleteItem(listItemController, model);
    }

}
