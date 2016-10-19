package ca.ulaval.glo2004.visualigue.domain.play;

import javax.inject.Singleton;

@Singleton
public class PlayFactory {

    public Play create(String name) {
        return new Play(name);
    }
}
