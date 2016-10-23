package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.HBox;

public class Breadcrumb extends HBox {

    public static final String VIEW_NAME = "/views/customcontrols/breadcrumb.fxml";
    private final List<BreadcrumbItem> items = new ArrayList();
    public EventHandler<Integer> onItemClicked = new EventHandler();

    public Breadcrumb() {
        InjectableFXMLLoader.loadView(VIEW_NAME, this, this);
    }

    public void addItem(String title) {
        BreadcrumbItem breadcrumbItem = new BreadcrumbItem(title, items.size() > 0);
        breadcrumbItem.onClick.setHandler(this::onItemClicked);
        items.add(breadcrumbItem);
        this.getChildren().add(this.getChildren().size() - 1, breadcrumbItem);
    }

    public void setActiveItem(Integer itemIndex) {
        items.forEach(item -> {
            BreadcrumbItem breadcrumbItem = (BreadcrumbItem) item;
            breadcrumbItem.setActive(item == items.get(itemIndex));
        });
    }

    private void onItemClicked(Object sender, Object eventArgs) {
        onItemClicked.fire(this, items.indexOf(sender));
    }
}
