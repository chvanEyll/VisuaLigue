package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.View;
import java.util.Stack;

public class ViewFlow {

    Stack<View> viewStack = new Stack();

    public ViewFlow() {
    }

    public ViewFlow(View initialView) {
        viewStack.push(initialView);
    }

    public void addView(View view) {
        viewStack.push(view);
    }

    public void clear() {
        while (!viewStack.empty()) {
            clearView(viewStack.pop());
        }
    }

    public View getCurrentView() {
        return viewStack.peek();
    }

    public View moveToPrevious() {
        clearView(viewStack.pop());
        return viewStack.peek();
    }

    public void clearView(View view) {
        ControllerBase controller = view.getController();
        controller.clearHandlers();
    }

    public int count() {
        return viewStack.size();
    }
}
