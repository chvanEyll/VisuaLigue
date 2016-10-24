package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.services.sport.SportService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.converters.FrameModelConverter;
import ca.ulaval.glo2004.visualigue.ui.customcontrols.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.customcontrols.ExtendedMenuItem;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.inject.Inject;

public class SequencePaneController extends ControllerBase {

    private static final Integer AUTO_ADVANCE_PERIOD = 15;
    @FXML private ExtendedButton realTimeButton;
    @FXML private Button playButton;
    @FXML private Button pauseButton;
    @FXML private Button previousKeyPointButton;
    @FXML private Button nextKeyPointButton;
    @FXML private SeekBarController seekBarController;
    @FXML private Label fixedRewindPeriodLabel;
    @FXML private Label fixedForwardPeriodLabel;
    @Inject private FrameModelConverter frameModelConverter;
    @Inject private PlayService playService;
    @Inject private SportService sportService;
    private PlayModel playModel;
    private SceneController sceneController;
    private Integer time = 0;
    private Integer playLength;
    private PlaySpeed playSpeed = PlaySpeed.STILL_SPEED;
    private PlayDirection playDirection = PlayDirection.NONE;
    private Integer fixedForwardPeriod = 2000;
    private Integer fixedRewindPeriod = 2000;
    private final Timer timer = new Timer();
    private final DecimalFormat fixedAdvancePeriodDecimalFormat = new DecimalFormat("0.##");

    public void init(PlayModel playModel, SceneController sceneController) {
        this.playModel = playModel;
        this.sceneController = sceneController;
        seekBarController.init(playModel);
        seekBarController.onTimeChanged.addHandler(this::onSeekBarTimeChanged);
    }

    @FXML
    protected void onRealTimeButtonAction(ActionEvent e) {
        realTimeButton.setSelected(!realTimeButton.isSelected());
        if (realTimeButton.isSelected()) {
            sceneController.enterRealTimeCreationMode();
        } else {
            sceneController.enterFrameByFrameCreationMode();
        }
    }

    @FXML
    protected void onPlayButtonAction(ActionEvent e) {
        autoAdvance(PlayDirection.FORWARD, PlaySpeed.NORMAL_SPEED);
    }

    @FXML
    protected void onPauseButtonAction(ActionEvent e) {
        stop();
    }

    @FXML
    protected void onNextKeyPointButtonAction(ActionEvent e) {
        seekBarController.goToNextKeyPoint();
    }

    @FXML
    protected void onPreviousKeyPointButtonAction(ActionEvent e) {
        seekBarController.goToPreviousKeyPoint();
    }

    @FXML
    protected void onFastForwardButtonAction(ActionEvent e) {
        if (playDirection == PlayDirection.NONE) {
            autoAdvance(PlayDirection.FORWARD, PlaySpeed.DOUBLE_SPEED);
        } else if (!playSpeed.isMaxSpeed()) {
            playSpeed.nextSpeed();
        }
    }

    @FXML
    protected void onRewindButtonAction(ActionEvent e) {
        if (playDirection == PlayDirection.NONE) {
            autoAdvance(PlayDirection.REVERSE, PlaySpeed.NORMAL_SPEED);
        } else if (!playSpeed.isMaxSpeed()) {
            playSpeed.nextSpeed();
        }
    }

    @FXML
    protected void onFixedForwardButtonAction(ActionEvent e) {
        advanceTime(fixedForwardPeriod);
    }

    @FXML
    protected void onFixedRewindButtonAction(ActionEvent e) {
        advanceTime(-fixedRewindPeriod);
    }

    @FXML
    protected void onChangeFixedForwardPeriodMenuItemAction(ActionEvent e) {
        ExtendedMenuItem menuItem = (ExtendedMenuItem) e.getSource();
        fixedForwardPeriod = (Integer) menuItem.getCustomData();
        fixedForwardPeriodLabel.setText(fixedAdvancePeriodDecimalFormat.format(fixedForwardPeriod / 1000.0));
    }

    @FXML
    protected void onChangeFixedRewindPeriodMenuItemAction(ActionEvent e) {
        ExtendedMenuItem menuItem = (ExtendedMenuItem) e.getSource();
        fixedRewindPeriod = (Integer) menuItem.getCustomData();
        fixedRewindPeriodLabel.setText(fixedAdvancePeriodDecimalFormat.format(fixedRewindPeriod / 1000.0));
    }

    private void autoAdvance(PlayDirection playDirection, PlaySpeed playSpeed) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                advanceTime(AUTO_ADVANCE_PERIOD * SequencePaneController.this.playSpeed.getMultiplier());
            }
        };
        FXUtils.setDisplay(playButton, false);
        FXUtils.setDisplay(pauseButton, true);
        this.playDirection = playDirection;
        this.playSpeed = playSpeed;
        timer.schedule(timerTask, AUTO_ADVANCE_PERIOD, AUTO_ADVANCE_PERIOD);
    }

    private void advanceTime(Integer period) {
        playLength = playService.getDefinedPlayLength(playModel.getUUID());
        if (time + period >= 0 && time + period <= playLength) {
            time = time + period;
        } else if ((time + period > playLength && playDirection == PlayDirection.FORWARD) || (time + period < 0 && playDirection == PlayDirection.REVERSE)) {
            time = playLength;
            stop();
        }
        seekBarController.changeTime(time, false);
    }

    private void stop() {
        timer.cancel();
        playDirection = PlayDirection.NONE;
        playSpeed = PlaySpeed.STILL_SPEED;
        FXUtils.setDisplay(playButton, false);
        FXUtils.setDisplay(pauseButton, true);
    }

    private void onSeekBarTimeChanged(Object sender, Integer time) {
        updateFrame(time);
    }

    private void updateFrame(Integer time) {
        frameModelConverter.update(playModel, sceneController.getFrameModel(), playService.getFrame(playModel.getUUID(), time));
    }
}
