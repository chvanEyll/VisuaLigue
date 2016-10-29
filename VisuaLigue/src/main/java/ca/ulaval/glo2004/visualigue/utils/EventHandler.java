package ca.ulaval.glo2004.visualigue.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EventHandler<T> {

    Set<BiConsumer<Object, T>> biConsumers = new HashSet();
    Set<Consumer<Object>> consumers = new HashSet();
    Set<EventHandler> forwardHandlers = new HashSet();

    public void addHandler(BiConsumer<Object, T> handler) {
        biConsumers.add(handler);
    }

    public void addHandler(Consumer<Object> handler) {
        consumers.add(handler);
    }

    public void setHandler(BiConsumer<Object, T> handler) {
        biConsumers.clear();
        biConsumers.add(handler);
    }

    public void setHandler(Consumer<Object> handler) {
        consumers.clear();
        consumers.add(handler);
    }

    public void removeHandler(BiConsumer<Object, T> handler) {
        biConsumers.remove(handler);
    }

    public void removeHandler(Consumer<Object> handler) {
        consumers.remove(handler);
    }

    public void forward(EventHandler<T> handler) {
        forwardHandlers.add(handler);
    }

    public void clear() {
        biConsumers.clear();
    }

    public void fire(Object object) {
        fire(object, null);
    }

    public void fire(Object object, T eventArgs) {
        biConsumers.stream().forEach((biConsumer) -> {
            biConsumer.accept(object, eventArgs);
        });
        consumers.stream().forEach((consumer) -> {
            consumer.accept(object);
        });
        forwardHandlers.stream().forEach((forwardHandler) -> {
            forwardHandler.fire(object, eventArgs);
        });
    }
}
