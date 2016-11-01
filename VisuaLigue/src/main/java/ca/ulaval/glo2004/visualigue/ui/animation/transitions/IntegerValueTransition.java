package ca.ulaval.glo2004.visualigue.ui.animation.transitions;

import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import java.time.Duration;

public class IntegerValueTransition extends Transition<Integer> {

    public IntegerValueTransition(EasingFunction easingFunction) {
        super(easingFunction);
    }

    @Override
    public Integer animate(Integer startValue, Integer endValue, Duration elapsedTime, Duration duration) {
        return (int) easingFunction.ease((double) startValue, (double) endValue, elapsedTime.toMillis(), duration.toMillis());
    }
}
