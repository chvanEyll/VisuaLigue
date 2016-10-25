package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import ca.ulaval.glo2004.visualigue.ui.controllers.ViewController;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class SeekBarKeyPointController extends ViewController {

    public static final String VIEW_NAME = "/views/playeditor/sequencecontrol/seekbar-key-point.fxml";

    public EventHandler<Integer> onClick = new EventHandler();
    @FXML private Label indexLabel;
    private Integer index;

    public void init(Integer index) {
        this.index = index;
        indexLabel.setText(index.toString());
    }

    @FXML
    protected void onMouseClicked(MouseEvent e) {
        onClick.fire(this, index);
    }

}
