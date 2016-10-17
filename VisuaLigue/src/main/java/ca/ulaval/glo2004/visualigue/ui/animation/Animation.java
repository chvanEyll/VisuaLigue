package ca.ulaval.glo2004.visualigue.ui.animation;

import ca.ulaval.glo2004.visualigue.ui.animation.easing.EasingFunction;
import ca.ulaval.glo2004.visualigue.ui.animation.easing.ExponentialEaseOut;
import java.time.Duration;
import java.util.function.Consumer;

public class Animation<T> {

    private final Consumer method;
    private T startValue;
    private T endValue;
    private Duration duration;
    private EasingFunction easingFunction;
    private Boolean isFirstOfGroup = false;
    private Boolean isLastOfGroup = false;

    public Animation(Consumer method) {
        this.method = method;
    }

    public static <T> Animation method(Consumer<T> method) {
        return new Animation(method);
    }

    public Animation from(T value) {
        this.startValue = value;
        return this;
    }

    public Animation to(T value) {
        this.endValue = value;
        return this;
    }

    public Animation duration(Double durationSeconds) {
        Double durationMillis = durationSeconds * 1000;
        this.duration = Duration.ofMillis(durationMillis.longValue());
        return this;
    }

    public Animation groupBegin() {
        this.isFirstOfGroup = true;
        return this;
    }

    public Animation groupEnd() {
        this.isLastOfGroup = true;
        return this;
    }

    public void easeOutExp() {
        build(new ExponentialEaseOut());
    }

    private void build(EasingFunction easingFunction) {
        Animator animator = new Animator(method, startValue, endValue, duration, easingFunction, isFirstOfGroup, isLastOfGroup);
        animator.animate();
    }

}
