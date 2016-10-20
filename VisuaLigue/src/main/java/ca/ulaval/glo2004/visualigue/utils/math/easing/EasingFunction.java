package ca.ulaval.glo2004.visualigue.utils.math.easing;

public interface EasingFunction {

    double ease(double startValue, double endValue, double elapsedTime, double duration);
}
