package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.services.settings.SettingsService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedButton;
import ca.ulaval.glo2004.visualigue.ui.controllers.common.ExtendedRadioMenuItem;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.DurationUtils;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;
import javax.inject.Inject;

public class SequencePaneController extends ControllerBase {

    private static final Integer LINEAR_AUTO_ADVANCE_PERIOD = 15;
    private static final Integer DEFAULT_FIXED_ADVANCE_PERIOD = 1000;
    private static final Long KEYPOINT_MOVE_ANIMATION_PERIOD = 250L;
    @FXML private ExtendedButton playButton;
    @FXML private ExtendedButton pauseButton;
    @FXML private ExtendedButton fixedRewindButton;
    @FXML private ExtendedButton fixedForwardButton;
    @FXML private ExtendedButton firstKeyPointButton;
    @FXML private ExtendedButton lastKeyPointButton;
    @FXML private ExtendedButton previousKeyPointButton;
    @FXML private ExtendedButton nextKeyPointButton;
    @FXML private ExtendedButton rewindButton;
    @FXML private ExtendedButton fastForwardButton;
    @FXML private ContextMenu fixedRewindContextMenu;
    @FXML private ContextMenu fixedForwardContextMenu;
    @FXML private ContextMenu playSpeedContextMenu;
    @FXML private SeekBarController seekBarController;
    @FXML private Label fixedRewindPeriodLabel;
    @FXML private Label fixedForwardPeriodLabel;
    @FXML private Label currentTimeLabel;
    @FXML private Label remainingTimeLabel;
    @Inject private PlayService playService;
    @Inject private SettingsService settingsService;
    private PlayModel playModel;
    private PlaySpeed playSpeed = new PlaySpeed();
    private Integer fixedForwardPeriod;
    private Integer fixedRewindPeriod;
    private Timer timer = new Timer();
    private final DecimalFormat fixedAdvancePeriodDecimalFormat = new DecimalFormat("#.##");
    public BooleanProperty smoothMovementsEnabledProperty = new SimpleBooleanProperty(true);

    public void init(PlayModel playModel, SceneController sceneController) {
        this.playModel = playModel;
        seekBarController.onTimeChanged.setHandler(this::onSeekBarTimeChanged);
        seekBarController.onSeekThumbPressed.setHandler(this::onSeekBarThumbPressed);
        seekBarController.init(playModel, sceneController);
        sceneController.onCreationModeEntered.addHandler(this::sceneControllerCreationModeEntered);
        smoothMovementsEnabledProperty.set(settingsService.getEnableSmoothMovements());
        smoothMovementsEnabledProperty.addListener(this::smoothMovementsEnabledPropertyChanged);
        super.addChild(seekBarController);
        setFixedForwardPeriod(DEFAULT_FIXED_ADVANCE_PERIOD);
        setFixedRewindPeriod(DEFAULT_FIXED_ADVANCE_PERIOD);
        stop();
    }

    @Override
    public void clean() {
        stop();
        super.clean();
    }

    private void smoothMovementsEnabledPropertyChanged(ObservableValue<? extends Boolean> value, Boolean oldPropertyValue, Boolean newPropertyValue) {
        settingsService.setEnableSmoothMovements(newPropertyValue);
    }

    @FXML
    protected void onPlayButtonAction(ActionEvent e) {
        play(PlaySpeed.getNormalSpeed());
    }

    @FXML
    protected void onPauseButtonAction(ActionEvent e) {
        stop();
    }

    @FXML
    protected void onFirstKeyPointButtonAction(ActionEvent e) {
        stop();
        seekBarController.goToBeginning(true, KEYPOINT_MOVE_ANIMATION_PERIOD);
    }

    @FXML
    protected void onLastKeyPointButtonAction(ActionEvent e) {
        stop();
        seekBarController.goToEnd(true, KEYPOINT_MOVE_ANIMATION_PERIOD);
    }

    @FXML
    protected void onNextKeyPointButtonAction(ActionEvent e) {
        stop();
        seekBarController.goToNextKeyPoint(true, KEYPOINT_MOVE_ANIMATION_PERIOD);
    }

    @FXML
    protected void onPreviousKeyPointButtonAction(ActionEvent e) {
        stop();
        seekBarController.goToPreviousKeyPoint(true, KEYPOINT_MOVE_ANIMATION_PERIOD);
    }

    @FXML
    protected void onNewKeyPointButtonAction(ActionEvent e) {
        stop();
        playService.beginUpdate(playModel.getUUID());
        playService.addKeypoint(playModel.getUUID(), seekBarController.getTime());
        playService.endUpdate(playModel.getUUID());
        seekBarController.goToEnd(false, 0L);
    }

    @FXML
    protected void onFastForwardButtonAction(ActionEvent e) {
        if (playSpeed.lessThan(PlaySpeed.getDoubleSpeed())) {
            play(PlaySpeed.getDoubleSpeed());
        } else if (!playSpeed.isMaxSpeed()) {
            play(playSpeed.getNextSpeed());
        }
    }

    @FXML
    protected void onRewindButtonAction(ActionEvent e) {
        if (playSpeed.greaterThan(PlaySpeed.getNormalReverseSpeed())) {
            play(PlaySpeed.getNormalReverseSpeed());
        } else if (!playSpeed.isMinSpeed()) {
            play(playSpeed.getPreviousSpeed());
        }
    }

    @FXML
    protected void onFixedRewindButtonAction(ActionEvent e) {
        stop();
        seekBarController.move(seekBarController.getTime() - fixedRewindPeriod);
    }

    @FXML
    protected void onChangeFixedRewindPeriodMenuItemAction(ActionEvent e) {
        ExtendedRadioMenuItem menuItem = (ExtendedRadioMenuItem) e.getSource();
        setFixedRewindPeriod(Integer.parseInt(menuItem.getCustomData().toString()));
    }

    private void setFixedRewindPeriod(Integer fixedRewindPeriod) {
        this.fixedRewindPeriod = fixedRewindPeriod;
        fixedRewindPeriodLabel.setText(fixedAdvancePeriodDecimalFormat.format(this.fixedRewindPeriod / 1000.0));
    }

    @FXML
    protected void onFixedRewindMenuShowing(WindowEvent e) {
        fixedRewindContextMenu.getItems().stream().forEach(menuItem -> {
            ExtendedRadioMenuItem item = (ExtendedRadioMenuItem) menuItem;
            if (Integer.parseInt(item.getCustomData().toString()) == fixedRewindPeriod) {
                item.setSelected(true);
            }
        });
    }

    @FXML
    protected void onFixedForwardButtonAction(ActionEvent e) {
        stop();
        seekBarController.move(seekBarController.getTime() + fixedRewindPeriod);
    }

    @FXML
    protected void onChangeFixedForwardPeriodMenuItemAction(ActionEvent e) {
        ExtendedRadioMenuItem menuItem = (ExtendedRadioMenuItem) e.getSource();
        setFixedForwardPeriod(Integer.parseInt(menuItem.getCustomData().toString()));
    }

    private void setFixedForwardPeriod(Integer fixedForwardPeriod) {
        this.fixedForwardPeriod = fixedForwardPeriod;
        fixedForwardPeriodLabel.setText(fixedAdvancePeriodDecimalFormat.format(this.fixedForwardPeriod / 1000.0));
    }

    @FXML
    protected void onFixedForwardMenuShowing(WindowEvent e) {
        fixedForwardContextMenu.getItems().stream().forEach(menuItem -> {
            ExtendedRadioMenuItem item = (ExtendedRadioMenuItem) menuItem;
            if (Integer.parseInt(item.getCustomData().toString()) == fixedForwardPeriod) {
                item.setSelected(true);
            }
        });
    }

    @FXML
    protected void onChangePlaySpeedMenuItemAction(ActionEvent e) {
        ExtendedRadioMenuItem menuItem = (ExtendedRadioMenuItem) e.getSource();
        setBasePlaySpeed(Double.parseDouble(menuItem.getCustomData().toString()));
    }

    private void setBasePlaySpeed(Double basePlaySpeedMultiplier) {
        playSpeed.setBaseMultiplier(basePlaySpeedMultiplier);
        if (!playSpeed.isStillSpeed()) {
            play(playSpeed.getSpeed());
        }
    }

    @FXML
    protected void onPlaySpeedMenuShowing(WindowEvent e) {
        playSpeedContextMenu.getItems().stream().forEach(menuItem -> {
            ExtendedRadioMenuItem item = (ExtendedRadioMenuItem) menuItem;
            if (Double.parseDouble(item.getCustomData().toString()) == playSpeed.getBaseMultiplier()) {
                item.setSelected(true);
            }
        });
    }

    private void play(Double speed) {
        timer.cancel();
        playSpeed.setSpeed(speed);
        updateControlButtonStates();
        timer = new Timer();
        if (smoothMovementsEnabledProperty.get()) {
            timer.schedule(new SmoothPlayTask(), 0, (int) (playModel.keyPointInterval.get() / SequencePaneController.this.playSpeed.getSpeedAbs()));
        } else {
            timer.schedule(new LinearPlayTask(), 0, LINEAR_AUTO_ADVANCE_PERIOD);
        }
    }

    private class SmoothPlayTask extends TimerTask {

        @Override
        public void run() {
            Long time;
            if (playSpeed.isForward()) {
                time = seekBarController.getNextKeyPointTime();
            } else {
                time = seekBarController.getPreviousKeyPointTime();
            }
            seekBarController.move(time, true, true, (long) (playModel.keyPointInterval.get() / SequencePaneController.this.playSpeed.getSpeedAbs()));
        }
    };

    private class LinearPlayTask extends TimerTask {

        @Override
        public void run() {
            Long time = seekBarController.getTime() + (long) (LINEAR_AUTO_ADVANCE_PERIOD * SequencePaneController.this.playSpeed.getSpeed());
            seekBarController.move(time, false, false, 0L);
        }
    };

    private void stop() {
        timer.cancel();
        playSpeed.setSpeed(PlaySpeed.getStillSpeed());
        updateControlButtonStates();
    }

    private void onSeekBarThumbPressed(Object sender) {
        stop();
    }

    private void onSeekBarTimeChanged(Object sender, Long time) {
        updateControlButtonStates();
        Platform.runLater(() -> {
            currentTimeLabel.setText(DurationUtils.toMMSSddd(Duration.ofMillis(time)));
            remainingTimeLabel.setText(DurationUtils.toMMSSddd(Duration.ofMillis(seekBarController.getRemainingTime())));
        });
        Long length = playModel.timelineLength.get();
        if (time.equals(length) && playSpeed.isForward()) {
            stop();
        } else if (time.equals(0L) && playSpeed.isBackward()) {
            stop();
        }
    }

    private void updateControlButtonStates() {
        Long length = playModel.timelineLength.get();
        FXUtils.setDisplay(playButton, playSpeed.isStillSpeed());
        FXUtils.setDisplay(pauseButton, !playSpeed.isStillSpeed());
        playButton.setDisable(seekBarController.getTime() >= length);
        fixedRewindButton.setDisable(seekBarController.getTime() <= 0);
        fixedForwardButton.setDisable(seekBarController.getTime() >= length);
        firstKeyPointButton.setDisable(seekBarController.getTime() <= 0);
        lastKeyPointButton.setDisable(seekBarController.getTime() >= length);
        previousKeyPointButton.setDisable(seekBarController.getTime() <= 0);
        nextKeyPointButton.setDisable(seekBarController.getTime() >= length);
        rewindButton.setDisable(seekBarController.getTime() <= 0);
        rewindButton.setSelected(playSpeed.lessThanOrEqual(PlaySpeed.getNormalReverseSpeed()));
        fastForwardButton.setDisable(seekBarController.getTime() >= length);
        fastForwardButton.setSelected(playSpeed.greaterThan(PlaySpeed.getNormalSpeed()));
    }

    private void sceneControllerCreationModeEntered(Object sender) {
        stop();
        seekBarController.goToBeginning(true, KEYPOINT_MOVE_ANIMATION_PERIOD);
    }
}
