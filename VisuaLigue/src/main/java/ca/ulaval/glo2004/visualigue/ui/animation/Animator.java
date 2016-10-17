package ca.ulaval.glo2004.visualigue.ui.animation;

import ca.ulaval.glo2004.visualigue.ui.animation.easing.EasingFunction;
import ca.ulaval.glo2004.visualigue.ui.animation.tasks.RectangleTransition;
import ca.ulaval.glo2004.visualigue.ui.animation.tasks.SimpleValueTransition;
import ca.ulaval.glo2004.visualigue.ui.animation.tasks.Transition;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

public class Animator<T> {

    private static final Integer ANIMATION_PERIOD = 15;
    private static final List<Animator> runningAnimators = new ArrayList<>();

    private final Consumer method;
    private T startValue;
    private T endValue;
    private Duration duration;
    private EasingFunction easingFunction;
    private Boolean isFirstOfGroup;
    private Boolean isLastOfGroup;
    private LocalDateTime animationStartTime;
    private Timer timer;
    private Transition transition;

    public Animator(Consumer method, T startValue, T endValue, Duration duration, EasingFunction easingFunction, Boolean isFirstOfGroup, Boolean isLastOfGroup) {
        this.method = method;
        this.startValue = startValue;
        this.endValue = endValue;
        this.duration = duration;
        this.easingFunction = easingFunction;
        this.isFirstOfGroup = isFirstOfGroup;
        this.isLastOfGroup = isLastOfGroup;
    }

    public void animate() {
        if (endValue instanceof Double) {
            transition = new SimpleValueTransition(easingFunction);
        } else if (endValue instanceof Rectangle) {
            transition = new RectangleTransition(easingFunction);
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
        if (isFirstOfGroup) {
            stopRunningAnimations();
        }
        runningAnimators.add(this);
        timer.schedule(timerTask, ANIMATION_PERIOD, ANIMATION_PERIOD);
    }

    private static void stopRunningAnimations() {
        runningAnimators.stream().collect(Collectors.toList()).forEach(animator -> {
            animator.cancel();
        });
    }

    private void cancel() {
        if (timer != null) {
            timer.cancel();
            runningAnimators.remove(this);
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
        });
    }
}
