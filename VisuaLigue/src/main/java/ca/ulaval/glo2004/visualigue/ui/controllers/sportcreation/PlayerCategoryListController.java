package ca.ulaval.glo2004.visualigue.ui.controllers.sportcreation;

import ca.ulaval.glo2004.visualigue.domain.play.PlayIntegrityViolationException;
import ca.ulaval.glo2004.visualigue.services.sport.SportService;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist.EditableListController;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist.ListItemController;
import ca.ulaval.glo2004.visualigue.ui.dialog.AlertDialogBuilder;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javax.inject.Inject;

public class PlayerCategoryListController extends EditableListController {

    public static final String VIEW_NAME = "/views/obstaclemanagement/player-category-list.fxml";
    @Inject private SportService sportService;

    @Override
    protected void deleteItem(ListItemController listItemController, ModelBase model) {
        try {
            sportService.simulatePlayerCategoryDeletion(model.getUUID());
            super.deleteItem(listItemController, model);
        } catch (PlayIntegrityViolationException ex) {
            new AlertDialogBuilder().alertType(Alert.AlertType.ERROR).headerText("Suppression impossible")
                    .contentText(String.format("Cette catégorie ne peut être supprimée car elle est utilisé dans le jeu '%s'.", ex.getPlay().getTitle()))
                    .buttonType(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE)).showAndWait();
        }
    }

}
