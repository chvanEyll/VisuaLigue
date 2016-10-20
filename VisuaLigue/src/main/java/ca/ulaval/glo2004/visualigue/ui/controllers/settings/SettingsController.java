package ca.ulaval.glo2004.visualigue.ui.controllers.settings;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import ca.ulaval.glo2004.visualigue.services.settings.SettingsService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.dialog.AlertDialogBuilder;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javax.inject.Inject;

public class SettingsController extends ControllerBase {

    public static final String VIEW_NAME = "/views/settings/settings.fxml";
    public static final String VIEW_TITLE = "Paramètres";
    @Inject private SettingsService settingsService;
    @FXML Label productName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productName.setText(VisuaLigue.getAppName());
    }

    @Override
    public StringProperty getTitle() {
        return new SimpleStringProperty(VIEW_TITLE);
    }

    @FXML
    public void onResetButtonAction(ActionEvent e) {
        Optional<ButtonType> result = new AlertDialogBuilder().alertType(Alert.AlertType.WARNING).headerText("Réinitialisation de l'application et des données")
                .contentText("Êtes-vous sûr de vouloir supprimer réinitialiser l'application et les données (cette action est irréversible)?")
                .buttonType(new ButtonType("Réinitialiser", ButtonBar.ButtonData.YES))
                .buttonType(new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE)).showAndWait();

        if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
            performReset();
        }
    }

    private void performReset() {
        try {
            settingsService.reset();
        } catch (Exception ex) {
            new AlertDialogBuilder().alertType(Alert.AlertType.ERROR).headerText("Réinitialisation de l'application et des données")
                    .contentText("Une erreur est survenue lors de la réinitialisation.")
                    .exception(ex)
                    .buttonType(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE)).showAndWait();
        }
    }

}
