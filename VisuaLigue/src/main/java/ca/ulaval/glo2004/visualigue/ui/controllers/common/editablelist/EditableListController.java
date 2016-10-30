package ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class EditableListController extends ControllerBase {

    @FXML private VBox gridContent;
    private ObservableList models;
    private ListItemEditionController listItemEditionController;
    private String itemControllerName;
    private String itemEditionControllerName;
    private Integer itemCount = 0;
    private Class modelClass;

    public void init(ObservableList models, Class modelClass, String itemControllerName, String itemEditionControllerName) {
        this.modelClass = modelClass;
        this.itemControllerName = itemControllerName;
        this.itemEditionControllerName = itemEditionControllerName;
        this.models = models;
        EditableListController.this.models.forEach(model -> {
            insertItem((ModelBase) model, itemCount);
        });
    }

    private void insertItem(ModelBase model, Integer rowIndex) {
        View view = InjectableFXMLLoader.loadView(itemControllerName);
        ListItemController listItemController = (ListItemController) view.getController();
        listItemController.init(model);
        listItemController.onEditRequested.setHandler(this::onItemEditRequested);
        listItemController.onDeleteRequested.setHandler(this::onItemDeleteRequest);
        super.addChild(listItemController);
        gridContent.getChildren().add(rowIndex, view.getRoot());
        itemCount += 1;
    }

    private void onItemEditRequested(Object sender, ModelBase model) {
        enterItemEditionMode(model);
    }

    private void enterItemEditionMode(ModelBase model) {
        closeItemEdition();
        View view = InjectableFXMLLoader.loadView(itemEditionControllerName);
        listItemEditionController = (ListItemEditionController) view.getController();
        listItemEditionController.init(model);
        listItemEditionController.onCloseRequested.setHandler(this::onItemEditionCloseRequested);
        super.addChild(listItemEditionController);
        int rowIndex = models.indexOf(model);
        gridContent.getChildren().remove(rowIndex);
        PredefinedAnimations.split(view.getRoot());
        gridContent.getChildren().add(rowIndex, view.getRoot());
    }

    private void onItemEditionCloseRequested(Object sender, ModelBase model) {
        closeItemEdition();
    }

    private void closeItemEdition() {
        if (listItemEditionController != null) {
            int rowIndex = models.indexOf(listItemEditionController.getModel());
            Node node = gridContent.getChildren().get(rowIndex);
            gridContent.getChildren().remove(rowIndex);
            ModelBase model = listItemEditionController.getModel();
            insertItem(model, rowIndex);
            model.makeDirty();
            listItemEditionController = null;
        }
    }

    @FXML
    protected void onNewItemLinkButtonClicked(MouseEvent e) {
        newItem();
    }

    public void newItem() {
        closeItemEdition();
        ModelBase model = null;
        try {
            model = (ModelBase) modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(EditableListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        models.add(0, model);
        insertItem(model, 0);
        enterItemEditionMode(model);
    }

    private void onItemDeleteRequest(Object sender, ModelBase model) {
        deleteItem((ListItemController) sender, model);
    }

    protected void deleteItem(ListItemController listItemController, ModelBase model) {
        model.markAsDeleted();
        listItemController.hide();
    }
}
