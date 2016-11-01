package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedMenuItem;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.converters.FrameModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
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
    private static final Integer DEFAULT_FIXED_ADVANCE_PERIOD = 500;
    @FXML private ExtendedButton realTimeButton;
    @FXML private Button playButton;
    @FXML private Button pauseButton;
    @FXML private SeekBarController seekBarController;
    @FXML private Label fixedRewindPeriodLabel;
    @FXML private Label fixedForwardPeriodLabel;
    @Inject private FrameModelConverter frameModelConverter;
    @Inject private PlayService playService;
    private PlayModel playModel;
    private SceneController sceneController;
    private Integer time = 0;
    private Integer playLength;
    private PlaySpeed playSpeed = PlaySpeed.STILL_SPEED;
    private PlayDirection playDirection = PlayDirection.NONE;
    private Integer fixedForwardPeriod;
    private Integer fixedRewindPeriod;
    private final Timer timer = new Timer();
    private final DecimalFormat fixedAdvancePeriodDecimalFormat = new DecimalFormat(".#");

    public void init(PlayModel playModel, SceneController sceneController) {
        this.playModel = playModel;
        this.sceneController = sceneController;
        seekBarController.onTimeChanged.setHandler(this::onSeekBarTimeChanged);
        seekBarController.init(playModel);
        super.addChild(seekBarController);
        setFixedForwardPeriod(DEFAULT_FIXED_ADVANCE_PERIOD);
        setFixedRewindPeriod(DEFAULT_FIXED_ADVANCE_PERIOD);
        stop();
    }

    @FXML
    protected void onRealTimeButtonAction(ActionEvent e) {
        realTimeButton.setSelected(!realTimeButton.isSelected());
        if (realTimeButton.isSelected()) {
            sceneController.enterRealTimeMode();
        } else {
            sceneController.enterFrameByFrameMode();
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
        setFixedForwardPeriod(Integer.parseInt((String) menuItem.getCustomData()));
    }

    private void setFixedForwardPeriod(Integer fixedForwardPeriod) {
        this.fixedForwardPeriod = fixedForwardPeriod;
        fixedForwardPeriodLabel.setText(fixedAdvancePeriodDecimalFormat.format(this.fixedForwardPeriod / 1000.0));
    }

    @FXML
    protected void onChangeFixedRewindPeriodMenuItemAction(ActionEvent e) {
        ExtendedMenuItem menuItem = (ExtendedMenuItem) e.getSource();
        setFixedRewindPeriod(Integer.parseInt((String) menuItem.getCustomData()));
    }

    private void setFixedRewindPeriod(Integer fixedRewindPeriod) {
        this.fixedRewindPeriod = fixedRewindPeriod;
        fixedRewindPeriodLabel.setText(fixedAdvancePeriodDecimalFormat.format(this.fixedRewindPeriod / 1000.0));
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
        playLength = playService.getPlayLength(playModel.getUUID());
        if (time + period >= 0 && time + period <= playLength) {
            time = time + period;
        } else if ((time + period > playLength && playDirection == PlayDirection.FORWARD) || (time + period < 0 && playDirection == PlayDirection.REVERSE)) {
            time = playLength;
            stop();
        }
        seekBarController.setTime(time, false);
    }

    private void stop() {
        timer.cancel();
        playDirection = PlayDirection.NONE;
        playSpeed = PlaySpeed.STILL_SPEED;
        FXUtils.setDisplay(playButton, true);
        FXUtils.setDisplay(pauseButton, false);
    }

    private void onSeekBarTimeChanged(Object sender, Integer time) {
        updateFrame(time);
    }

    private void updateFrame(Integer time) {
        frameModelConverter.update(playModel, sceneController.getFrameModel(), playService.getFrame(playModel.getUUID(), time));
    }
}
