package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor;

import ca.ulaval.glo2004.visualigue.domain.play.PlayNotFoundException;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.ViewFlowRequestEventArgs;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane.ItemPaneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol.SequencePaneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.toolbar.ToolbarController;
import ca.ulaval.glo2004.visualigue.ui.dialog.AlertDialogBuilder;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import java.util.Optional;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javax.inject.Inject;

public class PlayEditorController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/play-editor.fxml";

    @FXML private ItemPaneController itemPaneController;
    @FXML private SceneController sceneController;
    @FXML private ToolbarController toolbarController;
    @FXML private SequencePaneController sequencePaneController;
    @Inject PlayService playService;
    private PlayModel playModel;

    public void init(PlayModel playModel) throws PlayNotFoundException {
        this.playModel = playModel;
        sceneController.init(playModel);
        itemPaneController.init(playModel, sceneController);
        toolbarController.init(playModel, sceneController, itemPaneController);
        toolbarController.onSaveButtonAction.setHandler(this::onSaveToolbarButtonAction);
        toolbarController.onCloseButtonAction.setHandler(this::onCloseToolbarButtonAction);
        toolbarController.onExportButtonAction.setHandler(this::onExportToolbarButtonAction);
        toolbarController.onUndoButtonAction.setHandler(this::onUndoToolbarButtonAction);
        toolbarController.onRedoButtonAction.setHandler(this::onRedoToolbarButtonAction);
        toolbarController.onBestFitButtonAction.setHandler(this::onBestFitToolbarButtonAction);
        sequencePaneController.init(playModel, sceneController);
        sceneController.enterNavigationMode();
        playModel.title.addListener(this::onPlayTitleChanged);
        super.addChild(sceneController);
        super.addChild(itemPaneController);
        super.addChild(toolbarController);
        super.addChild(sequencePaneController);
    }

    @Override
    public StringProperty getTitle() {
        return playModel.title;
    }

    @Override
    public Boolean isTitleEditable() {
        return true;
    }

    @Override
    public Boolean onViewClosing() {
        return validateUnsavedChanges() != ButtonBar.ButtonData.CANCEL_CLOSE;
    }

    private void onPlayTitleChanged(final ObservableValue<? extends String> value, final String oldPropertyValue, final String newPropertyValue) {
        playService.updatePlayTitle(playModel.getUUID(), playModel.title.get());
    }

    private void onSaveToolbarButtonAction(Object sender, Object eventArgs) {
        savePlay();
    }

    private void savePlay() {
        playService.savePlay(playModel.getUUID());
    }

    private void onCloseToolbarButtonAction(Object sender, Object eventArgs) {
        onViewCloseRequested.fire(this, new ViewFlowRequestEventArgs());
    }

    private void discardChanges() {
        playService.discardChanges(playModel.getUUID());
    }

    private void onExportToolbarButtonAction(Object sender, Object eventArgs) {
        //TODO
    }

    private void onUndoToolbarButtonAction(Object sender, Object eventArgs) {
        playService.undo(playModel.getUUID());
    }

    private void onRedoToolbarButtonAction(Object sender, Object eventArgs) {
        playService.redo(playModel.getUUID());
    }

    private void onBestFitToolbarButtonAction(Object sender, Object eventArgs) {
        sceneController.autoFit();
    }

    private ButtonBar.ButtonData validateUnsavedChanges() {
        Optional<ButtonType> result = new AlertDialogBuilder().alertType(Alert.AlertType.WARNING).headerText("Fermeture du jeu")
                .contentText("Voulez-vous sauvegarder le jeu avant de quitter?")
                .buttonType(new ButtonType("Sauvegarder", ButtonBar.ButtonData.YES))
                .buttonType(new ButtonType("Ne pas sauvegarder", ButtonBar.ButtonData.NO))
                .buttonType(new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE)).showAndWait();

        if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
            savePlay();
        } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO) {
            discardChanges();
        }
        return result.get().getButtonData();
    }

}
