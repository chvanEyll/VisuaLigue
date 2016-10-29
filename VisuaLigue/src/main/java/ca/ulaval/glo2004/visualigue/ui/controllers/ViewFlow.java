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

    public void appendView(View view) {
        viewStack.push(view);
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
            cleanController(currentView);
            return viewStack.pop();
        }
    }

    private Boolean validateViewClose(View currentView) {
        ControllerBase controller = (ControllerBase) currentView.getController();
        return controller.onViewClosing();
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
