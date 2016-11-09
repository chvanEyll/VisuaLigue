package ca.ulaval.glo2004.visualigue.ui.animation;

import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import ca.ulaval.glo2004.visualigue.utils.math.easing.ExponentialEaseOutFunction;
import ca.ulaval.glo2004.visualigue.utils.math.easing.LinearEaseFunction;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.scene.shape.Rectangle;

public class Animation<T> {

    private final Consumer<T> method;
    private Object startValue;
    private Object endValue;
    private Long duration = 0L;
    private Long delay = 0L;
    private EasingFunction easingFunction;
    private Object groupKey = null;
    private Boolean isFirstOfGroup = false;
    private Boolean isLastOfGroup = false;
    private BiConsumer<Animator, T> onFrame = null;
    private Consumer<Animator> onComplete = null;

    public Animation(Consumer<T> method) {
        this.method = method;
    }

    public static <T> Animation method(Consumer<T> method) {
        return new Animation(method);
    }

    public Animation from(Long value) {
        this.startValue = value;
        return this;
    }

    public Animation from(Double value) {
        this.startValue = value;
        return this;
    }

    public Animation to(Long value) {
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

    public Animation duration(Long duration) {
        this.duration = duration;
        return this;
    }

    public Animation delay(Long delay) {
        this.delay = delay;
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

    public Animation callOnFrame(BiConsumer<Animator, T> consumer) {
        this.onFrame = consumer;
        return this;
    }

    public Animation callOnComplete(Consumer<Animator> consumer) {
        this.onComplete = consumer;
        return this;
    }

    public Animator easeOutExp() {
        return build(new ExponentialEaseOutFunction());
    }

    public Animator linear() {
        return build(new LinearEaseFunction());
    }

    private Animator build(EasingFunction easingFunction) {
        if (delay == 0) {
            method.accept((T) startValue);
        }
        Animator animator = new Animator(method, startValue, endValue, duration, delay, easingFunction, groupKey, isFirstOfGroup, isLastOfGroup, onFrame, onComplete);
        animator.animate();
        return animator;
    }

}
