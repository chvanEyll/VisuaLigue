package ca.ulaval.glo2004.visualigue.utils;


import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EventHandler<T>
{
    Set<Consumer<Object>> consumers = new HashSet<>();
    Set<BiConsumer<Object, T>> biConsumers = new HashSet<>();
    
    public void addHandler(Consumer<Object> handler) {
        consumers.add(handler);
    }
    
    public void addHandler(BiConsumer<Object, T> handler) {
        biConsumers.add(handler);
    }
    
    public void fire(Object object) {
        for (Consumer<Object> consumer : consumers) {
            consumer.accept(object);
        }
    }
        
    public void fire(Object object, T eventArgs) {
        for (BiConsumer<Object, T> consumer : biConsumers) {
            consumer.accept(object, eventArgs);
        }
    }
}