package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;

public abstract class Command {

    protected Play play;

    public Command(Play play) {
        this.play = play;
    }

    public abstract void execute();

    public abstract void revert();
}
