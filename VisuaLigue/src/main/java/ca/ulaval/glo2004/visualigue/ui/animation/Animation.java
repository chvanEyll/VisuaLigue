package ca.ulaval.glo2004.visualigue.ui.animation;

import ca.ulaval.glo2004.visualigue.ui.animation.easing.EasingFunction;
import ca.ulaval.glo2004.visualigue.ui.animation.easing.ExponentialEaseOut;
import java.time.Duration;
import java.util.function.Consumer;
import javafx.scene.shape.Rectangle;

public class Animation<T> {

    private final Consumer<T> method;
    private Object startValue;
    private Object endValue;
    private Duration duration;
    private EasingFunction easingFunction;
    private Object groupKey = null;
    private Boolean isFirstOfGroup = false;
    private Boolean isLastOfGroup = false;

    public Animation(Consumer<T> method) {
        this.method = method;
    }

    public static <T> Animation method(Consumer<T> method) {
        return new Animation(method);
    }

    public Animation from(Double value) {
        this.startValue = value;
        return this;
    }

    public Animation to(Double value) {
        this.endValue = value;
        return this;
    }

    public Animation from(Rectangle value) {
        this.startValue = value;
        return this;
    }

    public Animation to(Rectangle value) {
        this.endValue = value;
        return this;
    }

    public Animation duration(Double durationSeconds) {
        Double durationMillis = durationSeconds * 1000;
        this.duration = Duration.ofMillis(durationMillis.longValue());
        return this;
    }

    public Animation group(Object key) {
        this.groupKey = key;
        return this;
    }

    public Animation first() {
        this.isFirstOfGroup = true;
        return this;
    }

    public Animation last() {
        this.isLastOfGroup = true;
        return this;
    }

    public void easeOutExp() {
        build(new ExponentialEaseOut());
    }

    private void build(EasingFunction easingFunction) {
        method.accept((T) startValue);
        Animator animator = new Animator(method, startValue, endValue, duration, easingFunction, groupKey, isFirstOfGroup, isLastOfGroup);
        animator.animate();
    }

}
