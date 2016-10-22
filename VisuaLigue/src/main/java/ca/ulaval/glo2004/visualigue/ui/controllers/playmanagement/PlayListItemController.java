package ca.ulaval.glo2004.visualigue.ui.controllers.playmanagement;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.dialog.AlertDialogBuilder;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class PlayListItemController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playmanagement/play-list-item.fxml";

    @FXML private VBox rootNode;
    @FXML private Label playTitleLabel;
    private PlayModel model;
    public EventHandler<PlayModel> onClick = new EventHandler();
    public EventHandler<PlayModel> onDeleteButtonClicked = new EventHandler();

    public void init(PlayModel model) {
        this.model = model;
        playTitleLabel.textProperty().bindBidirectional(model.title);
        if (model.thumbnailImagePathName.isNotEmpty().get()) {
            setPlayImage(model.thumbnailImagePathName.get());
        } else {
            setPlayImage(model.defaultThumbnailImagePathName.get());
        }
    }

    private void setPlayImage(String playImagePathName) {
        ImageView imageView = new ImageView();
        Image image = new Image(playImagePathName);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(playImagePathName));
        imageView.setFitWidth(372);
        imageView.setFitHeight(200);
        rootNode.getChildren().add(imageView);
    }

    @FXML
    protected void onMouseClicked(MouseEvent e) {
        onClick.fire(this, model);
    }

    @FXML
    protected void onDeleteButtonAction(ActionEvent e) {
        Optional<ButtonType> result = new AlertDialogBuilder().alertType(Alert.AlertType.WARNING).headerText("Suppression d'un jeu")
                .contentText(String.format("Êtes-vous sûr de vouloir supprimer le jeu '%s'?", model.title.get()))
                .buttonType(new ButtonType("Supprimer", ButtonBar.ButtonData.YES))
                .buttonType(new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE)).showAndWait();

        if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
            onDeleteButtonClicked.fire(this, model);
        }
    }

}
