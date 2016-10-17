package ca.ulaval.glo2004.visualigue.ui.animation.tasks;

import ca.ulaval.glo2004.visualigue.ui.animation.easing.EasingFunction;
import java.time.Duration;

public class SimpleValueTransition extends Transition<Double> {

    public SimpleValueTransition(EasingFunction easingFunction) {
        super(easingFunction);
    }

    @Override
    public Double animate(Double startValue, Double endValue, Duration elapsedTime, Duration duration) {
        return easingFunction.ease(startValue, endValue, elapsedTime.toMillis(), duration.toMillis());
    }
}
