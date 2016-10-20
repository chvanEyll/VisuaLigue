package ca.ulaval.glo2004.visualigue.domain.play;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import javax.inject.Singleton;

@Singleton
public class PlayFactory {

    public Play create(String name, Sport sport) {
        return new Play(name, sport);
    }
}
