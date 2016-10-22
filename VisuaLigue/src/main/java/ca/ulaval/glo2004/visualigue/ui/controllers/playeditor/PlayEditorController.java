package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor;

import ca.ulaval.glo2004.visualigue.domain.play.PlayNotFoundException;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane.ItemPaneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
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
    @Inject PlayService playService;
    private PlayModel playModel;

    public void init(PlayModel playModel) throws PlayNotFoundException {
        this.playModel = playModel;
        itemPaneController.init(playModel, sceneController);
        toolbarController.init(playModel, sceneController);
        toolbarController.onSaveButtonAction.addHandler(this::onSaveToolbarButtonAction);
        toolbarController.onCloseButtonAction.addHandler(this::onCloseToolbarButtonAction);
        toolbarController.onExportButtonAction.addHandler(this::onExportToolbarButtonAction);
        toolbarController.onUndoButtonAction.addHandler(this::onUndoToolbarButtonAction);
        toolbarController.onRedoButtonAction.addHandler(this::onRedoToolbarButtonAction);
        toolbarController.onBestFitButtonAction.addHandler(this::onBestFitToolbarButtonAction);
        playModel.title.addListener(this::onPlayTitleChanged);
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

    private void onPlayTitleChanged(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
        try {
            playService.updatePlayTitle(playModel.getUUID(), playModel.title.get());
        } catch (PlayNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onSaveToolbarButtonAction(Object sender, Object eventArgs) {
        savePlay();
    }

    private void savePlay() {
        try {
            playService.savePlay(playModel.getUUID());
        } catch (PlayNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onCloseToolbarButtonAction(Object sender, Object eventArgs) {
        onViewCloseRequested.fire(this, null);
    }

    private void discardChanges() {
        try {
            playService.discardChanges(playModel.getUUID());
        } catch (PlayNotFoundException ex) {
            throw new RuntimeException();
        }
    }

    private void onExportToolbarButtonAction(Object sender, Object eventArgs) {
        //TODO
    }

    private void onUndoToolbarButtonAction(Object sender, Object eventArgs) {
        try {
            playService.undo(playModel.getUUID());
        } catch (PlayNotFoundException ex) {
            throw new RuntimeException();
        }
        sceneController.undo();
    }

    private void onRedoToolbarButtonAction(Object sender, Object eventArgs) {
        try {
            playService.redo(playModel.getUUID());
        } catch (PlayNotFoundException ex) {
            throw new RuntimeException();
        }
        sceneController.redo();
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
