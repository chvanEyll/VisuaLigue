package ca.ulaval.glo2004.visualigue.services.play.commands;

public interface Command {

    void execute() throws Exception;

    void revert();

}