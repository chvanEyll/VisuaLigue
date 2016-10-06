package ca.ulaval.glo2004.visualigue.domain.sport;

public class SportFactory {

    public Sport create(String name) {
        return new Sport(name);
    }
}
