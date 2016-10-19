package ca.ulaval.glo2004.visualigue.ui.controllers.obstaclemanagement;

import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleNotFoundException;
import ca.ulaval.glo2004.visualigue.services.ObstacleService;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.EditableListController;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ListItemController;
import ca.ulaval.glo2004.visualigue.ui.models.Model;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

public class ObstacleListController extends EditableListController {

    public static final String VIEW_NAME = "/views/obstaclemanagement/obstacle-list.fxml";
    @Inject private ObstacleService obstacleService;

    @Override
    protected void deleteItem(ListItemController listItemController, Model model) {
        try {
            obstacleService.deleteObstacle(model.getUUID());
        } catch (ObstacleNotFoundException ex) {
            Logger.getLogger(ObstacleListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.deleteItem(listItemController, model);
    }
}
