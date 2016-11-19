package ca.ulaval.glo2004.visualigue.services.play.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class CommandGroup {

    private List<Command> commands = new ArrayList();

    public CommandGroup(Command command) {
        commands.add(command);
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public Long revert() {
        Long time = null;
        ListIterator<Command> listIterator = commands.listIterator(commands.size());
        while (listIterator.hasPrevious()) {
            time = listIterator.previous().revert();
        }
        return time;
    }

    public Long execute() {
        Long time = null;
        ListIterator<Command> listIterator = commands.listIterator(0);
        while (listIterator.hasNext()) {
            time = listIterator.next().execute();
        }
        return time;
    }

}
