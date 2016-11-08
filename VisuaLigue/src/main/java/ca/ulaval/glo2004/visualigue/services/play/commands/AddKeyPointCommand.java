package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public class AddKeyPointCommand extends Command {

    private EventHandler<Play> onPlayUpdated = new EventHandler();

    public AddKeyPointCommand(Play play, Long time, EventHandler<Play> onPlayUpdated) {
        super(play, time);
        this.onPlayUpdated = onPlayUpdated;
    }

    @Override
    public void execute() {
        play.addKeyPoint();
        onPlayUpdated.fire(this, play);
    }

    @Override
    public void revert() {
        play.removeKeyPoint();
        onPlayUpdated.fire(this, play);
    }

}
