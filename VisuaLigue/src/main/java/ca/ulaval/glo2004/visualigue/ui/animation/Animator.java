package ca.ulaval.glo2004.visualigue.ui.animation;

import ca.ulaval.glo2004.visualigue.ui.animation.transitions.*;
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
    private static final List<Animator> runningAnimators = Collections.synchronizedList(new ArrayList());
    private static final Object ANIMATION_SYNCHRONIZE_LOCK = new Object();

    private final Consumer method;
    private T startValue;
    private T endValue;
    private Duration duration;
    private EasingFunction easingFunction;
    private Object groupKey;
    private Boolean isFirstOfGroup;
    private Boolean isLastOfGroup;
    private LocalDateTime animationStartTime;
    private Timer timer;
    private Transition transition;
    private BiConsumer<Animator, T> onFrameConsumer;

    public Animator(Consumer method, T startValue, T endValue, Duration duration, EasingFunction easingFunction, Object groupKey, Boolean firstOfGroup, Boolean lastOfGroup, BiConsumer<Animator, T> onFrameConsumer) {
        this.method = method;
        this.startValue = startValue;
        this.endValue = endValue;
        this.duration = duration;
        this.easingFunction = easingFunction;
        this.groupKey = groupKey;
        this.isFirstOfGroup = firstOfGroup;
        this.isLastOfGroup = lastOfGroup;
        this.onFrameConsumer = onFrameConsumer;
    }

    public void animate() {
        if (endValue instanceof Integer) {
            transition = new IntegerValueTransition(easingFunction);
        } else if (endValue instanceof Double) {
            transition = new DoubleValueTransition(easingFunction);
        } else if (endValue instanceof Rectangle) {
            transition = new RectangleTransition(easingFunction);
        } else if (endValue instanceof Insets) {
            transition = new InsetsTransition(easingFunction);
        } else {
            return;
        }
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                animateFrame();
            }
        };
        animationStartTime = LocalDateTime.now();
        timer = new Timer();
        if (groupKey != null && isFirstOfGroup) {
            stopAnimationsOfGroup(groupKey);
        }
        synchronized (ANIMATION_SYNCHRONIZE_LOCK) {
            runningAnimators.add(this);
        }
        timer.schedule(timerTask, ANIMATION_PERIOD, ANIMATION_PERIOD);
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
            synchronized (ANIMATION_SYNCHRONIZE_LOCK) {
                timer.cancel();
                runningAnimators.remove(this);
            }
        }
    }

    private void animateFrame() {
        T value;
        Duration elapsedTime = Duration.between(animationStartTime, LocalDateTime.now());
        if (startValue == null || elapsedTime.toMillis() > duration.toMillis()) {
            cancel();
            value = endValue;
        } else {
            value = (T) transition.animate(startValue, endValue, elapsedTime, duration);
        }
        Platform.runLater(() -> {
            method.accept(value);
            if (onFrameConsumer != null) {
                onFrameConsumer.accept(this, value);
            }
        });
    }
}
