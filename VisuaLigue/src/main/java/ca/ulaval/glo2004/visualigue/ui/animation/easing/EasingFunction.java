package ca.ulaval.glo2004.visualigue.ui.animation.easing;

public interface EasingFunction {

    double ease(double startValue, double endValue, double elapsedTime, double duration);
}
