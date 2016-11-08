package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;

public abstract class Command {

    protected Play play;
    protected Long time;

    public Command(Play play, Long time) {
        this.play = play;
        this.time = time;
    }

    public abstract void execute();

    public abstract void revert();

    public Long getTime() {
        return time;
    }
}
