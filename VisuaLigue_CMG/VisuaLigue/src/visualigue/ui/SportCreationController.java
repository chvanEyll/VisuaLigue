package visualigue.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import visualigue.domain.VisuaLigue;

public class SportCreationController implements Initializable {

    private VisuaLigue visualigue = VisuaLigue.getInstance();

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        visualigue.createSport("test");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visualigue.onSportCreated.addHandler(this::onSportCreated);
    }

    private void onSportCreated(Object sender, String name) {
        label.setText(String.format("Sport '%s' has been created!", name));
    }

}
