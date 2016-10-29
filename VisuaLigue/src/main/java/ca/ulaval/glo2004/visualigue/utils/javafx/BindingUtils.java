package ca.ulaval.glo2004.visualigue.utils.javafx;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.ObjectProperty;

public class BindingUtils {

    public static Map<ObjectProperty, Set<ObjectProperty>> bindingMap = new HashMap();

    public static void bind(ObjectProperty property, ObjectProperty otherProperty) {
        addBindingToMap(property, otherProperty);
        property.bind(otherProperty);
    }

    public static void bindClean(ObjectProperty property, ObjectProperty otherProperty) {
        clearBindings(property);
        bind(property, otherProperty);
    }

    public static void bindBidirectional(ObjectProperty property, ObjectProperty otherProperty) {
        addBindingToMap(property, otherProperty);
        property.bindBidirectional(otherProperty);
    }

    public static void bindBidirectionalClean(ObjectProperty property, ObjectProperty otherProperty) {
        clearBindings(property);
        bindBidirectional(property, otherProperty);
    }

    public static void clearBindings(ObjectProperty property) {
        if (bindingMap.containsKey(property)) {
            Set<ObjectProperty> bindedProperties = bindingMap.get(property);
            property.unbind();
            bindedProperties.stream().forEach(p -> property.unbindBidirectional(p));
        }
    }

    private static void addBindingToMap(ObjectProperty property, ObjectProperty otherProperty) {
        Set<ObjectProperty> bindedProperties;
        if (bindingMap.containsKey(property)) {
            bindedProperties = bindingMap.get(property);
        } else {
            bindedProperties = new HashSet();
            bindingMap.put(property, bindedProperties);
        }
        bindedProperties.add(otherProperty);
    }

}
