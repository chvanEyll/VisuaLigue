package ca.ulaval.glo2004.visualigue.ui.animation.transitions;

import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;

public class LongValueTransition extends Transition<Long> {

    public LongValueTransition(EasingFunction easingFunction) {
        super(easingFunction);
    }

    @Override
    public Long animate(Long startValue, Long endValue, Long elapsedTime, Long duration) {
        return (long) easingFunction.ease((double) startValue, (double) endValue, elapsedTime, duration);
    }
}
