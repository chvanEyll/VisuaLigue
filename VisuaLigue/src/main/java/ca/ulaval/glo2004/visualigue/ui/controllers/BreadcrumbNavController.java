/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class BreadcrumbNavController {

    @FXML HBox rootNode;
    private final List<BreadcrumbNavItemController> items = new ArrayList<>();
    public EventHandler<Integer> onItemClicked = new EventHandler<>();

    public void addItem(String title) {
        FXMLLoader fxmlLoader = GuiceFXMLLoader.load(BreadcrumbNavItemController.VIEW_NAME);
        BreadcrumbNavItemController controller = (BreadcrumbNavItemController) fxmlLoader.getController();
        controller.init(title, items.size() > 0);
        controller.onClick.addHandler(this::onItemClickedHandler);
        rootNode.getChildren().add(rootNode.getChildren().size() - 1, fxmlLoader.getRoot());
        items.add(controller);
    }

    public void setActiveItem(Integer itemIndex) {
        items.stream().forEach(item -> {
            item.setActive(item == items.get(itemIndex));
        });
    }

    public void onItemClickedHandler(Object sender, Object eventArgs) {
        onItemClicked.fire(this, items.indexOf(sender));
    }
}