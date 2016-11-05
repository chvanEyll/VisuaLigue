package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public class TimelineLengthUpdateCommand extends Command {

    private Integer timelineLength;
    private Integer oldTimelineLength;
    private EventHandler<Play> onPlayUpdated = new EventHandler();

    public TimelineLengthUpdateCommand(Play play, Integer time, Integer timelineLength, EventHandler<Play> onPlayUpdated) {
        super(play, time);
        this.timelineLength = timelineLength;
        this.onPlayUpdated = onPlayUpdated;
    }

    @Override
    public void execute() {
        oldTimelineLength = play.getTimelineLength();
        play.setTimelineLength(timelineLength);
        onPlayUpdated.fire(this, play);
    }

    @Override
    public void revert() {
        play.setTimelineLength(oldTimelineLength);
        onPlayUpdated.fire(this, play);
    }

}
