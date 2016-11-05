package ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SportListItemController extends ControllerBase {

    public static final String VIEW_NAME = "/views/sportmanagement/sport-list-item.fxml";

    @FXML private Label sportNameLabel;
    @FXML private ImageView imageView;
    private SportListItemModel model;
    public EventHandler<SportListItemModel> onClick = new EventHandler();

    public void init(SportListItemModel model) {
        this.model = model;
        sportNameLabel.textProperty().bind(model.name);
        model.customIconPathName.addListener(this::onChange);
        model.builtInIconPathName.addListener(this::onChange);
        update();
    }

    public void onChange(ObservableValue<? extends Object> value, Object oldPropertyValue, Object newPropertyValue) {
        update();
    }

    private void update() {
        if (model.customIconPathName.isNotEmpty().get()) {
            setSportImage(FilenameUtils.getURIString(model.customIconPathName.get()));
        } else if (model.builtInIconPathName.isNotEmpty().get()) {
            setSportImage(model.builtInIconPathName.get());
        }
    }

    private void setSportImage(String sportImagePathName) {
        imageView.setImage(new Image(sportImagePathName));
    }

    @FXML
    protected void onMouseClicked(MouseEvent e) {
        onClick.fire(this, model);
    }

}
