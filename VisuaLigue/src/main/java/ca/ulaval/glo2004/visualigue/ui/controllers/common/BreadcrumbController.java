package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class BreadcrumbController {

    @FXML HBox rootNode;
    private final List<BreadcrumbItemController> items = new ArrayList<>();
    public EventHandler<Integer> onItemClicked = new EventHandler<>();

    public void addItem(String title) {
        FXMLLoader fxmlLoader = InjectableFXMLLoader.load(BreadcrumbItemController.VIEW_NAME);
        BreadcrumbItemController controller = (BreadcrumbItemController) fxmlLoader.getController();
        controller.init(title, items.size() > 0);
        controller.onClick.setHandler(this::onItemClickedHandler);
        rootNode.getChildren().add(rootNode.getChildren().size() - 1, fxmlLoader.getRoot());
        items.add(controller);
    }

    public void setActiveItem(Integer itemIndex) {
        items.forEach(item -> {
            item.setActive(item == items.get(itemIndex));
        });
    }

    public void onItemClickedHandler(Object sender, Object eventArgs) {
        onItemClicked.fire(this, items.indexOf(sender));
    }
}
