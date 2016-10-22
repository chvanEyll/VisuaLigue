package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.ui.models.Model;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class EditableListController {

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
            insertItem((Model) model, itemCount);
        });
    }

    private void insertItem(Model model, Integer rowIndex) {
        View view = InjectableFXMLLoader.loadView(itemControllerName);
        ListItemController listItemController = (ListItemController) view.getController();
        listItemController.init(model);
        listItemController.onEditRequested.setHandler(this::onItemEditRequested);
        listItemController.onDeleteRequested.setHandler(this::onItemDeleteRequest);
        gridContent.getChildren().add(rowIndex, view.getRoot());
        itemCount += 1;
    }

    private void onItemEditRequested(Object sender, Model model) {
        enterItemEditionMode(model);
    }

    private void enterItemEditionMode(Model model) {
        closeItemEdition();
        View view = InjectableFXMLLoader.loadView(itemEditionControllerName);
        listItemEditionController = (ListItemEditionController) view.getController();
        listItemEditionController.init(model);
        listItemEditionController.onCloseRequested.setHandler(this::onItemEditionCloseRequested);
        int rowIndex = models.indexOf(model);
        gridContent.getChildren().remove(rowIndex);
        PredefinedAnimations.nodeSplit(view.getRoot());
        gridContent.getChildren().add(rowIndex, view.getRoot());
    }

    private void onItemEditionCloseRequested(Object sender, Model model) {
        closeItemEdition();
    }

    private void closeItemEdition() {
        if (listItemEditionController != null) {
            int rowIndex = models.indexOf(listItemEditionController.getModel());
            Node node = gridContent.getChildren().get(rowIndex);
            gridContent.getChildren().remove(rowIndex);
            Model model = listItemEditionController.getModel();
            insertItem(model, rowIndex);
            model.makeDirty();
            listItemEditionController = null;
        }
    }

    public void newItem() {
        closeItemEdition();
        Model model = null;
        try {
            model = (Model) modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        models.add(0, model);
        insertItem(model, 0);
        enterItemEditionMode(model);
    }

    private void onItemDeleteRequest(Object sender, Model model) {
        deleteItem((ListItemController) sender, model);
    }

    protected void deleteItem(ListItemController listItemController, Model model) {
        model.markAsDeleted();
        listItemController.hide();
    }
}
