package ca.ulaval.glo2004.visualigue.utils.javafx;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.Property;

public class BindingUtils {

    public static Map<Property, Set<Property>> propertyBindingMap = new HashMap();

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

}
