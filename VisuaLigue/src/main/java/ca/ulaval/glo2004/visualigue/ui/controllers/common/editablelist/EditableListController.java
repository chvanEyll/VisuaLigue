package ca.ulaval.glo2004.visualigue.ui.controllers.common.editablelist;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class EditableListController extends ControllerBase {

    @FXML private VBox gridContent;
    private ObservableList models;
    private Map<ModelBase, View> views = new HashMap();
    private ListItemEditionController listItemEditionController;
    private String itemControllerName;
    private String itemEditionControllerName;
    private Class modelClass;

    public void init(ObservableList models, Class modelClass, String itemControllerName, String itemEditionControllerName) {
        this.modelClass = modelClass;
        this.itemControllerName = itemControllerName;
        this.itemEditionControllerName = itemEditionControllerName;
        this.models = models;
        EditableListController.this.models.forEach(model -> {
            insertItem((ModelBase) model, gridContent.getChildren().size());
        });
    }

    private void insertItem(ModelBase model, Integer rowIndex) {
        View view = InjectableFXMLLoader.loadView(itemControllerName);
        ListItemController listItemController = (ListItemController) view.getController();
        listItemController.init(model);
        listItemController.onEditRequested.setHandler(this::onItemEditRequested);
        listItemController.onDeleteRequested.setHandler(this::onItemDeleteRequest);
        views.put(model, view);
        gridContent.getChildren().add(rowIndex, view.getRoot());
        super.addChild(listItemController);
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
        int rowIndex = gridContent.getChildren().indexOf(views.get(model).getRoot());
        gridContent.getChildren().remove(rowIndex);
        PredefinedAnimations.split(view.getRoot());
        views.put(model, view);
        gridContent.getChildren().add(rowIndex, view.getRoot());
    }

    private void onItemEditionCloseRequested(Object sender, ModelBase model) {
        closeItemEdition();
    }

    private void closeItemEdition() {
        if (listItemEditionController != null) {
            ModelBase model = listItemEditionController.getModel();
            int rowIndex = gridContent.getChildren().indexOf(views.get(model).getRoot());
            gridContent.getChildren().remove(views.get(model).getRoot());
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
        gridContent.getChildren().remove(views.get(model).getRoot());
        views.remove(model);
    }
}
