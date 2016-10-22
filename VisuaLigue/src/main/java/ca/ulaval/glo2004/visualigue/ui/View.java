package ca.ulaval.glo2004.visualigue.ui;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import javafx.scene.Node;

public class View<T extends ControllerBase> {

    private T controller;
    private Node root;

    public View(T controller, Node root) {
        this.controller = controller;
        this.root = root;
    }

    public T getController() {
        return controller;
    }

    public void setController(T controller) {
        this.controller = controller;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

}
