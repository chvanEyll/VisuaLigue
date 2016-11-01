package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

public enum PlayDirection {
    NONE(1),
    FORWARD(1),
    REVERSE(-1);

    private Integer sign;

    PlayDirection(Integer sign) {
        this.sign = sign;
    }

    public Integer getSign() {
        return this.sign;
    }
}
