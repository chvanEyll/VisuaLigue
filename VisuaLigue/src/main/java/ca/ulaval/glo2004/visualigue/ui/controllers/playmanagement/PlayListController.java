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
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javax.inject.Inject;
import javax.swing.SortOrder;

public class PlayListController extends ControllerBase {

    public EventHandler<PlayModel> onPlaySelected = new EventHandler();
    @Inject private PlayService playService;
    @Inject private PlayModelConverter playModelConverter;
    @FXML private TilePane tilePane;
    private Map<PlayModel, View> playViews = new HashMap();
    private Map<String, PlayModel> playModels = new HashMap();
    private BiConsumer<Object, Play> onPlayCreated = this::onPlayCreated;
    private BiConsumer<Object, Play> onPlayUpdated = this::onPlayUpdated;
    private BiConsumer<Object, Play> onPlayDeleted = this::onPlayDeleted;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playService.onPlayCreated.addHandler(onPlayCreated);
        playService.onPlayUpdated.addHandler(onPlayUpdated);
        playService.onPlayDeleted.addHandler(onPlayDeleted);
        fillPlayList();
    }

    @Override
    public void clean() {
        playService.onPlayCreated.removeHandler(onPlayCreated);
        playService.onPlayUpdated.removeHandler(onPlayUpdated);
        playService.onPlayDeleted.removeHandler(onPlayDeleted);
        super.clean();
    }

    private void onPlayCreated(Object sender, Play play) {
        PlayModel model = playModelConverter.convert(play);
        addPlayItem(model);
    }

    private void onPlayUpdated(Object sender, Play play) {
        PlayModel model = playModels.get(play.getUUID());
        playModelConverter.update(model, play);
    }

    private void onPlayDeleted(Object sender, Play play) {
        PlayModel model = playModels.get(play.getUUID());
        removePlayItem(model);
    }

    private void fillPlayList() {
        List<Play> plays = playService.getPlays(Play::getTitle, SortOrder.ASCENDING);
        plays.forEach(play -> {
            addPlayItem(playModelConverter.convert(play));
        });
    }

    private void addPlayItem(PlayModel model) {
        View view = InjectableFXMLLoader.loadView(PlayListItemController.VIEW_NAME);
        PlayListItemController controller = (PlayListItemController) view.getController();
        controller.init(model);
        controller.onClick.setHandler(this::onItemClicked);
        controller.onDeleteButtonClicked.setHandler(this::onItemDeleteButtonClicked);
        playViews.put(model, view);
        playModels.put(model.getUUID(), model);
        tilePane.getChildren().add(view.getRoot());
    }

    private void removePlayItem(PlayModel model) {
        tilePane.getChildren().remove(playViews.get(model).getRoot());
        playViews.remove(model);
        playModels.remove(model.getUUID());
    }

    private void onItemClicked(Object sender, PlayModel model) {
        onPlaySelected.fire(this, model);
    }

    private void onItemDeleteButtonClicked(Object sender, PlayModel model) {
        playService.deletePlay(model.getUUID());
    }

    @FXML
    protected void onNewPlayButtonClicked(MouseEvent e) {
        View view = InjectableFXMLLoader.loadView(PlayCreationController.VIEW_NAME);
        onViewAppendRequested.fire(this, new ViewFlowRequestEventArgs(view));
    }
}
