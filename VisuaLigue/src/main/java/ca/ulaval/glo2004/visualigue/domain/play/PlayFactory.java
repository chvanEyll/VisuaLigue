package ca.ulaval.glo2004.visualigue.domain.play;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import javax.inject.Singleton;

@Singleton
public class PlayFactory {

    public Play create(Sport sport) {
        return new Play(sport);
    }
}
