package ca.ulaval.glo2004.visualigue.utils.javafx;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class BindingUtils {

    public static Map<Property, Set<Property>> propertyBindingMap = new HashMap();
    public static Map<ObservableValue, Set<ChangeListener>> listenerMap = new HashMap();

    public static <T> void addListener(ObservableValue observableValue, ChangeListener<T> listener) {
        addListenerToMap(observableValue, listener);
        observableValue.addListener(listener);
    }

    public static <T> void setListener(ObservableValue observableValue, ChangeListener<T> listener) {
        clearListeners(observableValue);
        addListener(observableValue, listener);
    }

    public static void bind(Property property, Property otherProperty) {
        addBindingToMap(property, otherProperty);
        property.bind(otherProperty);
    }

    public static void cleanBind(Property property, Property otherProperty) {
        clearBindings(property);
        bind(property, otherProperty);
    }

    public static void bindBidirectional(Property property, Property otherProperty) {
        addBindingToMap(property, otherProperty);
        property.bindBidirectional(otherProperty);
    }

    public static void cleanBindBidirectional(Property property, Property otherProperty) {
        clearBindings(property);
        bindBidirectional(property, otherProperty);
    }

    public static void clearBindings(Property property) {
        if (propertyBindingMap.containsKey(property)) {
            Set<Property> bindedProperties = propertyBindingMap.get(property);
            property.unbind();
            bindedProperties.stream().forEach(p -> property.unbindBidirectional(p));
            propertyBindingMap.remove(property);
        }
    }

    public static void clearListeners(ObservableValue observableValue) {
        if (listenerMap.containsKey(observableValue)) {
            Set<ChangeListener> listeners = listenerMap.get(observableValue);
            listeners.stream().forEach(o -> observableValue.removeListener(o));
            listenerMap.remove(observableValue);
        }
    }

    private static void addBindingToMap(Property property, Property otherProperty) {
        Set<Property> bindedProperties;
        if (propertyBindingMap.containsKey(property)) {
            bindedProperties = propertyBindingMap.get(property);
        } else {
            bindedProperties = new HashSet();
            propertyBindingMap.put(property, bindedProperties);
        }
        bindedProperties.add(otherProperty);
    }

    private static void addListenerToMap(ObservableValue observableValue, ChangeListener changeListener) {
        Set<ChangeListener> listeners;
        if (listenerMap.containsKey(observableValue)) {
            listeners = listenerMap.get(observableValue);
        } else {
            listeners = new HashSet();
            listenerMap.put(observableValue, listeners);
        }
        listeners.add(changeListener);
    }

}
