package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.animation.Animation;
import ca.ulaval.glo2004.visualigue.ui.animation.Animator;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.converters.FrameModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.math.MathUtils;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javax.inject.Inject;

public class SeekBarController extends ControllerBase {

    private static final Integer KEY_POINT_INTERVAL = 1000;
    public EventHandler<Integer> onTimeChanged = new EventHandler();
    public EventHandler onSeekThumbPressed = new EventHandler();

    @FXML private HBox keyframeHBox;
    @FXML private ExtendedScrollPane keyframeScrollPane;
    @FXML private Button seekBarThumb;
    @Inject private PlayService playService;
    @Inject private FrameModelConverter frameModelConverter;

    private PlayModel playModel;
    private SceneController sceneController;
    private List<View> keyPoints = new ArrayList();
    private Integer time = 0;
    private Double dragStartX;
    private Double dragStartThumbLocationX;
    private Animator animator;

    public void init(PlayModel playModel, SceneController sceneController) {
        this.playModel = playModel;
        this.sceneController = sceneController;
        updateKeyPoints();
        move(0);
    }

    @FXML
    protected void onNewFrameButtonAction(ActionEvent e) {
        updateKeyPoints();
        move(time + KEY_POINT_INTERVAL);
    }

    private void updateKeyPoints() {
        Integer numberOfKeyPoints = (int) Math.ceil(getLength() / KEY_POINT_INTERVAL);
        if (numberOfKeyPoints > keyPoints.size()) {
            for (Integer i = keyPoints.size(); i <= numberOfKeyPoints; i++) {
                addKeyPoint();
            }
        } else if (numberOfKeyPoints < keyPoints.size()) {
            for (Integer i = keyPoints.size(); i > numberOfKeyPoints; i--) {
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
        move(index * KEY_POINT_INTERVAL);
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

    private void setTime(Integer time) {
        Integer length = getLength();
        if (time > length) {
            time = length;
        } else if (time < 0) {
            time = 0;
        }
        setSeekThumbLocationFromTime(time);
        updateFrame(time);
        onTimeChanged.fire(this, time);
    }

    private void updateFrame(Integer time) {
        frameModelConverter.update(playService.getFrame(playModel.getUUID(), time), sceneController.getFrameModel(), playModel);
    }

    public Integer getLength() {
        return playService.getPlayLength(playModel.getUUID());
    }

    public Integer getRemainingTime() {
        return getLength() - time;
    }

    public Integer getClosestKeyPointTime(Integer time) {
        return (int) Math.round(time / (double) KEY_POINT_INTERVAL) * KEY_POINT_INTERVAL;
    }

    public void goToBeginning(Boolean smooth, Integer smoothingDuration) {
        move(0, false, true, smoothingDuration);
    }

    public void goToEnd(Boolean smooth, Integer smoothingDuration) {
        move(getLength(), false, true, smoothingDuration);
    }

    public void goToNextKeyPoint(Boolean smooth, Integer smoothingDuration) {
        Integer nextKeyPointTime = getNextKeyPointTime();
        if (nextKeyPointTime <= getLength()) {
            move(nextKeyPointTime, false, smooth, smoothingDuration);
        }
    }

    public Integer getNextKeyPointTime() {
        return MathUtils.roundDown(time + (int) (KEY_POINT_INTERVAL * 1.1), KEY_POINT_INTERVAL);
    }

    public void goToPreviousKeyPoint(Boolean smooth, Integer smoothingDuration) {
        Integer previousKeyPointTime = getPreviousKeyPointTime();
        if (previousKeyPointTime >= 0) {
            move(previousKeyPointTime, false, smooth, smoothingDuration);
        }
    }

    public Integer getPreviousKeyPointTime() {
        return MathUtils.roundUp(time - (int) (KEY_POINT_INTERVAL * 1.1), KEY_POINT_INTERVAL);
    }

    private Integer getTimeFromSeekThumbLocation() {
        return (int) (getLength() * (getSeekThumbLocation() / (keyframeHBox.getWidth() - seekBarThumb.getWidth())));
    }

    public void setSeekThumbLocationFromTime(Integer time) {
        this.time = time;
        Double margin = (time / (double) getLength()) * (keyframeHBox.getWidth() - seekBarThumb.getWidth());
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
