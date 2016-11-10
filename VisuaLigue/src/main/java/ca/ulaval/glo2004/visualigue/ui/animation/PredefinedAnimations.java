package ca.ulaval.glo2004.visualigue.ui.animation;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public class PredefinedAnimations {

    public static void zoom(Node node) {
        Animation.method(node::setScaleX).duration(600L).from(0.95).to(1.0).group(node).first().easeOutExp();
        Animation.method(node::setScaleY).duration(600L).from(0.95).to(1.0).group(node).easeOutExp();
        Animation.method(node::setScaleZ).duration(600L).from(0.95).to(1.0).group(node).easeOutExp();
        Animation.method(node::setOpacity).duration(600L).from(0.0).to(1.0).group(node).last().easeOutExp();
    }

    public static void pan(Node node) {
        Animation.method(node::setTranslateX).duration(750L).from(50.0).to(0.0).group(node).first().easeOutExp();
        Animation.method(node::setOpacity).duration(750L).from(0.0).to(1.0).group(node).last().easeOutExp();
    }

    public static void split(Node node) {
        Animation.method(node::setScaleY).duration(30L).from(0.25).to(1.0).group(node).first().easeOutExp();
        Animation.method(node::setOpacity).duration(30L).from(0.0).to(1.0).group(node).last().easeOutExp();
    }

    public static void hideRight(Region region, Double targetWidth) {
        Insets startInsets = region.getInsets();
        Insets endInsets = new Insets(0, targetWidth - region.getWidth() + startInsets.getRight(), 0, 0);
        Animation.method(region::setPadding).duration(400L).from(startInsets).to(endInsets).group(region).first().easeOutExp();
    }

    public static void revealRight(Region region) {
        Insets startInsets = region.getInsets();
        Insets endInsets = new Insets(0);
        Animation.method(region::setPadding).duration(400L).from(startInsets).to(endInsets).group(region).first().easeOutExp();
    }
}
