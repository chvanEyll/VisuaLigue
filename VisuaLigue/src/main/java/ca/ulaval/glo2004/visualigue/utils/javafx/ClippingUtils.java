package ca.ulaval.glo2004.visualigue.utils.javafx;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public class ClippingUtils {

    private static Map<ObservableValue, Region> regionMap = new HashMap();
    private static ChangeListener<Number> onRegionWidthChanged = ClippingUtils::onRegionWidthChanged;
    private static ChangeListener<Number> onRegionHeightChanged = ClippingUtils::onRegionHeightChanged;

    public static void clipToSize(Region region) {
        regionMap.put(region.widthProperty(), region);
        region.widthProperty().removeListener(onRegionWidthChanged);
        region.widthProperty().addListener(onRegionWidthChanged);
        regionMap.put(region.heightProperty(), region);
        region.heightProperty().removeListener(onRegionHeightChanged);
        region.heightProperty().addListener(onRegionHeightChanged);
    }

    public static void unclip(Region region) {
        region.widthProperty().removeListener(onRegionWidthChanged);
        region.heightProperty().removeListener(onRegionHeightChanged);
        regionMap.remove(region.widthProperty(), region);
        regionMap.remove(region.heightProperty(), region);
        region.setClip(null);
    }

    private static void onRegionWidthChanged(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        if (newPropertyValue.doubleValue() != 0) {
            Region region = regionMap.get(value);
            Double currentWidth = newPropertyValue.doubleValue();
            Double currentHeight = region.getHeight();
            region.setClip(new Rectangle(0, 0, currentWidth, currentHeight));
        }
    }

    private static void onRegionHeightChanged(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        if (newPropertyValue.doubleValue() != 0) {
            Region region = regionMap.get(value);
            Double currentWidth = region.getWidth();
            Double currentHeight = newPropertyValue.doubleValue();
            region.setClip(new Rectangle(0, 0, currentWidth, currentHeight));
        }
    }
}
