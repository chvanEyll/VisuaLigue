package ca.ulaval.glo2004.visualigue.ui.controllers.obstaclemanagement;

import ca.ulaval.glo2004.visualigue.domain.play.PlayIntegrityViolationException;
import ca.ulaval.glo2004.visualigue.services.obstacle.ObstacleService;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist.EditableListController;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist.ListItemController;
import ca.ulaval.glo2004.visualigue.ui.dialog.AlertDialogBuilder;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javax.inject.Inject;

public class ObstacleListController extends EditableListController {

    public static final String VIEW_NAME = "/views/obstaclemanagement/obstacle-list.fxml";
    @Inject private ObstacleService obstacleService;

    @Override
    protected void deleteItem(ListItemController listItemController, ModelBase model) {
        try {
            obstacleService.deleteObstacle(model.getUUID());
            super.deleteItem(listItemController, model);
        } catch (PlayIntegrityViolationException ex) {
            new AlertDialogBuilder().alertType(Alert.AlertType.ERROR).headerText("Suppression impossible")
                    .contentText(String.format("Cet obstacle ne peut être supprimé car il est utilisé dans le jeu '%s'.", ex.getPlay().getTitle()))
                    .buttonType(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE)).showAndWait();
        }
    }

}
