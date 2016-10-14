package ca.ulaval.glo2004.visualigue.ui.dialog;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

public class AlertDialogBuilder {

    private AlertType alertType = AlertType.NONE;
    private String headerText;
    private String contentText;
    private final Set<ButtonType> buttonTypes = new HashSet<>();

    public AlertDialogBuilder alertType(AlertType alertType) {
        this.alertType = AlertType.WARNING;
        return this;
    }

    public AlertDialogBuilder headerText(String headerText) {
        this.headerText = headerText;
        return this;
    }

    public AlertDialogBuilder contentText(String contentText) {
        this.contentText = contentText;
        return this;
    }

    public AlertDialogBuilder buttonType(ButtonType buttonType) {
        this.buttonTypes.add(buttonType);
        return this;
    }

    public Optional<ButtonType> showAndWait() {
        Alert alert = new Alert(alertType);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(VisuaLigue.getMainStage());
        alert.setTitle(VisuaLigue.getAppName());
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getButtonTypes().clear();;
        alert.getButtonTypes().addAll(buttonTypes);
        alert.getDialogPane().getStyleClass().add("root-node");
        alert.getDialogPane().getStylesheets().add(VisuaLigue.getMainStyleSheet());
        return alert.showAndWait();
    }

}
