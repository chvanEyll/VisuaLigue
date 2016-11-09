package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.View;
import java.util.Stack;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class ViewFlowController {

    private Stack<View> viewStack = new Stack();
    @FXML private StackPane rootNode;

    public void appendView(View view) {
        viewStack.push(view);
        rootNode.getChildren().add(view.getRoot());
    }

    public void clear() {
        while (!viewStack.empty()) {
            popView();
        }
    }

    public View getCurrentView() {
        return viewStack.peek();
    }

    public View popView() {
        View currentView = viewStack.peek();
        if (!validateViewClose(currentView)) {
            throw new ViewFlowException("The controller has declined the close operation.");
        } else {
            rootNode.getChildren().remove(currentView.getRoot());
            cleanController(currentView);
            return viewStack.pop();
        }
    }

    private Boolean validateViewClose(View currentView) {
        ControllerBase controller = (ControllerBase) currentView.getController();
        return controller.onClose();
    }

    private void cleanController(View currentView) {
        ControllerBase controller = (ControllerBase) currentView.getController();
        controller.clean();
    }

    public int count() {
        return viewStack.size();
    }

    public Boolean empty() {
        return viewStack.empty();
    }
}
