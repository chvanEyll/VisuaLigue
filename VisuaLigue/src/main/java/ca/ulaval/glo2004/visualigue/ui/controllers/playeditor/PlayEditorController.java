package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayNotFoundException;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane.ItemPaneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.toolbar.ToolbarController;
import java.util.UUID;
import javafx.fxml.FXML;
import javax.inject.Inject;

public class PlayEditorController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/play-editor.fxml";

    @FXML private ItemPaneController itemPaneController;
    @FXML private SceneController sceneController;
    @FXML private ToolbarController toolbarController;
    @Inject private PlayService playService;
    private UUID playUUID;

    public void init(UUID playUUID) throws PlayNotFoundException {
        this.playUUID = playUUID;
        Play play = playService.getPlay(playUUID);
        itemPaneController.init(play.getSport().getUUID(), sceneController);
        toolbarController.init(playUUID, sceneController);
        toolbarController.onSaveButtonAction.addHandler(this::onSaveToolbarButtonAction);
        toolbarController.onCloseButtonAction.addHandler(this::onCloseToolbarButtonAction);
        toolbarController.onExportButtonAction.addHandler(this::onExportToolbarButtonAction);
        toolbarController.onUndoButtonAction.addHandler(this::onUndoToolbarButtonAction);
        toolbarController.onRedoButtonAction.addHandler(this::onRedoToolbarButtonAction);
        toolbarController.onBestFitButtonAction.addHandler(this::onBestFitToolbarButtonAction);
    }

    private void onSaveToolbarButtonAction(Object sender, Object eventArgs) {
        try {
            playService.savePlay(playUUID);
        } catch (PlayNotFoundException ex) {
            throw new RuntimeException();
        }
    }

    private void onCloseToolbarButtonAction(Object sender, Object eventArgs) {
        try {
            playService.discardChanges(playUUID);
        } catch (PlayNotFoundException ex) {
            throw new RuntimeException();
        }
        onViewCloseRequested.fire(sender, null);
    }

    private void onExportToolbarButtonAction(Object sender, Object eventArgs) {
        //TODO
    }

    private void onUndoToolbarButtonAction(Object sender, Object eventArgs) {
        try {
            playService.undo(playUUID);
        } catch (PlayNotFoundException ex) {
            throw new RuntimeException();
        }
        sceneController.undo();
    }

    private void onRedoToolbarButtonAction(Object sender, Object eventArgs) {
        try {
            playService.redo(playUUID);
        } catch (PlayNotFoundException ex) {
            throw new RuntimeException();
        }
        sceneController.redo();
    }

    private void onBestFitToolbarButtonAction(Object sender, Object eventArgs) {
        sceneController.autoFit();
    }

}
