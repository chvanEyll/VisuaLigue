package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene;

import ca.ulaval.glo2004.visualigue.services.settings.SettingsService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javax.inject.Inject;

public class Settings {

    private SettingsService settingsService;

    public BooleanProperty showActorLabelsProperty = new SimpleBooleanProperty(false);
    public BooleanProperty showMovementArrowsProperty = new SimpleBooleanProperty(true);
    public BooleanProperty resizeActorsOnZoomProperty = new SimpleBooleanProperty(true);
    public BooleanProperty showPlayerTrailsOnLastFrameProperty = new SimpleBooleanProperty(true);

    @Inject
    public Settings(SettingsService settingsService) {
        this.settingsService = settingsService;
        showActorLabelsProperty.set(settingsService.getShowActorLabels());
        showActorLabelsProperty.addListener(this::onShowActorLabelsPropertyChanged);
        showMovementArrowsProperty.set(settingsService.getShowMovementArrows());
        showMovementArrowsProperty.addListener(this::onShowMovementArrowsPropertyChanged);
        resizeActorsOnZoomProperty.set(settingsService.getResizeActorsOnZoom());
        resizeActorsOnZoomProperty.addListener(this::onResizeActorsOnZoomPropertyChanged);
        showPlayerTrailsOnLastFrameProperty.set(settingsService.getShowPlayerTrailsOnLastFrame());
        showPlayerTrailsOnLastFrameProperty.addListener(this::onShowPlayerTrailsOnLastFramePropertyChanged);
    }

    public void onShowActorLabelsPropertyChanged(ObservableValue<? extends Boolean> value, Boolean oldPropertyValue, Boolean newPropertyValue) {
        settingsService.setShowActorLabels(newPropertyValue);
    }

    public void onShowMovementArrowsPropertyChanged(ObservableValue<? extends Boolean> value, Boolean oldPropertyValue, Boolean newPropertyValue) {
        settingsService.setShowMovementArrows(newPropertyValue);
    }

    public void onResizeActorsOnZoomPropertyChanged(ObservableValue<? extends Boolean> value, Boolean oldPropertyValue, Boolean newPropertyValue) {
        settingsService.setResizeActorsOnZoom(newPropertyValue);
    }

    public void onShowPlayerTrailsOnLastFramePropertyChanged(ObservableValue<? extends Boolean> value, Boolean oldPropertyValue, Boolean newPropertyValue) {
        settingsService.setShowPlayerTrailsOnLastFrame(newPropertyValue);
    }

}
