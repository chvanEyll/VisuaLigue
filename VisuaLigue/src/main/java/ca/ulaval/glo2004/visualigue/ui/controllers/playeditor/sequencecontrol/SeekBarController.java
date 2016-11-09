package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.animation.Animation;
import ca.ulaval.glo2004.visualigue.ui.animation.Animator;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.math.MathUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javax.inject.Inject;

public class SeekBarController extends ControllerBase {

    public EventHandler<Long> onTimeChanged = new EventHandler();
    public EventHandler onSeekThumbPressed = new EventHandler();

    @FXML private HBox keyframeHBox;
    @FXML private ExtendedScrollPane rootNode;
    @FXML private Button seekBarThumb;
    @Inject private PlayService playService;

    private PlayModel playModel;
    private SceneController sceneController;
    private List<View> keyPoints = new ArrayList();
    private Long time = 0L;
    private Double dragStartX;
    private Double dragStartThumbLocationX;
    private Animator animator;
    private BiConsumer<Object, Long> onUndoRedo = this::onUndoRedo;
    private Boolean layoutReady = false;

    public void init(PlayModel playModel, SceneController sceneController) {
        this.playModel = playModel;
        this.sceneController = sceneController;
        keyframeHBox.widthProperty().addListener(this::keyframeHBoxWidthChanged);
        playModel.numberOfKeyPoints.addListener(this::onNumberOfKeyPointsChanged);
        playService.onUndo.addHandler(onUndoRedo);
        playService.onRedo.addHandler(onUndoRedo);
        updateKeyPoints();
        move(0L);
    }

    @Override
    public void clean() {
        playService.onUndo.removeHandler(onUndoRedo);
        playService.onRedo.removeHandler(onUndoRedo);
        super.clean();
    }

    private void onUndoRedo(Object sender, Long time) {
        move(time);
    }

    private void onNumberOfKeyPointsChanged(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        updateKeyPoints();
    }

    private void updateKeyPoints() {
        if (playModel.numberOfKeyPoints.get() > keyPoints.size()) {
            for (Integer i = keyPoints.size(); i < playModel.numberOfKeyPoints.get(); i++) {
                addKeyPoint();
            }
        } else if (playModel.numberOfKeyPoints.get() < keyPoints.size()) {
            for (Integer i = keyPoints.size(); i > playModel.numberOfKeyPoints.get(); i--) {
                removeKeyPoint();
            }
        }
    }

    private void addKeyPoint() {
        View view = InjectableFXMLLoader.loadView(SeekBarKeyPointController.VIEW_NAME);
        SeekBarKeyPointController controller = (SeekBarKeyPointController) view.getController();
        controller.init(keyPoints.size(), keyframeHBox);
        controller.onClick.setHandler(this::onKeyPointClicked);
        layoutReady = false;
        keyframeHBox.getChildren().add(view.getRoot());
        keyPoints.add(view);
    }

    private void removeKeyPoint() {
        View lastkeyPoint = keyPoints.get(keyPoints.size() - 1);
        layoutReady = false;
        keyframeHBox.getChildren().remove(lastkeyPoint.getRoot());
        keyPoints.remove(lastkeyPoint);
    }

    private void onKeyPointClicked(Object sender, Integer index) {
        move((long) index * playModel.keyPointInterval.get());
    }

    public void move(Long time) {
        move(time, false, false, 0L);
    }

    public void move(Long time, Boolean snapToKeyPoint, Boolean smooth, Long smoothingDuration) {
        cancelAnimation();
        if (snapToKeyPoint) {
            time = getClosestKeyPointTime(time);
        }
        if (smooth && !time.equals(this.time)) {
            animator = Animation.method(this::setTime).from(getTime()).to(time).duration(smoothingDuration).group(this).first().easeOutExp();
        } else {
            setTime(time);
        }
    }

    public Long getTime() {
        return time;
    }

    private void setTime(Long time) {
        Long length = playModel.timelineLength.get();
        if (time > length) {
            time = length;
        } else if (time < 0) {
            time = 0L;
        }
        setSeekThumbLocationFromTime(time);
        sceneController.update(time);
        onTimeChanged.fire(this, time);
    }

    public Long getRemainingTime() {
        return playModel.timelineLength.get() - time;
    }

    public void goToClosestKeyPoint(Boolean smooth, Long smoothingDuration) {
        move(time, true, smooth, smoothingDuration);
    }

    public Long getClosestKeyPointTime(Long time) {
        return (long) Math.round(time / (double) playModel.keyPointInterval.get()) * playModel.keyPointInterval.get();
    }

    public void goToBeginning(Boolean smooth, Long smoothingDuration) {
        move(0L, false, smooth, smoothingDuration);
    }

    public void goToEnd(Boolean smooth, Long smoothingDuration) {
        move(playModel.timelineLength.get(), false, smooth, smoothingDuration);
    }

    public void goToNextKeyPoint(Boolean smooth, Long smoothingDuration) {
        Long nextKeyPointTime = getNextKeyPointTime();
        if (nextKeyPointTime <= playModel.timelineLength.get()) {
            move(nextKeyPointTime, false, smooth, smoothingDuration);
        }
    }

    public Long getNextKeyPointTime() {
        return MathUtils.roundDown(time + (long) (playModel.keyPointInterval.get() * 1.1), playModel.keyPointInterval.get());
    }

    public void goToPreviousKeyPoint(Boolean smooth, Long smoothingDuration) {
        Long previousKeyPointTime = getPreviousKeyPointTime();
        if (previousKeyPointTime >= 0L) {
            move(previousKeyPointTime, false, smooth, smoothingDuration);
        }
    }

    public Long getPreviousKeyPointTime() {
        return MathUtils.roundUp(time - (long) (playModel.keyPointInterval.get() * 1.1), playModel.keyPointInterval.get());
    }

    public void setSeekThumbLocationFromTime(Long time) {
        this.time = time;
        if (!layoutReady) {
            return;
        }
        if (playModel.timelineLength.get() != 0) {
            Double margin = (time / (double) playModel.timelineLength.get()) * (keyframeHBox.getWidth() - seekBarThumb.getWidth());
            setSeekThumbLocationFromMargin(margin);
        } else {
            setSeekThumbLocationFromMargin(0.0);
        }
        rootNode.ensureVisible(seekBarThumb);
    }

    private void keyframeHBoxWidthChanged(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        layoutReady = true;
        setSeekThumbLocationFromTime(time);
    }

    private void setSeekThumbLocationFromMargin(Double margin) {
        if (margin < 0) {
            margin = 0.0;
        } else if (margin > keyframeHBox.getWidth() - seekBarThumb.getWidth()) {
            margin = keyframeHBox.getWidth() - seekBarThumb.getWidth();
        }
        Double finalMargin = margin;
        Platform.runLater(() -> {
            StackPane.setMargin(seekBarThumb, new Insets(0, 0, 0, finalMargin));
        });
    }

    private Double getSeekThumbLocation() {
        return StackPane.getMargin(seekBarThumb).getLeft();
    }

    private void cancelAnimation() {
        if (animator != null) {
            animator.cancel();
        }
    }

    @FXML
    protected void onMousePressed(MouseEvent e) {
        cancelAnimation();
        dragStartX = e.getSceneX();
        dragStartThumbLocationX = getSeekThumbLocation();
        onSeekThumbPressed.fire(this);
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        Double newMargin = dragStartThumbLocationX + e.getSceneX() - dragStartX;
        Long newTime = (long) (playModel.timelineLength.get() * (newMargin / (keyframeHBox.getWidth() - seekBarThumb.getWidth())));
        move(newTime);
    }
}
