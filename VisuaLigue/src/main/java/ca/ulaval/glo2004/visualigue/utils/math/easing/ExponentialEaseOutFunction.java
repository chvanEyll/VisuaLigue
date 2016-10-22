package ca.ulaval.glo2004.visualigue.utils.math.easing;

public class ExponentialEaseOutFunction implements EasingFunction {

    @Override
    public double ease(double startValue, double endValue, double elapsedTime, double duration) {
        return (endValue - startValue) * (-Math.pow(2f, -10f * elapsedTime / duration) + 1f) + startValue;
    }

}