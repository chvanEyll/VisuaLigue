package ca.ulaval.glo2004.visualigue.ui.controllers.playmanagement;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.ViewFlowRequestEventArgs;
import ca.ulaval.glo2004.visualigue.ui.controllers.playcreation.PlayCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.PlayModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;
import javax.swing.SortOrder;

public class PlayListController extends ControllerBase {

    @Inject private PlayService playService;
    @Inject private PlayModelConverter playModelConverter;
    @FXML private TilePane tilePane;
    @FXML private Label emptyNoticeLabel;
    private List<PlayModel> models = new ArrayList();
    public EventHandler<PlayModel> onPlaySelected = new EventHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playService.onPlayCreated.addHandler(this::onPlayChanged);
        playService.onPlayTitleUpdated.addHandler(this::onPlayChanged);
        playService.onPlayDeleted.addHandler(this::onPlayChanged);
        fillPlayList();
    }

    @Override
    public void clean() {
        playService.onPlayCreated.removeHandler(this::onPlayChanged);
        playService.onPlayTitleUpdated.removeHandler(this::onPlayChanged);
        playService.onPlayDeleted.removeHandler(this::onPlayChanged);
    }

    private void onPlayChanged(Object sender, Play play) {
        Platform.runLater(() -> fillPlayList());
    }

    private void fillPlayList() {
        tilePane.getChildren().clear();
        List<Play> plays = playService.getPlays(Play::getTitle, SortOrder.ASCENDING);
        FXUtils.setDisplay(emptyNoticeLabel, plays.isEmpty());
        plays.forEach(play -> {
            initPlayItem(playModelConverter.convert(play));
        });
    }

    private void initPlayItem(PlayModel model) {
        View view = InjectableFXMLLoader.loadView(PlayListItemController.VIEW_NAME);
        PlayListItemController controller = (PlayListItemController) view.getController();
        controller.init(model);
        controller.onClick.setHandler(this::onItemClicked);
        controller.onDeleteButtonClicked.setHandler(this::onItemDeleteButtonClicked);
        tilePane.getChildren().add(view.getRoot());
        models.add(model);
    }

    private void onItemClicked(Object sender, PlayModel model) {
        onPlaySelected.fire(this, model);
    }

    private void onItemDeleteButtonClicked(Object sender, PlayModel model) {
        playService.deletePlay(model.getUUID());
        int tileIndex = models.indexOf(model);
        tilePane.getChildren().remove(tileIndex);
        models.remove(model);
    }

    @FXML
    protected void onNewPlayButtonClicked(MouseEvent e) {
        View view = InjectableFXMLLoader.loadView(PlayCreationController.VIEW_NAME);
        onViewAppendRequested.fire(this, new ViewFlowRequestEventArgs(view));
    }
}
