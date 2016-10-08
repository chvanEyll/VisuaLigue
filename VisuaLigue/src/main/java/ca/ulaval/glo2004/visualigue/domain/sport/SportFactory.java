package ca.ulaval.glo2004.visualigue.domain.sport;

import javax.inject.Singleton;

@Singleton
public class SportFactory {

    public Sport create(String name) {
        return new Sport(name);
    }
}
