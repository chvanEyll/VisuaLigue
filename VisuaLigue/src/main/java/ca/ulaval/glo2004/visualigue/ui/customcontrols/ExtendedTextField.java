package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ExtendedTextField extends TextField {

    private Integer maxLength = Integer.MAX_VALUE;
    private Boolean upperCase = false;

    public ExtendedTextField() {
        this.textProperty().addListener(this::onTextFieldChanged);
    }

    public Boolean getUpperCase() {
        return upperCase;
    }

    public void setUpperCase(Boolean upperCase) {
        this.upperCase = upperCase;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    private void onTextFieldChanged(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
        checkLength();
        checkCase();
    }

    private void checkLength() {
        if (this.getText().length() > maxLength) {
            String substring = this.getText().substring(0, maxLength);
            this.setText(substring);
        }
    }

    private void checkCase() {
        if (upperCase) {
            this.setText(this.getText().toUpperCase());
        }
    }
}
