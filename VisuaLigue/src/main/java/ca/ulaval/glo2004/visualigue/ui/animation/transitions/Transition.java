package ca.ulaval.glo2004.visualigue.ui.animation.transitions;

import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;

public abstract class Transition<T> {

    protected EasingFunction easingFunction;

    public Transition(EasingFunction easingFunction) {
        this.easingFunction = easingFunction;
    }

    public abstract T animate(T startRect, T endRect, Long elapsedTime, Long duration);

}
