package ca.ulaval.glo2004.visualigue.utils.math.easing;

public class LinearEaseFunction implements EasingFunction {

    @Override
    public double ease(double startValue, double endValue, double elapsedTime, double duration) {
        return (endValue - startValue) * (elapsedTime / duration) + startValue;
    }

}
