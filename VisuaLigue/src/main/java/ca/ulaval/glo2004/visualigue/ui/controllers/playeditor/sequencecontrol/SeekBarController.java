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
import javafx.beans.value.ChangeListener;
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
    private ChangeListener<Number> onNumberOfKeyPointsChanged = this::onNumberOfKeyPointsChanged;
    private BiConsumer<Object, Long> onUndoRedo = this::onUndoRedo;

    public void init(PlayModel playModel, SceneController sceneController) {
        this.playModel = playModel;
        this.sceneController = sceneController;
        playModel.numberOfKeyPoints.addListener(onNumberOfKeyPointsChanged);
        playService.onUndo.addHandler(onUndoRedo);
        playService.onRedo.addHandler(onUndoRedo);
        updateKeyPoints();
        move(0L);
    }

    @Override
    public void clean() {
        playService.onUndo.removeHandler(onUndoRedo);
        playService.onRedo.removeHandler(onUndoRedo);
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
        keyframeHBox.getChildren().add(view.getRoot());
        keyPoints.add(view);
    }

    private void removeKeyPoint() {
        View lastkeyPoint = keyPoints.get(keyPoints.size() - 1);
        keyframeHBox.getChildren().remove(lastkeyPoint.getRoot());
        keyPoints.remove(lastkeyPoint);
    }

    private void onKeyPointClicked(Object sender, Integer index) {
        move((long) index * playModel.keyPointInterval.get());
    }

    public void move(Long time) {
        move(time, false, false, 0L, 0L);
    }

    public void move(Long time, Boolean snapToKeyPoint, Boolean smooth, Long smoothingDuration, Long delay) {
        cancelAnimation();
        if (snapToKeyPoint) {
            time = getClosestKeyPointTime(time);
        }
        if (smooth && !time.equals(this.time)) {
            animator = Animation.method(this::setTime).from(getTime()).to(time).duration(smoothingDuration).delay(delay).group(this).first().easeOutExp();
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
        move(time, true, smooth, smoothingDuration, 0L);
    }

    public Long getClosestKeyPointTime(Long time) {
        return (long) Math.round(time / (double) playModel.keyPointInterval.get()) * playModel.keyPointInterval.get();
    }

    public void goToBeginning(Boolean smooth, Long smoothingDuration) {
        move(0L, false, smooth, smoothingDuration, 0L);
    }

    public void goToEnd(Boolean smooth, Long smoothingDuration, Long delay) {
        move(playModel.timelineLength.get(), false, smooth, smoothingDuration, delay);
    }

    public void goToNextKeyPoint(Boolean smooth, Long smoothingDuration) {
        Long nextKeyPointTime = getNextKeyPointTime();
        if (nextKeyPointTime <= playModel.timelineLength.get()) {
            move(nextKeyPointTime, false, smooth, smoothingDuration, 0L);
        }
    }

    public Long getNextKeyPointTime() {
        return MathUtils.roundDown(time + (long) (playModel.keyPointInterval.get() * 1.1), playModel.keyPointInterval.get());
    }

    public void goToPreviousKeyPoint(Boolean smooth, Long smoothingDuration) {
        Long previousKeyPointTime = getPreviousKeyPointTime();
        if (previousKeyPointTime >= 0L) {
            move(previousKeyPointTime, false, smooth, smoothingDuration, 0L);
        }
    }

    public Long getPreviousKeyPointTime() {
        return MathUtils.roundUp(time - (long) (playModel.keyPointInterval.get() * 1.1), playModel.keyPointInterval.get());
    }

    private Long getTimeFromSeekThumbLocation() {
        return (long) (playModel.timelineLength.get() * (getSeekThumbLocation() / (keyframeHBox.getWidth() - seekBarThumb.getWidth())));
    }

    public void setSeekThumbLocationFromTime(Long time) {
        this.time = time;
        Double margin = (time / (double) playModel.timelineLength.get()) * (keyframeHBox.getWidth() - seekBarThumb.getWidth());
        setSeekThumbLocationFromMargin(margin);
        rootNode.ensureVisible(seekBarThumb);
    }

    private void setSeekThumbLocationFromMargin(Double margin) {
        if (margin < 0) {
            margin = 0.0;
        } else if (margin > keyframeHBox.getWidth() - seekBarThumb.getWidth()) {
            margin = keyframeHBox.getWidth() - seekBarThumb.getWidth();
        }
        StackPane.setMargin(seekBarThumb, new Insets(0, 0, 0, margin));
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
        setSeekThumbLocationFromMargin(dragStartThumbLocationX + e.getSceneX() - dragStartX);
        move(getTimeFromSeekThumbLocation());
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        move(getTimeFromSeekThumbLocation());
    }
}
