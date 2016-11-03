package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;

public class PlaySpeed {

    private static final Double NORMAL_REVERSE_SPEED = -1.0;
    private static final Double STILL_SPEED = 0.0;
    private static final Double NORMAL_SPEED = 1.0;
    private static final Double DOUBLE_SPEED = 2.0;
    private NavigableSet<Double> playSpeeds = new TreeSet();
    private Double currentSpeed = 0.0;
    private Double baseMultiplier = 1.0;

    public PlaySpeed() {
        playSpeeds.addAll(Arrays.asList(-3.0, -2.0, -1.0, 0.0, 0.5, 1.0, 2.0, 3.0));
    }

    public Double getSpeed() {
        return baseMultiplier * currentSpeed;
    }

    public Double getSpeedAbs() {
        return Math.abs(getSpeed());
    }

    public void setSpeed(Double speed) {
        this.currentSpeed = speed;
    }

    public Boolean isMinSpeed() {
        return currentSpeed <= playSpeeds.first();
    }

    public Boolean isMaxSpeed() {
        return currentSpeed >= playSpeeds.last();
    }

    public Double getPreviousSpeed() {
        return playSpeeds.lower(currentSpeed);
    }

    public Double getNextSpeed() {
        return playSpeeds.higher(currentSpeed);
    }

    public Boolean isForward() {
        return getSpeed() > 0;
    }

    public Boolean isBackward() {
        return getSpeed() < 0;
    }

    public Double getBaseMultiplier() {
        return baseMultiplier;
    }

    public void setBaseMultiplier(Double baseMultiplier) {
        this.baseMultiplier = baseMultiplier;
    }

    public static Double getNormalReverseSpeed() {
        return NORMAL_REVERSE_SPEED;
    }

    public static Double getStillSpeed() {
        return STILL_SPEED;
    }

    public Boolean isStillSpeed() {
        return currentSpeed.equals(STILL_SPEED);
    }

    public static Double getNormalSpeed() {
        return NORMAL_SPEED;
    }

    public Boolean isNormalSpeed() {
        return currentSpeed.equals(NORMAL_SPEED);
    }

    public static Double getDoubleSpeed() {
        return DOUBLE_SPEED;
    }

    public Boolean lessThan(Double otherSpeed) {
        return currentSpeed < otherSpeed;
    }

    public Boolean lessThanOrEqual(Double otherSpeed) {
        return currentSpeed <= otherSpeed;
    }

    public Boolean greaterThan(Double otherSpeed) {
        return currentSpeed > otherSpeed;
    }

}
