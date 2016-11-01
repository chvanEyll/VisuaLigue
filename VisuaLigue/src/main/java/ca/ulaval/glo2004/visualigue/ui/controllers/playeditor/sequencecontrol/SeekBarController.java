package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
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

    @FXML private HBox keyframeHBox;
    @FXML private ExtendedScrollPane keyframeScrollPane;
    @FXML private Button seekBarThumb;
    @Inject private PlayService playService;

    private PlayModel playModel;
    private List<View> keyPoints = new ArrayList();
    private Integer time = 0;
    private Double dragStartX;

    public void init(PlayModel playModel) {
        this.playModel = playModel;
        updateKeyPoints();
        setTime(0, false);
    }

    @FXML
    protected void onNewFrameButtonAction(ActionEvent e) {
        updateKeyPoints();
        setTime(time + KEY_POINT_INTERVAL, true);
    }

    private void updateKeyPoints() {
        Integer numberOfKeyPoints = (int) Math.ceil(getPlayLength() / KEY_POINT_INTERVAL);
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

    private Integer getPlayLength() {
        return playService.getPlayLength(playModel.getUUID());
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
        setTime(index * KEY_POINT_INTERVAL, true);
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time, Boolean snapToKeyPoint) {
        Integer playLength = playService.getPlayLength(playModel.getUUID());
        if (time > playLength) {
            time = playLength;
        } else if (time < 0) {
            time = 0;
        }
        if (snapToKeyPoint) {
            time = getNearestKeyPointTime(time);
        }
        setSeekThumbLocation(time);
        onTimeChanged.fire(this, time);
    }

    public void goToNextKeyPoint() {
        Integer nextKeyPointTime = MathUtils.roundDown(time + KEY_POINT_INTERVAL, KEY_POINT_INTERVAL);
        if (nextKeyPointTime <= getPlayLength()) {
            setTime(nextKeyPointTime, false);
        }
    }

    public void goToPreviousKeyPoint() {
        Integer previousKeyPointTime = MathUtils.roundUp(time - KEY_POINT_INTERVAL, KEY_POINT_INTERVAL);
        if (previousKeyPointTime >= 0) {
            setTime(previousKeyPointTime, false);
        }
    }

    private Integer getNearestKeyPointTime(Integer time) {
        return (int) Math.round(time / (double) KEY_POINT_INTERVAL) * KEY_POINT_INTERVAL;
    }

    public void setSeekThumbLocation(Integer time) {
        this.time = time;
        setSeekThumbMargin(getSeekThumbLocationFromTime());
        keyframeScrollPane.ensureVisible(seekBarThumb);
    }

    private Double getSeekThumbLocationFromTime() {
        return (time / (double) getPlayLength()) * (keyframeHBox.getWidth() - seekBarThumb.getWidth());
    }

    private Integer getTimeFromSeekThumbLocation() {
        return (int) (getPlayLength() * (getSeekThumbMargin() / (keyframeHBox.getWidth() - seekBarThumb.getWidth())));
    }

    private void setSeekThumbMargin(Double margin) {
        if (margin < 0) {
            margin = 0.0;
        } else if (margin > keyframeHBox.getWidth() - seekBarThumb.getWidth()) {
            margin = keyframeHBox.getWidth() - seekBarThumb.getWidth();
        }
        StackPane.setMargin(seekBarThumb, new Insets(0, 0, 0, margin));
    }

    private Double getSeekThumbMargin() {
        return StackPane.getMargin(seekBarThumb).getLeft();
    }

    @FXML
    protected void onMousePressed(MouseEvent e) {
        dragStartX = e.getSceneX();
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        setSeekThumbMargin(getSeekThumbMargin() + e.getSceneX() - dragStartX);
        dragStartX = e.getSceneX();
        setTime(getTimeFromSeekThumbLocation(), false);
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        setTime(getTimeFromSeekThumbLocation(), false);
    }

}
