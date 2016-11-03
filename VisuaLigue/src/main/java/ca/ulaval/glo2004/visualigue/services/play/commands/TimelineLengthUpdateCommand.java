package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;

public class TimelineLengthUpdateCommand implements Command {

    private Play play;
    private Integer timelineLength;
    private Integer oldTimelineLength;

    public TimelineLengthUpdateCommand(Play play, Integer timelineLength) {
        this.play = play;
        this.timelineLength = timelineLength;
    }

    @Override
    public void execute() {
        oldTimelineLength = play.getTimelineLength();
        play.setTimelineLength(timelineLength);
    }

    @Override
    public void revert() {
        play.setTimelineLength(oldTimelineLength);
    }

}
