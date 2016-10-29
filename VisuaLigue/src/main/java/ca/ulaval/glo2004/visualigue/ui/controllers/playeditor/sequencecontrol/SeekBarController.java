package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedScrollPane;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javax.inject.Inject;

public class SeekBarController extends ControllerBase {

    private static final Integer KEY_POINT_INTERVAL = 1000;
    public EventHandler<Integer> onTimeChanged = new EventHandler();

    @FXML private GridPane keyframeGridPane;
    @FXML private ExtendedScrollPane keyframeScrollPane;
    @FXML private Button seekBarThumb;
    @Inject private PlayService playService;

    private PlayModel playModel;
    private List<View> keyPoints = new ArrayList();
    private Integer time = 0;
    private Integer playLength;
    private Double dragStartX;

    public void init(PlayModel playModel) {
        this.playModel = playModel;
        updateKeyPoints();
    }

    @FXML
    protected void onNewFrameButtonAction(ActionEvent e) {
        playService.extendPlayLength(playModel.getUUID());
        updateKeyPoints();
        changeTime(time + KEY_POINT_INTERVAL, true);
    }

    private void updateKeyPoints() {
        playLength = playService.getDefinedPlayLength(playModel.getUUID());
        Integer numberOfKeyPoints = (int) Math.ceil(playLength / KEY_POINT_INTERVAL);
        if (numberOfKeyPoints > keyPoints.size()) {
            for (Integer index = numberOfKeyPoints; index <= numberOfKeyPoints; index++) {
                addKeyPoint();
            }
        } else if (numberOfKeyPoints < keyPoints.size()) {
            for (Integer index = keyPoints.size(); index > numberOfKeyPoints; index--) {
                removeKeyPoint();
            }
        }
    }

    private void addKeyPoint() {
        View view = InjectableFXMLLoader.loadView(SeekBarKeyPointController.VIEW_NAME);
        SeekBarKeyPointController controller = (SeekBarKeyPointController) view.getController();
        controller.init(keyPoints.size());
        controller.onClick.setHandler(this::onKeyPointClicked);
        keyframeGridPane.getChildren().add(view.getRoot());
        GridPane.setColumnIndex(keyframeGridPane, keyPoints.size());
        keyPoints.add(view);
    }

    private void removeKeyPoint() {
        View lastkeyPoint = keyPoints.get(keyPoints.size() - 1);
        keyframeGridPane.getChildren().remove(lastkeyPoint.getRoot());
        keyPoints.remove(lastkeyPoint);
    }

    private void onKeyPointClicked(Object sender, Integer index) {
        changeTime(index * KEY_POINT_INTERVAL, true);
    }

    public Integer getTime() {
        return time;
    }

    public void goToNextKeyPoint() {
        Integer nextKeyPointTime = (int) Math.ceil(time / (double) KEY_POINT_INTERVAL) * KEY_POINT_INTERVAL;
        if (nextKeyPointTime <= playLength) {
            changeTime(nextKeyPointTime, false);
        }
    }

    public void goToPreviousKeyPoint() {
        Integer previousKeyPointTime = (int) Math.floor(time / (double) KEY_POINT_INTERVAL) * KEY_POINT_INTERVAL;
        if (previousKeyPointTime > 0) {
            changeTime(previousKeyPointTime, false);
        }
    }

    public void changeTime(Integer time, Boolean snapToKeyPoint) {
        if (snapToKeyPoint) {
            time = getNearestKeyPointTime(time);
        }
        setTime(time);
        onTimeChanged.fire(this, time);
    }

    private Integer getNearestKeyPointTime(Integer time) {
        return (int) Math.round(time / (double) KEY_POINT_INTERVAL) * KEY_POINT_INTERVAL;
    }

    public void setTime(Integer time) {
        this.time = time;
        seekBarThumb.setTranslateX(getPointerLocationFromTime());
        keyframeScrollPane.ensureVisible(seekBarThumb);
    }

    private Double getPointerLocationFromTime() {
        return (time / playService.getDefinedPlayLength(playModel.getUUID())) * keyframeGridPane.getWidth() - seekBarThumb.getWidth() / 2;
    }

    private Integer getTimeFromPointerLocation() {
        return (int) ((playService.getDefinedPlayLength(playModel.getUUID()) * (seekBarThumb.getTranslateX() + seekBarThumb.getWidth() / 2)) / keyframeGridPane.getWidth());
    }

    @FXML
    protected void onMousePressed(MouseEvent e) {
        dragStartX = e.getSceneX();
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        seekBarThumb.setTranslateX(seekBarThumb.getTranslateX() + e.getSceneX() - dragStartX);
        dragStartX = e.getSceneX();
        changeTime(getTimeFromPointerLocation(), false);
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        changeTime(getTimeFromPointerLocation(), true);
    }

}
