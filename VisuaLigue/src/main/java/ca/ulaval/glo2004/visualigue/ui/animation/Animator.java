package ca.ulaval.glo2004.visualigue.ui.animation;

import ca.ulaval.glo2004.visualigue.ui.animation.transitions.*;
import ca.ulaval.glo2004.visualigue.utils.TimerTaskUtils;
import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.shape.Rectangle;

public class Animator<T> {

    private static final Integer ANIMATION_PERIOD = 15;
    private static final List<Animator> runningAnimators = new ArrayList();
    private static final Object ANIMATION_SYNCHRONIZE_LOCK = new Object();

    private final Consumer method;
    private T startValue;
    private T endValue;
    private Long duration;
    private Long delay;
    private EasingFunction easingFunction;
    private Object groupKey;
    private Boolean isFirstOfGroup;
    private Boolean isLastOfGroup;
    private LocalDateTime animationStartTime;
    private Timer timer;
    private Transition transition;
    private BiConsumer<Animator, T> onFrame;
    private Consumer<Animator> onComplete;

    public Animator(Consumer method, T startValue, T endValue, Long duration, Long delay, EasingFunction easingFunction, Object groupKey, Boolean firstOfGroup, Boolean lastOfGroup, BiConsumer<Animator, T> onFrame, Consumer<Animator> onComplete) {
        this.method = method;
        this.startValue = startValue;
        this.endValue = endValue;
        this.duration = duration;
        this.delay = delay;
        this.easingFunction = easingFunction;
        this.groupKey = groupKey;
        this.isFirstOfGroup = firstOfGroup;
        this.isLastOfGroup = lastOfGroup;
        this.onFrame = onFrame;
        this.onComplete = onComplete;
    }

    public void animate() {
        if (endValue instanceof Long) {
            transition = new LongValueTransition(easingFunction);
        } else if (endValue instanceof Double) {
            transition = new DoubleValueTransition(easingFunction);
        } else if (endValue instanceof Rectangle) {
            transition = new RectangleTransition(easingFunction);
        } else if (endValue instanceof Insets) {
            transition = new InsetsTransition(easingFunction);
        } else {
            return;
        }
        animationStartTime = LocalDateTime.now();
        timer = new Timer();
        if (groupKey != null && isFirstOfGroup) {
            stopAnimationsOfGroup(groupKey);
        }
        synchronized (ANIMATION_SYNCHRONIZE_LOCK) {
            runningAnimators.add(this);
        }
        timer.schedule(TimerTaskUtils.wrap(() -> animateFrame()), delay, ANIMATION_PERIOD);
    }

    private static void stopAnimationsOfGroup(Object groupKey) {
        List<Animator> animatorList;
        synchronized (ANIMATION_SYNCHRONIZE_LOCK) {
            animatorList = runningAnimators.stream().filter(a -> a.groupKey == groupKey).collect(Collectors.toList());
        }
        animatorList.forEach(animator -> {
            animator.cancel();
        });
    }

    public void cancel() {
        if (timer != null) {
            timer.cancel();
        }
        synchronized (ANIMATION_SYNCHRONIZE_LOCK) {
            runningAnimators.remove(this);
        }
    }

    private void animateFrame() {
        T value;
        if (animationComplete()) {
            cancel();
            value = endValue;
        } else {
            value = (T) transition.animate(startValue, endValue, getElapsedTime() - delay, duration);
        }
        Platform.runLater(() -> {
            method.accept(value);
            if (onFrame != null) {
                onFrame.accept(this, value);
            }
        });
        if (onComplete != null && animationComplete()) {
            onComplete.accept(this);
        }
    }

    private Long getElapsedTime() {
        return Duration.between(animationStartTime, LocalDateTime.now()).toMillis();
    }

    private Boolean animationComplete() {
        return startValue == null || getElapsedTime() > duration + delay;
    }
}
