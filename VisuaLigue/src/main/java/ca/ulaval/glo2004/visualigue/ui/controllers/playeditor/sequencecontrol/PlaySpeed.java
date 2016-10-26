package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import java.util.Arrays;

public enum PlaySpeed {

    STILL_SPEED(0),
    NORMAL_SPEED(1),
    DOUBLE_SPEED(2),
    QUINTUPLE_SPEED(5);

    private Integer multiplier;

    PlaySpeed(Integer multiplier) {
        this.multiplier = multiplier;
    }

    public Integer getMultiplier() {
        return multiplier;
    }

    public Boolean isMaxSpeed() {
        return this == PlaySpeed.values()[PlaySpeed.values().length - 1];
    }

    public PlaySpeed nextSpeed() {
        Integer currentIndex = Arrays.asList(PlaySpeed.values()).indexOf(this);
        return PlaySpeed.values()[currentIndex + 1];
    }

}