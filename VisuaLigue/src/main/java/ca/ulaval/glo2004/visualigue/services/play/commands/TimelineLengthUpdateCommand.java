package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public class TimelineLengthUpdateCommand extends Command {

    private Integer timelineLength;
    private Integer oldTimelineLength;
    private EventHandler<Integer> onPlayTimelineLengthChanged = new EventHandler();

    public TimelineLengthUpdateCommand(Play play, Integer timelineLength, EventHandler<Integer> onPlayTimelineLengthChanged) {
        super(play);
        this.timelineLength = timelineLength;
        this.onPlayTimelineLengthChanged = onPlayTimelineLengthChanged;
    }

    @Override
    public void execute() {
        oldTimelineLength = play.getTimelineLength();
        play.setTimelineLength(timelineLength);
        onPlayTimelineLengthChanged.fire(this, timelineLength);
    }

    @Override
    public void revert() {
        play.setTimelineLength(oldTimelineLength);
        onPlayTimelineLengthChanged.fire(this, timelineLength);
    }

}
