package ca.ulaval.glo2004.visualigue.ui.animation;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public class PredefinedAnimations {

    public static void nodeZoom(Node node) {
        Animation.method(node::setScaleX).duration(0.6).from(0.95).to(1.0).group(node).first().easeOutExp();
        Animation.method(node::setScaleY).duration(0.6).from(0.95).to(1.0).group(node).easeOutExp();
        Animation.method(node::setScaleZ).duration(0.6).from(0.95).to(1.0).group(node).easeOutExp();
        Animation.method(node::setOpacity).duration(0.6).from(0.0).to(1.0).group(node).last().easeOutExp();
    }

    public static void nodePan(Node node) {
        Animation.method(node::setTranslateX).duration(0.75).from(50.0).to(0.0).group(node).first().easeOutExp();
        Animation.method(node::setOpacity).duration(0.75).from(0.0).to(1.0).group(node).last().easeOutExp();
    }

    public static void nodeSplit(Node node) {
        Animation.method(node::setScaleY).duration(0.3).from(0.25).to(1.0).group(node).first().easeOutExp();
        Animation.method(node::setOpacity).duration(0.3).from(0.0).to(1.0).group(node).last().easeOutExp();
    }

    public static void regionRevealLeft(Region region) {
        region.setClip(new Rectangle(0, 0));
        Animation.method(region::setClip).duration(0.4).from(new Rectangle(region.getWidth(), 0, Integer.MAX_VALUE, Integer.MAX_VALUE)).to(new Rectangle(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE)).easeOutExp();
    }

    public static void regionExpand(Region region, Double extendedWidth) {
        Animation.method(region::setMaxWidth).duration(0.4).from(region.getWidth()).to(extendedWidth).group(region).first().easeOutExp();
        Animation.method(region::setMinWidth).duration(0.4).from(region.getWidth()).to(extendedWidth).group(region).easeOutExp();
        Animation.method(region::setClip).duration(0.4).from((Rectangle) region.getClip()).to(new Rectangle(extendedWidth, Integer.MAX_VALUE)).group(region).last().easeOutExp();
    }

    public static void regionCollapse(Region region, Double collapsedWidth) {
        region.setClip(new Rectangle(region.getWidth(), Integer.MAX_VALUE));
        Animation.method(region::setClip).duration(0.4).from((Rectangle) region.getClip()).to(new Rectangle(collapsedWidth, Integer.MAX_VALUE)).group(region).first().easeOutExp();
        Animation.method(region::setMaxWidth).duration(0.4).from(region.getWidth()).to(collapsedWidth).group(region).easeOutExp();
        Animation.method(region::setMinWidth).duration(0.4).from(region.getWidth()).to(collapsedWidth).group(region).last().easeOutExp();
    }

}
