package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import javafx.scene.control.RadioMenuItem;

public class ExtendedRadioMenuItem extends RadioMenuItem {

    private Object customData;

    public Object getCustomData() {
        return customData;
    }

    public void setCustomData(Object customData) {
        this.customData = customData;
    }

}
