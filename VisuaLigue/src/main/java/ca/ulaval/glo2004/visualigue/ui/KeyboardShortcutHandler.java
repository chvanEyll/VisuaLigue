package ca.ulaval.glo2004.visualigue.ui;

import ca.ulaval.glo2004.visualigue.VisuaLigue;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class KeyboardShortcutHandler {

    private static final KeyboardShortcutHandler INSTANCE = new KeyboardShortcutHandler();
    private Map<KeyCombination, Runnable> keyCombinationMap = new HashMap();

    public KeyboardShortcutHandler() {
        VisuaLigue.getMainScene().addEventHandler(KeyEvent.KEY_RELEASED, new KeyReleasedEventHandler());
    }

    public static void assign(KeyCombination keyCombination, Runnable runnable) {
        INSTANCE.keyCombinationMap.put(keyCombination, runnable);
    }

    public static void unassign(KeyCombination keyCombination) {
        INSTANCE.keyCombinationMap.remove(keyCombination);
    }

    private class KeyReleasedEventHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            keyCombinationMap.keySet().stream().collect(Collectors.toList()).forEach(k -> {
                if (k.match(event)) {
                    keyCombinationMap.get(k).run();
                }
            });
        }
    }

}
