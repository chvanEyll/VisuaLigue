package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.View;

public class ViewFlowRequestEventArgs {

    private View view = null;
    private Boolean cancelled = false;

    public ViewFlowRequestEventArgs() {
    }

    public ViewFlowRequestEventArgs(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public Boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

}
