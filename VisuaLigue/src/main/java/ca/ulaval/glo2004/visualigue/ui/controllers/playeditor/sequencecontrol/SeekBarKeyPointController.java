package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class SeekBarKeyPointController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/sequencecontrol/seekbar-key-point.fxml";

    public EventHandler<Integer> onClick = new EventHandler();
    @FXML private Button rootNode;
    @FXML private Label indexLabel;
    private Integer index;

    public void init(Integer index, HBox keyframeHBox) {
        this.index = index;
        indexLabel.setText(index.toString());
        GridPane.setColumnIndex(rootNode, index);
    }

    @FXML
    protected void onMouseClicked(MouseEvent e) {
        onClick.fire(this, index);
    }

}
