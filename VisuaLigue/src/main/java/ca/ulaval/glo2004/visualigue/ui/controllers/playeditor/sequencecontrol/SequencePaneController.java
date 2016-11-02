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
import javafx.scene.control.Label;
import javax.inject.Inject;

public class SequencePaneController extends ControllerBase {

    private static final Integer AUTO_ADVANCE_PERIOD = 15;
    private static final Integer DEFAULT_FIXED_ADVANCE_PERIOD = 500;
    @FXML private ExtendedButton realTimeButton;
    @FXML private ExtendedButton playButton;
    @FXML private ExtendedButton pauseButton;
    @FXML private ExtendedButton fixedRewindButton;
    @FXML private ExtendedButton fixedForwardButton;
    @FXML private ExtendedButton previousKeyPointButton;
    @FXML private ExtendedButton nextKeyPointButton;
    @FXML private ExtendedButton rewindButton;
    @FXML private ExtendedButton fastForwardButton;
    @FXML private SeekBarController seekBarController;
    @FXML private Label fixedRewindPeriodLabel;
    @FXML private Label fixedForwardPeriodLabel;
    @Inject private FrameModelConverter frameModelConverter;
    @Inject private PlayService playService;
    private PlayModel playModel;
    private SceneController sceneController;
    private PlaySpeed playSpeed = PlaySpeed.STILL_SPEED;
    private Integer fixedForwardPeriod;
    private Integer fixedRewindPeriod;
    private Timer timer = new Timer();
    private final DecimalFormat fixedAdvancePeriodDecimalFormat = new DecimalFormat(".#");

    public void init(PlayModel playModel, SceneController sceneController) {
        this.playModel = playModel;
        this.sceneController = sceneController;
        seekBarController.onTimeChanged.setHandler(this::onSeekBarTimeChanged);
        seekBarController.onSeekThumbPressed.setHandler(this::onSeekBarThumbPressed);
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
        autoAdvance(PlaySpeed.NORMAL_SPEED);
    }

    @FXML
    protected void onPauseButtonAction(ActionEvent e) {
        stop();
    }

    @FXML
    protected void onNextKeyPointButtonAction(ActionEvent e) {
        stop();
        seekBarController.goToNextKeyPoint(false, 0.0);
    }

    @FXML
    protected void onPreviousKeyPointButtonAction(ActionEvent e) {
        stop();
        seekBarController.goToPreviousKeyPoint(false, 0.0);
    }

    @FXML
    protected void onFastForwardButtonAction(ActionEvent e) {
        if (playSpeed.getMultiplier() < PlaySpeed.DOUBLE_SPEED.getMultiplier()) {
            autoAdvance(PlaySpeed.DOUBLE_SPEED);
        } else if (!playSpeed.isMaxSpeed()) {
            autoAdvance(playSpeed.nextSpeed());
        }
    }

    @FXML
    protected void onRewindButtonAction(ActionEvent e) {
        if (playSpeed.getMultiplier() > PlaySpeed.NORMAL_REVERSE_SPEED.getMultiplier()) {
            autoAdvance(PlaySpeed.NORMAL_REVERSE_SPEED);
        } else if (!playSpeed.isMinSpeed()) {
            autoAdvance(playSpeed.previousSpeed());
        }
    }

    @FXML
    protected void onFixedForwardButtonAction(ActionEvent e) {
        stop();
        seekBarController.move(seekBarController.getTime() + fixedRewindPeriod, false);
    }

    @FXML
    protected void onFixedRewindButtonAction(ActionEvent e) {
        stop();
        seekBarController.move(seekBarController.getTime() - fixedRewindPeriod, false);
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

    private void autoAdvance(PlaySpeed playSpeed) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                autoAdvanceNext(AUTO_ADVANCE_PERIOD * SequencePaneController.this.playSpeed.getMultiplier());
            }
        };
        this.playSpeed = playSpeed;
        updateControlButtonStates();
        timer.cancel();
        timer = new Timer();
        timer.schedule(timerTask, 0, AUTO_ADVANCE_PERIOD);
    }

    private void autoAdvanceNext(Integer period) {
        Integer time = seekBarController.getTime();
        time += period;
        seekBarController.move(time, false);
    }

    private void stop() {
        timer.cancel();
        playSpeed = PlaySpeed.STILL_SPEED;
        updateControlButtonStates();
    }

    private void onSeekBarThumbPressed(Object sender) {
        stop();
    }

    private void onSeekBarTimeChanged(Object sender, Integer time) {
        updateFrame(time);
        updateControlButtonStates();
        Integer playLength = playService.getPlayLength(playModel.getUUID());
        if (time.equals(playLength) && playSpeed.isForward()) {
            stop();
        } else if (time.equals(0) && playSpeed.isBackward()) {
            stop();
        }
    }

    private void updateFrame(Integer time) {
        frameModelConverter.update(playService.getFrame(playModel.getUUID(), time), sceneController.getFrameModel(), playModel);
    }

    private void updateControlButtonStates() {
        Integer playLength = playService.getPlayLength(playModel.getUUID());
        FXUtils.setDisplay(playButton, playSpeed == PlaySpeed.STILL_SPEED);
        FXUtils.setDisplay(pauseButton, playSpeed != PlaySpeed.STILL_SPEED);
        playButton.setDisable(seekBarController.getTime() >= playLength);
        fixedRewindButton.setDisable(seekBarController.getTime() <= 0);
        fixedForwardButton.setDisable(seekBarController.getTime() >= playLength);
        previousKeyPointButton.setDisable(seekBarController.getTime() <= 0);
        nextKeyPointButton.setDisable(seekBarController.getTime() >= playLength);
        rewindButton.setDisable(seekBarController.getTime() <= 0);
        rewindButton.setSelected(playSpeed.getMultiplier() <= PlaySpeed.NORMAL_REVERSE_SPEED.getMultiplier());
        fastForwardButton.setDisable(seekBarController.getTime() >= playLength);
        fastForwardButton.setSelected(playSpeed.getMultiplier() > PlaySpeed.NORMAL_SPEED.getMultiplier());
    }
}
