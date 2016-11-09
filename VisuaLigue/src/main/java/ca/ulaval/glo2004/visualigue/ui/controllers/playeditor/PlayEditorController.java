package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayNotFoundException;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.KeyboardShortcutMapper;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.ViewFlowRequestEventArgs;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorpane.ActorPaneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol.SequencePaneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.toolbar.ToolbarController;
import ca.ulaval.glo2004.visualigue.ui.converters.PlayModelConverter;
import ca.ulaval.glo2004.visualigue.ui.dialog.AlertDialogBuilder;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import java.util.Optional;
import java.util.function.BiConsumer;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javax.inject.Inject;

public class PlayEditorController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/play-editor.fxml";

    @FXML private ActorPaneController actorPaneController;
    @FXML private SceneController sceneController;
    @FXML private ToolbarController toolbarController;
    @FXML private SequencePaneController sequencePaneController;
    @Inject PlayService playService;
    @Inject private PlayModelConverter playModelConverter;
    private PlayModel playModel;
    private BiConsumer<Object, Play> onPlayUpdated = this::onPlayUpdated;

    public void init(String playUUID) throws PlayNotFoundException {
        Play play = playService.getPlay(playUUID);
        this.playModel = playModelConverter.convert(play);
        sceneController.init(playModel);
        actorPaneController.init(playModel, sceneController);
        sequencePaneController.init(playModel, sceneController);
        toolbarController.init(playModel, sceneController, actorPaneController, sequencePaneController);
        sceneController.enterNavigationMode();
        playModel.title.addListener(this::onPlayTitleChanged);
        initHandlers();
        initKeyboardShortcuts();
        super.addChild(sceneController);
        super.addChild(actorPaneController);
        super.addChild(toolbarController);
        super.addChild(sequencePaneController);
    }

    private void initHandlers() {
        playService.onPlayUpdated.addHandler(onPlayUpdated);
        toolbarController.onSaveButtonAction.setHandler(this::onSaveToolbarButtonAction);
        toolbarController.onCloseButtonAction.setHandler(this::onCloseToolbarButtonAction);
        toolbarController.onExportButtonAction.setHandler(this::onExportToolbarButtonAction);
        toolbarController.onUndoButtonAction.setHandler(this::onUndoToolbarButtonAction);
        toolbarController.onRedoButtonAction.setHandler(this::onRedoToolbarButtonAction);
    }

    private void initKeyboardShortcuts() {
        KeyboardShortcutMapper.map(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), this::savePlay);
        KeyboardShortcutMapper.map(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN), this::closePlay);
        KeyboardShortcutMapper.map(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN), this::undo);
        KeyboardShortcutMapper.map(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN), this::redo);
    }

    @Override
    public void clean() {
        playService.onPlayUpdated.removeHandler(onPlayUpdated);
        KeyboardShortcutMapper.unmap(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        KeyboardShortcutMapper.unmap(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
        KeyboardShortcutMapper.unmap(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        KeyboardShortcutMapper.unmap(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        super.clean();
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
    public Boolean onClose() {
        return validateUnsavedChanges();
    }

    private void onPlayTitleChanged(final ObservableValue<? extends String> value, final String oldPropertyValue, final String newPropertyValue) {
        playService.updatePlayTitle(playModel.getUUID(), playModel.title.get());
    }

    private void onPlayUpdated(Object sender, Play play) {
        playModelConverter.update(playModel, play);
    }

    private void onSaveToolbarButtonAction(Object sender, Object eventArgs) {
        savePlay();
    }

    private void savePlay() {
        playService.savePlay(playModel.getUUID());
    }

    private void onCloseToolbarButtonAction(Object sender, Object eventArgs) {
        closePlay();
    }

    private void closePlay() {
        onViewCloseRequested.fire(this, new ViewFlowRequestEventArgs());
    }

    private void onExportToolbarButtonAction(Object sender, Object eventArgs) {
        //TODO
    }

    private void onUndoToolbarButtonAction(Object sender, Object eventArgs) {
        undo();
    }

    private void undo() {
        playService.undo(playModel.getUUID());
    }

    private void onRedoToolbarButtonAction(Object sender, Object eventArgs) {
        redo();
    }

    private void redo() {
        playService.redo(playModel.getUUID());
    }

    private Boolean validateUnsavedChanges() {
        if (!playService.isPlayDirty(playModel.getUUID())) {
            return true;
        }
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
        return result.get().getButtonData() != ButtonBar.ButtonData.CANCEL_CLOSE;
    }

    private void discardChanges() {
        playService.discardChanges(playModel.getUUID());
    }

}
