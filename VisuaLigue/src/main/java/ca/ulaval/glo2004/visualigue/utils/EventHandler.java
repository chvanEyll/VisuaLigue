package ca.ulaval.glo2004.visualigue.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class EventHandler<T> {

    Set<BiConsumer<Object, T>> biConsumers = new HashSet<>();
    Set<EventHandler> forwardHandlers = new HashSet<>();

    public void addHandler(BiConsumer<Object, T> handler) {
        biConsumers.add(handler);
    }

    public void setHandler(BiConsumer<Object, T> handler) {
        biConsumers.clear();
        biConsumers.add(handler);
    }

    public void forward(EventHandler<T> handler) {
        forwardHandlers.add(handler);
    }

    public void clear() {
        biConsumers.clear();
    }

    public void fire(Object object, T eventArgs) {
        for (BiConsumer<Object, T> consumer : biConsumers) {
            consumer.accept(object, eventArgs);
        }
        for (EventHandler forwardHandler : forwardHandlers) {
            forwardHandler.fire(object, eventArgs);
        }
    }
}
