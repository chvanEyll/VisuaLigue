package ca.ulaval.glo2004.visualigue.ui.animation.transitions;

import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.time.Duration;

public class DoubleValueTransition extends Transition<Double> {

    public DoubleValueTransition(EasingFunction easingFunction) {
        super(easingFunction);
    }

    @Override
    public Double animate(Double startValue, Double endValue, Duration elapsedTime, Duration duration) {
        return easingFunction.ease(startValue, endValue, elapsedTime.toMillis(), duration.toMillis());
    }
}
