package ca.ulaval.glo2004.visualigue.contexts;

public abstract class ContextBase {

    public void apply() throws Exception {
        applyFillers();
    }

    protected abstract void applyFillers() throws Exception;

}
