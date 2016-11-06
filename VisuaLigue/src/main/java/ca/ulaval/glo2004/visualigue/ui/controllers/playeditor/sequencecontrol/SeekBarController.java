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
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javax.inject.Inject;

public class SeekBarController extends ControllerBase {

    public EventHandler<Integer> onTimeChanged = new EventHandler();
    public EventHandler onSeekThumbPressed = new EventHandler();

    @FXML private HBox keyframeHBox;
    @FXML private ExtendedScrollPane keyframeScrollPane;
    @FXML private Button seekBarThumb;
    @Inject private PlayService playService;

    private PlayModel playModel;
    private SceneController sceneController;
    private List<View> keyPoints = new ArrayList();
    private Integer time = 0;
    private Double dragStartX;
    private Double dragStartThumbLocationX;
    private Animator animator;
    private ChangeListener<Number> onNumberOfKeyPointsChanged = this::onNumberOfKeyPointsChanged;
    private BiConsumer<Object, Integer> onUndoRedo = this::onUndoRedo;

    public void init(PlayModel playModel, SceneController sceneController) {
        this.playModel = playModel;
        this.sceneController = sceneController;
        playModel.numberOfKeyPoints.addListener(onNumberOfKeyPointsChanged);
        playService.onUndo.addHandler(onUndoRedo);
        playService.onRedo.addHandler(onUndoRedo);
        updateKeyPoints();
        move(0);
    }

    @Override
    public void clean() {
        playService.onUndo.removeHandler(onUndoRedo);
        playService.onRedo.removeHandler(onUndoRedo);
    }

    @FXML
    protected void onNewKeyPointButtonAction(ActionEvent e) {
        playService.addKeypoint(playModel.getUUID(), time);
    }

    private void onUndoRedo(Object sender, Integer time) {
        setTimeDelayed(time);
    }

    private void onNumberOfKeyPointsChanged(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        updateKeyPoints();
        setTimeDelayed(playModel.timelineLength.get());
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
        move(index * playModel.keyPointInterval.get());
    }

    public void move(Integer time) {
        move(time, false, false, 0);
    }

    public void move(Integer time, Boolean snapToKeyPoint, Boolean smooth, Integer smoothingDuration) {
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

    public Integer getTime() {
        return time;
    }

    private void setTimeDelayed(Integer time) {
        TimerTask setTimeDelayedTask = new TimerTask() {
            @Override
            public void run() {
                setTime(time);
            }
        };
        Timer timer = new Timer();
        timer.schedule(setTimeDelayedTask, 100);
    }

    private void setTime(Integer time) {
        Integer length = playModel.timelineLength.get();
        if (time > length) {
            time = length;
        } else if (time < 0) {
            time = 0;
        }
        setSeekThumbLocationFromTime(time);
        sceneController.update(time);
        onTimeChanged.fire(this, time);
    }

    public Integer getRemainingTime() {
        return playModel.timelineLength.get() - time;
    }

    public void goToClosestKeyPoint(Boolean smooth, Integer smoothingDuration) {
        move(time, true, smooth, smoothingDuration);
    }

    public Integer getClosestKeyPointTime(Integer time) {
        return (int) Math.round(time / (double) playModel.keyPointInterval.get()) * playModel.keyPointInterval.get();
    }

    public void goToBeginning(Boolean smooth, Integer smoothingDuration) {
        move(0, false, smooth, smoothingDuration);
    }

    public void goToEnd(Boolean smooth, Integer smoothingDuration) {
        move(playModel.timelineLength.get(), false, smooth, smoothingDuration);
    }

    public void goToNextKeyPoint(Boolean smooth, Integer smoothingDuration) {
        Integer nextKeyPointTime = getNextKeyPointTime();
        if (nextKeyPointTime <= playModel.timelineLength.get()) {
            move(nextKeyPointTime, false, smooth, smoothingDuration);
        }
    }

    public Integer getNextKeyPointTime() {
        return MathUtils.roundDown(time + (int) (playModel.keyPointInterval.get() * 1.1), playModel.keyPointInterval.get());
    }

    public void goToPreviousKeyPoint(Boolean smooth, Integer smoothingDuration) {
        Integer previousKeyPointTime = getPreviousKeyPointTime();
        if (previousKeyPointTime >= 0) {
            move(previousKeyPointTime, false, smooth, smoothingDuration);
        }
    }

    public Integer getPreviousKeyPointTime() {
        return MathUtils.roundUp(time - (int) (playModel.keyPointInterval.get() * 1.1), playModel.keyPointInterval.get());
    }

    private Integer getTimeFromSeekThumbLocation() {
        return (int) (playModel.timelineLength.get() * (getSeekThumbLocation() / (keyframeHBox.getWidth() - seekBarThumb.getWidth())));
    }

    public void setSeekThumbLocationFromTime(Integer time) {
        this.time = time;
        Double margin = (time / (double) playModel.timelineLength.get()) * (keyframeHBox.getWidth() - seekBarThumb.getWidth());
        setSeekThumbLocationFromMargin(margin);
        keyframeScrollPane.ensureVisible(seekBarThumb);
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
