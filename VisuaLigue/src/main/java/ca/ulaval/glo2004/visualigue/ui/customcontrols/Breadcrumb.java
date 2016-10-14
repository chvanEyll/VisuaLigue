package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class Breadcrumb extends HBox {

    public static final String VIEW_NAME = "/views/custom-controls/breadcrumb.fxml";
    private final List<BreadcrumbItem> items = new ArrayList<>();
    public EventHandler<Integer> onItemClicked = new EventHandler<>();

    public Breadcrumb() {
        try {
            FXMLLoader fxmlLoader = InjectableFXMLLoader.createLoader(VIEW_NAME);
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(Breadcrumb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addItem(String title) {
        BreadcrumbItem breadcrumbItem = new BreadcrumbItem(title, items.size() > 0);
        breadcrumbItem.onClick.setHandler(this::onItemClickedHandler);
        items.add(breadcrumbItem);
        this.getChildren().add(this.getChildren().size() - 1, breadcrumbItem);
    }

    public void setActiveItem(Integer itemIndex) {
        items.forEach(item -> {
            BreadcrumbItem breadcrumbItem = (BreadcrumbItem) item;
            breadcrumbItem.setActive(item == items.get(itemIndex));
        });
    }

    public void onItemClickedHandler(Object sender, Object eventArgs) {
        onItemClicked.fire(this, items.indexOf(sender));
    }
}
