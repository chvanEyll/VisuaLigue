package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import javafx.scene.control.MenuItem;

public class ExtendedMenuItem extends MenuItem {

    private Object customData;

    public Object getCustomData() {
        return customData;
    }

    public void setCustomData(Object customData) {
        this.customData = customData;
    }

}
