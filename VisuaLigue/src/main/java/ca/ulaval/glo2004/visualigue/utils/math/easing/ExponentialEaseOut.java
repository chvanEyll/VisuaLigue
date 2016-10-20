package ca.ulaval.glo2004.visualigue.utils.math.easing;

import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;

public class ExponentialEaseOut implements EasingFunction {

    @Override
    public double ease(double startValue, double endValue, double elapsedTime, double duration) {
        return (endValue - startValue) * (-Math.pow(2f, -10f * elapsedTime / duration) + 1f) + startValue;
    }

}
