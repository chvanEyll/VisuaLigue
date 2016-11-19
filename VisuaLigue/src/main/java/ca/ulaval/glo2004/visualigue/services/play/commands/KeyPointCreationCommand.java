package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;

public class KeyPointCreationCommand extends Command {

    public KeyPointCreationCommand(Play play, Long time) {
        super(play, time);
    }

    @Override
    public Long execute() {
        return play.addKeyPoint();
    }

    @Override
    public Long revert() {
        play.removeKeyPoint();
        return time;
    }

}
