package ca.ulaval.glo2004.visualigue.ui.animation.tasks;

import ca.ulaval.glo2004.visualigue.ui.animation.easing.EasingFunction;
import java.time.Duration;

public abstract class Transition<T> {

    protected EasingFunction easingFunction;

    public Transition(EasingFunction easingFunction) {
        this.easingFunction = easingFunction;
    }

    public abstract T animate(T startRect, T endRect, Duration elapsedTime, Duration duration);

}