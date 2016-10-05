package ca.ulaval.glo2004.visualigue.ui.controllers;

import java.util.Stack;
import javafx.fxml.FXMLLoader;

public class ViewFlow {

    Stack<FXMLLoader> viewStack = new Stack<>();

    public ViewFlow() {
    }

    public ViewFlow(FXMLLoader initialView) {
        viewStack.push(initialView);
    }

    public void addView(FXMLLoader view) {
        viewStack.push(view);
    }

    public void clear() {
        while (!viewStack.empty()) {
            clearView(viewStack.pop());
        }
    }

    public FXMLLoader moveToPrevious() {
        clearView(viewStack.pop());
        return viewStack.peek();
    }

    public void clearView(FXMLLoader view) {
        Controller controller = view.getController();
        controller.clearHandlers();
    }

    public int count() {
        return viewStack.size();
    }
}
