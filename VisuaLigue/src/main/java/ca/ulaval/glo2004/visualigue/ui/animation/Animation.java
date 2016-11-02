package ca.ulaval.glo2004.visualigue.ui.animation;

import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import ca.ulaval.glo2004.visualigue.utils.math.easing.ExponentialEaseOutFunction;
import ca.ulaval.glo2004.visualigue.utils.math.easing.LinearEaseFunction;
import java.time.Duration;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.geometry.Insets;
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
    private BiConsumer<Animator, T> onFrameConsumer = null;

    public Animation(Consumer<T> method) {
        this.method = method;
    }

    public static <T> Animation method(Consumer<T> method) {
        return new Animation(method);
    }

    public Animation from(Integer value) {
        this.startValue = value;
        return this;
    }

    public Animation from(Double value) {
        this.startValue = value;
        return this;
    }

    public Animation to(Integer value) {
        this.endValue = value;
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

    public Animation from(Insets value) {
        this.startValue = value;
        return this;
    }

    public Animation to(Insets value) {
        this.endValue = value;
        return this;
    }

    public Animation duration(Integer duration) {
        this.duration = Duration.ofMillis(duration);
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

    public Animation callFrame(BiConsumer<Animator, T> consumer) {
        this.onFrameConsumer = consumer;
        return this;
    }

    public Animator easeOutExp() {
        return build(new ExponentialEaseOutFunction());
    }

    public Animator linear() {
        return build(new LinearEaseFunction());
    }

    private Animator build(EasingFunction easingFunction) {
        method.accept((T) startValue);
        Animator animator = new Animator(method, startValue, endValue, duration, easingFunction, groupKey, isFirstOfGroup, isLastOfGroup, onFrameConsumer);
        animator.animate();
        return animator;
    }

}
